/*
 * Copyright (C) 2022 util2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.wingate.assj.core;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.geometry.Point2D;
import org.wingate.assj.ASS;
import org.wingate.assj.AssEvent;
import org.wingate.assj.AssTime;

/**
 *
 * @author util2
 */
public class FxCharList {
    
    private final List<FxChar> sfc = new ArrayList<>();
    private Font font = null;
    
    private double lineWidth = 0d;
    private double lineHeight = 0d;
    private double spaceWidth = 0d;

    private FxCharList() {
        
    }
    
    public static FxCharList getFxChars(ASS ass, AssEvent ev, long nanos){
        FxCharList list = new FxCharList();
                
        // On obtient le texte à passer au hachoir
        String rawline = ev.getText();
        
        // On récupère les surcharges
        String overrides = ev.getStyle().getTagsFromStyleToString();
        if(rawline.startsWith("{") == true){
            rawline = rawline.replace("}{", "");
            int index = rawline.indexOf("}");
            overrides += rawline.substring(1, index);
        }
        
        if(overrides.isEmpty() == false){
            rawline = rawline.substring(rawline.indexOf("}") + 1);
        }
        
        // On récupère les effets
        String strTags = overrides;
        boolean inTAGS = false;
        
        for(char c : rawline.toCharArray()){
            if(c == '{'){
                inTAGS = true;
            }
            
            if(inTAGS == true && c != '}' && c != '{'){
                strTags += Character.toString(c);
            }else if(inTAGS == false && c != '}'){
                Point2D pos = list.calcFontAndPos(ass, ev, strTags, nanos);
                FxChar fxChar = new FxChar(
                        c,
                        strTags,
                        nanos,
                        ass,
                        ev,
                        list.font,
                        list.lineWidth,
                        list.lineHeight,
                        list.spaceWidth
                );
                fxChar.getPath().setTranslateX(pos.getX());
                fxChar.getPath().setTranslateY(pos.getY());
                list.sfc.add(fxChar);
            }
            
            if(c == '}'){
                inTAGS = false;
            }
        }
        
        return list;
    }

    public List<FxChar> getFxCharList() {
        return sfc;
    }
    
    //--------------------------------------------------------------------------
    // FONTE & Pos
    //--------------------------------------------------------------------------
    
    private Point2D calcFontAndPos(ASS ass, AssEvent event, String tags, long nanos){
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        boolean anotherFontName = tags.contains("\\fn");
        String fontName = event.getStyle().getFontname();
        if(anotherFontName == true){
            int lastIndex = tags.lastIndexOf("\\fn");
            int endIndex = tags.indexOf("\\", lastIndex + 1);
            fontName = endIndex == -1 ?
                    tags.substring(lastIndex + "\\fn".length()) :
                    tags.substring(lastIndex + "\\fn".length(), endIndex);
        }
        
        float fontSize = (float)event.getStyle().getFontsize();
        Pattern p = Pattern.compile("\\\\fs(\\d+.?\\d?)");
        Matcher m = p.matcher(tags);
        
        while(m.find()){
            fontSize = Float.parseFloat(m.group(1));
        }
        
        // Marche si b1 > b0 :
        // - si b0 == -1 alors b1 est actif
        // - si b1 == b0 alors les deux sont inactifs
        // - si b0 != -1 alors b1 peut être inactif si b1 < b0 ou actif si b1 > b0
        boolean anotherBold = tags.lastIndexOf("\\b1") > tags.lastIndexOf("\\b0");
        boolean bold = anotherBold == true ? true : event.getStyle().isBold();
        
        // Marche si i1 > i0 :
        // - si i0 == -1 alors i1 est actif
        // - si i1 == i0 alors les deux sont inactifs
        // - si i0 != -1 alors i1 peut être inactif si i1 < i0 ou actif si i1 > i0
        boolean anotherItalic = tags.lastIndexOf("\\i1") > tags.lastIndexOf("\\i0");
        boolean italic = anotherItalic == true ? true : event.getStyle().isItalic();
        
        font = new Font(fontName, (bold ? Font.BOLD : 0) + (italic ? Font.ITALIC : 0), (int)fontSize).deriveFont(fontSize);
        
        // Mesure
        FontMetrics fm = g2d.getFontMetrics(font);

        // Fausse taille :
        float falseSizeInPoints = font.getSize2D();
        float falseHeight = fm.getHeight();

        // Exemple : 12pt on est à taille = height
        //           ?pt  on est à taille = ascent + descent
        // Opération ?pt = 12pt * (ascent + descent) / height
        float trueSizeInPoints = falseSizeInPoints * (fm.getAscent() + fm.getDescent()) / falseHeight;
        
        // On applique la bonne taille :
        font = new Font(fontName, (bold ? Font.BOLD : 0) + (italic ? Font.ITALIC : 0), (int)trueSizeInPoints)
                .deriveFont(trueSizeInPoints);
        
        // On configure une ligne sans tags
        String strippedline = event.getText().replaceAll("\\{[^\\}]+\\}", "");
        
        // On retourne sa taille et sa hauteur
        lineWidth = fm.stringWidth(strippedline);
        lineHeight = fm.getHeight();
        spaceWidth = fm.charWidth(' ');
        
        double widthLine = lineWidth;
        double heightLine = lineHeight;
        double videoWidth = Integer.parseInt(ass.getResX());
        double videoHeight = Integer.parseInt(ass.getResY());
        
        String s;

        if(widthLine >= videoWidth - (double)event.getMarginL() - (double)event.getMarginR()){
            switch(Integer.parseInt(ass.getWrapStyle())){
                case 0 -> {
                    int indexOfNextSpace = strippedline.indexOf(" ", (int)(Math.round(widthLine / 2d)));
                    String top = strippedline.substring(0, indexOfNextSpace).trim();
                    String bottom = strippedline.substring(indexOfNextSpace+1).trim();
                    s = String.format("%s\n%s", top, bottom).replace("\\N", "\n");
                    double tw = fm.stringWidth(top);
                    double bw = fm.stringWidth(bottom);
                    widthLine = tw > bw ? tw : bw;
                    heightLine = (double)fm.getHeight() + ((double)fm.getHeight())*0.9;
                }
                case 1 -> {
                    String[] u = strippedline.split(" ");
                    double width; String test = "", top = "", bottom = "";
                    for(int i = 0; i<u.length; i++){
                        test += (i == 0) ? u[i] : " " + u[i];
                        width = fm.stringWidth(test);
                        if(width > videoWidth - event.getMarginL() - event.getMarginR()){
                            top = strippedline.substring(0, strippedline.indexOf(" ", i-1));
                            bottom = strippedline.substring(strippedline.indexOf(" ", i-1));
                            s = String.format("%s\n%s", top, bottom).replace("\\N", "\n");
                            break;
                        }
                    }
                    double tw = fm.stringWidth(top);
                    double bw = fm.stringWidth(bottom);
                    widthLine = tw > bw ? tw : bw;
                    heightLine = (double)fm.getHeight() + ((double)fm.getHeight())*0.9;
                }
                case 2 -> {
                    s = strippedline.replace("\\N", "\n");
                    widthLine = fm.stringWidth(s);
                    heightLine = fm.getHeight();
                }
                case 3 -> {
                    int indexOfPreviousSpace = strippedline.lastIndexOf(" ", (int)(Math.round(widthLine / 2d)));
                    String top = strippedline.substring(0, indexOfPreviousSpace).trim();
                    String bottom = strippedline.substring(indexOfPreviousSpace+1).trim();
                    s = String.format("%s\n%s", top, bottom).replace("\\N", "\n");
                    double tw = fm.stringWidth(top);
                    double bw = fm.stringWidth(bottom);
                    widthLine = tw > bw ? tw : bw;
                    heightLine = (double)fm.getHeight() + ((double)fm.getHeight())*0.9;
                }
                default -> {
                    int indexOfNextSpace = strippedline.indexOf(" ", (int)(Math.round(widthLine / 2d)));
                    String top = strippedline.substring(0, indexOfNextSpace).trim();
                    String bottom = strippedline.substring(indexOfNextSpace+1).trim();
                    s = String.format("%s\n%s", top, bottom).replace("\\N", "\n");
                    double tw = fm.stringWidth(top);
                    double bw = fm.stringWidth(bottom);
                    widthLine = tw > bw ? tw : bw;
                    heightLine = (double)fm.getHeight() + ((double)fm.getHeight())*0.9;
                }
            }
        }
        
        g2d.dispose();

        double x = 0d, offsetX = 0d;
        double y = 0d, offsetY = 0d;        

        int align = event.getStyle().getAlignment();        
        if(tags.contains("\\an") == true){
            p = Pattern.compile("\\\\an(\\d+)");
            m = p.matcher(tags);
            while(m.find()){
                align = Integer.parseInt(m.group(1));
            }
        }        
        if(tags.contains("\\a") == true){
            p = Pattern.compile("\\\\a(\\d+)");
            m = p.matcher(tags);
            while(m.find()){
                switch(Integer.parseInt(m.group(1))){
                    case 1 -> { align = 1; }
                    case 2 -> { align = 2; }
                    case 3 -> { align = 3; }
                    case 5 -> { align = 7; }
                    case 6 -> { align = 8; }
                    case 7 -> { align = 9; }
                    case 9 -> { align = 4; }
                    case 10 -> { align = 5; }
                    case 11 -> { align = 6; }
                }
            }
        }
        
        if(tags.contains("\\move") == true){
            p = Pattern.compile("\\\\move\\((\\d+.*\\d*),(\\d+.*\\d*),(\\d+.*\\d*),(\\d+.*\\d*),(\\d*.*\\d*),(\\d*.*\\d*)\\)");
            m = p.matcher(tags);
            
            double[] move = new double[6];
            if(m.find()){
                move[0] = Double.parseDouble(m.group(1));
                move[1] = Double.parseDouble(m.group(2));
                move[2] = Double.parseDouble(m.group(3));
                move[3] = Double.parseDouble(m.group(4));
                move[4] = m.groupCount() > 5 ? Double.parseDouble(m.group(5)) : 0d;
                move[5] = m.groupCount() > 5 ? Double.parseDouble(m.group(6)) : 0d;
                
                if(move[0] != move[2] || move[1] != move[3]){
                    long nanosStart, nanosEnd;
                    if(move[4] != move[5]){
                        nanosStart = Math.round(Math.pow(move[4], 6));
                        nanosEnd = Math.round(Math.pow(move[5], 6));
                    }else{
                        nanosStart = TimeUnit.MILLISECONDS.toNanos(AssTime.toMillisecondsTime(event.getStartTime()));
                        nanosEnd = TimeUnit.MILLISECONDS.toNanos(AssTime.toMillisecondsTime(event.getEndTime()));
                    }

                    // p2 = 100%    >> t2
                    // p1 = 0%      >> t1
                    // p? = x%      >> t
                    if(nanosStart <= nanos && nanos <= nanosEnd){
                        long rangeValues = nanosEnd - nanosStart; // t2 - t1
                        long rangeAbout = nanosEnd - nanos; // t2 - t

                        // 100% >> rangeValues
                        // X%   >> rangeAbout                                
                        double percent = rangeAbout * 100 / rangeValues;

                        // p? = x% en fonction de t1 d'où p1 * x%                              
                        x = move[0] + (move[2] - move[0]) * percent;
                        y = move[1] + (move[3] - move[1]) * percent;
                    }
                }
            }
            
        }        

        // Mise en place de valeurs par défaut de marges
        int L = event.getMarginL() != 0 ? event.getMarginL() : event.getStyle().getMarginL();
        int R = event.getMarginR() != 0 ? event.getMarginR() : event.getStyle().getMarginR();
        int V = event.getMarginV() != 0 ? event.getMarginV() : event.getStyle().getMarginV();

        if(x == 0d && y == 0d){
            switch(align){
                case 1 -> {
                    // an1 (Left    Bottom)
                    offsetX = L; // Left                
                    offsetY = videoHeight - heightLine - V; // Bottom
                }
                case 2 -> {
                    // an2 (Center  Bottom)
                    offsetX = (videoWidth - widthLine) / 2; // Center
                    offsetY = videoHeight - heightLine - V; // Bottom
                }
                case 3 -> {
                    // an3 (Right   Bottom)
                    offsetX = videoWidth - R - widthLine; // Right
                    offsetY = videoHeight - heightLine - V;// Bottom
                }
                case 4 -> {
                    // an4 (Left    Middle)
                    offsetX = L; // Left
                    offsetY = (videoHeight - heightLine) / 2; // Middle
                }
                case 5 -> {
                    // an5 (Center  Middle)
                    offsetX = (videoWidth - widthLine) / 2; // Center
                    offsetY = (videoHeight - heightLine) / 2; // Middle
                }
                case 6 -> {
                    // an6 (Right   Middle)
                    offsetX = videoWidth - R - widthLine; // Right
                    offsetY = (videoHeight - heightLine) / 2; // Middle
                }
                case 7 -> {
                    // an7 (Left    Top)
                    offsetX = L; // Left
                    offsetY = (V*2 + heightLine) / 2 + heightLine; // Top
                }
                case 8 -> {
                    // an8 (Center  Top)
                    offsetX = (videoWidth - widthLine) / 2; // Center
                    offsetY = (V*2 + heightLine) / 2 + heightLine; // Top
                }
                case 9 -> {
                    // an9 (Right   Top)
                    offsetX = videoWidth - R - widthLine; // Right
                    offsetY = (V*2 + heightLine) / 2 + heightLine; // Top
                }
            }
        }else{
            switch(align){
                case 1 -> {
                    // an1 (Left    Bottom)
                    offsetX = 0; // Left                
                    offsetY = heightLine - heightLine; // Bottom
                }
                case 2 -> {
                    // an2 (Center  Bottom)
                    offsetX = -widthLine / 2; // Center
                    offsetY = heightLine - heightLine; // Bottom
                }
                case 3 -> {
                    // an3 (Right   Bottom)
                    offsetX = -widthLine; // Right
                    offsetY = heightLine - heightLine;// Bottom
                }
                case 4 -> {
                    // an4 (Left    Middle)
                    offsetX = 0; // Left
                    offsetY = (heightLine - heightLine) / 2; // Middle
                }
                case 5 -> {
                    // an5 (Center  Middle)
                    offsetX = -widthLine / 2; // Center
                    offsetY = (heightLine - heightLine) / 2; // Middle
                }
                case 6 -> {
                    // an6 (Right   Middle)
                    offsetX = -widthLine; // Right
                    offsetY = (heightLine - heightLine) / 2; // Middle
                }
                case 7 -> {
                    // an7 (Left    Top)
                    offsetX = 0; // Left
                    offsetY = heightLine; // Top
                }
                case 8 -> {
                    // an8 (Center  Top)
                    offsetX = -widthLine / 2; // Center
                    offsetY = heightLine; // Top
                }
                case 9 -> {
                    // an9 (Right   Top)
                    offsetX = -widthLine; // Right
                    offsetY = heightLine; // Top
                }
            }
        }


        x += offsetX;
        y += offsetY;

        return new Point2D(x, y);
    }
    
}
