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
package org.wingate.assj.tag;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Shear;
import org.wingate.assj.AssEvent;
import org.wingate.assj.AssTime;

/**
 *
 * @author util2
 */
public class ImageGenerator extends JFXPanel {
    
    private Font font = null;
    private final List<Point2D> pos = new ArrayList<>();
    private String s = null;
    
    public ImageGenerator() {
    }
    
    public List<BufferedImage> render(TagParameters p, long nanos){
        List<BufferedImage> images = new ArrayList<>();
        
        List<AssEvent> evtsNow = new ArrayList<>();
        for(AssEvent ev : p.getASS().getEvents()){
            long start = TimeUnit.MILLISECONDS.toNanos(AssTime.toMillisecondsTime(ev.getStartTime()));
            long end = TimeUnit.MILLISECONDS.toNanos(AssTime.toMillisecondsTime(ev.getEndTime()));
            if(start <= nanos && nanos < end){
                evtsNow.add(ev);
            }
        }
        
        for(AssEvent ev : evtsNow){
            Group root = new Group();
        
            double betweenLetters = 0d;

            calcFont(p);
            calcPosition(p, ev, nanos);

            TagLettersWith tlw = TagLettersWith.create(ev);
            int count = 0;
            for(TagLetter tl : tlw.getListWords()){
                // String
                String str = tl.getSentence();

                // Texte
                Text text = new Text(str);
                Text karaoke_text = new Text(str);
                List<Text> outline_texts = Outline.createXYOutline(str, p.getBord()[0], p.getBord()[1]).getTexts();
                List<Text> shadow_texts = Shadow.createXYShadow(str, p.getShad()[0], p.getShad()[1], p.getBord()[0], p.getBord()[1]).getTexts();

                // Fonte
                text.setFont(font);
                karaoke_text.setFont(font);
                for(Text t : outline_texts) { t.setFont(font); }
                for(Text t : shadow_texts) { t.setFont(font); }
                if(p.isUnderline()){
                    text.setUnderline(true);
                    karaoke_text.setUnderline(true);
                    for(Text t : outline_texts) { t.setUnderline(true); }
                    for(Text t : shadow_texts) { t.setUnderline(true); }
                }
                if(p.isStrikeout()){
                    text.setStrikethrough(true);
                    karaoke_text.setStrikethrough(true);
                    for(Text t : outline_texts) { t.setStrikethrough(true); }
                    for(Text t : shadow_texts) { t.setStrikethrough(true); }
                }

                // Nettoyage de la ligne pour la mesure (enlève les tags)
                Pattern pt = Pattern.compile("\\{[^\\}]+\\}([^\\{]+)");
                Matcher m = pt.matcher(ev.getText());
                String cleanText = "";
                while(m.find()){
                    cleanText = cleanText + m.group(1);
                }

                // Mesure de la ligne
                Text textMeasureLine = new Text(cleanText);
                textMeasureLine.setFont(font);
                if(p.isUnderline()) textMeasureLine.setUnderline(true);
                if(p.isStrikeout()) textMeasureLine.setStrikethrough(true);
                double lineWidth = textMeasureLine.getLayoutBounds().getWidth();
    //            double lineHeight = textMeasureLine.getLayoutBounds().getHeight();

                // Position
                Point2D point = pos.get(count);
                text.setTranslateX(point.getX() + betweenLetters);
                text.setTranslateY(point.getY());
                karaoke_text.setTranslateX(point.getX() + betweenLetters);
                karaoke_text.setTranslateY(point.getY());
                for(Text t : outline_texts) {
                    t.setTranslateX(point.getX() + betweenLetters);
                    t.setTranslateY(point.getY());
                }
                for(Text t : shadow_texts) {
                    t.setTranslateX(point.getX() + betweenLetters);
                    t.setTranslateY(point.getY());
                }

                // Color dans l'ordre Shadow > Outline > Karaoke > Text
                // Shadow
                for(Text t : shadow_texts){
                    t.setFill(applyColor(ev, ColorChoice.Shadow));
                    Shear shear = new Shear();
                    shear.setPivotX(lineWidth / 2 + point.getX());
                    shear.setPivotY(point.getY());
                    shear.setX(p.getShear()[0]);
                    shear.setY(p.getShear()[1]);
                    t.getTransforms().add(shear);
                }
                // Outline
                for(Text t : outline_texts){
                    t.setStroke(applyColor(ev, ColorChoice.Outline));
                    Shear shear = new Shear();
                    shear.setPivotX(lineWidth / 2 + point.getX());
                    shear.setPivotY(point.getY());
                    shear.setX(p.getShear()[0]);
                    shear.setY(p.getShear()[1]);
                    t.getTransforms().add(shear);
                }
                // Karaoke
                // Text
                text.setFill(applyColor(ev, ColorChoice.Text));
                Shear shear = new Shear();
                shear.setPivotX(lineWidth / 2 + point.getX());
                shear.setPivotY(point.getY());
                shear.setX(p.getShear()[0]);
                shear.setY(p.getShear()[1]);
                text.getTransforms().addAll(shear);

                root.getChildren().addAll(shadow_texts);
                root.getChildren().addAll(outline_texts);
                root.getChildren().add(text);

                betweenLetters += text.getLayoutBounds().getWidth();

                count++;
            }

            Scene scene = new Scene(root, p.getVideoWidth(), p.getVideoHeight(), true, SceneAntialiasing.BALANCED);
            scene.setFill(Color.TRANSPARENT);

            boolean fixedEyeAtCameraZero = false;
            PerspectiveCamera camera = new PerspectiveCamera(fixedEyeAtCameraZero);
            scene.setCamera(camera);

            setScene(scene);

            WritableImage screenshot = scene.snapshot(null);
            BufferedImage image = SwingFXUtils.fromFXImage(screenshot, null);
            
            images.add(image);
        }
        
        return images;
    }
    
    //--------------------------------------------------------------------------
    // FONTE
    //--------------------------------------------------------------------------
    
    private void calcFont(TagParameters p){
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        java.awt.Font textFont = new java.awt.Font(
                p.getFontName(),
                (p.isBold() ? java.awt.Font.BOLD : 0) + (p.isItalic() ? java.awt.Font.ITALIC : 0),
                (int)p.getFontSize()
        ).deriveFont(p.getFontSize());
        
        // Mesure
        FontMetrics fm = g2d.getFontMetrics(textFont);

        // Fausse taille :
        float falseSizeInPoints = textFont.getSize2D();
        float falseHeight = fm.getHeight();

        // Exemple : 12pt on est à taille = height
        //           ?pt  on est à taille = ascent + descent
        // Opération ?pt = 12pt * (ascent + descent) / height
        float trueSizeInPoints = falseSizeInPoints * (fm.getAscent() + fm.getDescent()) / falseHeight;
        
        // On applique la bonne taille :
        font = Font.font(
                p.getFontName(),
                p.isBold() ? FontWeight.BOLD : FontWeight.NORMAL,
                p.isItalic() ? FontPosture.ITALIC : FontPosture.REGULAR,
                trueSizeInPoints
        );
        
        g2d.dispose();
    }
    
    //--------------------------------------------------------------------------
    // POSITION
    //--------------------------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="Setup POS/MOVE">
    private void calcPosition(TagParameters p, AssEvent event, long nanos){
        
        TagLettersWith tlw = TagLettersWith.create(event);
        
        for(TagLetter tl : tlw.getListWords()){
            s = tl.getSentence();
            Text t = new Text(s);
            t.setFont(font);
            double widthLine = t.getBoundsInLocal().getWidth();
            double heightLine;
            double videoWidth = p.getVideoWidth();
            double videoHeight = p.getVideoHeight();

            if(widthLine >= videoWidth - (double)event.getMarginL() - (double)event.getMarginR()){
                switch(p.getWrapStyle()){
                    case 0 -> {
                        int indexOfNextSpace = tl.getSentence().indexOf(" ", (int)(Math.round(widthLine / 2d)));
                        String top = tl.getSentence().substring(0, indexOfNextSpace).trim();
                        String bottom = tl.getSentence().substring(indexOfNextSpace+1).trim();
                        s = String.format("%s\n%s", top, bottom).replace("\\N", "\n");
                    }
                    case 1 -> {
                        String[] u = tl.getSentence().split(" ");
                        double width; String test = "";
                        for(int i = 0; i<u.length; i++){
                            test += (i == 0) ? u[i] : " " + u[i];
                            Text text = new Text(test);
                            text.setFont(font);
                            width = text.getBoundsInLocal().getWidth();
                            if(width > videoWidth - event.getMarginL() - event.getMarginR()){
                                String top = tl.getSentence().substring(0, tl.getSentence().indexOf(" ", i-1));
                                String bottom = tl.getSentence().substring(tl.getSentence().indexOf(" ", i-1));
                                s = String.format("%s\n%s", top, bottom).replace("\\N", "\n");
                                break;
                            }
                        }
                    }
                    case 2 -> {
                        s = tl.getSentence().replace("\\N", "\n");
                    }
                    case 3 -> {
                        int indexOfPreviousSpace = tl.getSentence().lastIndexOf(" ", (int)(Math.round(widthLine / 2d)));
                        String top = tl.getSentence().substring(0, indexOfPreviousSpace).trim();
                        String bottom = tl.getSentence().substring(indexOfPreviousSpace+1).trim();
                        s = String.format("%s\n%s", top, bottom).replace("\\N", "\n");
                    }
                    default -> {
                        int indexOfNextSpace = tl.getSentence().indexOf(" ", (int)(Math.round(widthLine / 2d)));
                        String top = tl.getSentence().substring(0, indexOfNextSpace).trim();
                        String bottom = tl.getSentence().substring(indexOfNextSpace+1).trim();
                        s = String.format("%s\n%s", top, bottom).replace("\\N", "\n");
                    }
                }
            }

            t = new Text(s);
            t.setFont(font);
            widthLine = t.getBoundsInLocal().getWidth();
            heightLine = t.getBoundsInLocal().getHeight();

            double x = 0d, offsetX = 0d;
            double y = 0d, offsetY = 0d;        

            int align = p.getAlignment();

            if(p.getMove()[0] != p.getMove()[2] || p.getMove()[1] != p.getMove()[3]){
                long nanosStart, nanosEnd;
                if(p.getMove()[4] != p.getMove()[5]){
                    nanosStart = Math.round(Math.pow(p.getMove()[4], 6));
                    nanosEnd = Math.round(Math.pow(p.getMove()[5], 6));
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
                    x = p.getMove()[0] + (p.getMove()[2] - p.getMove()[0]) * percent;
                    y = p.getMove()[1] + (p.getMove()[3] - p.getMove()[1]) * percent;
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

            pos.add(new Point2D(x, y));
        }
    }
    
    public enum ColorChoice {
        Text, Karaoke, Outline, Shadow;
    }
    
    private Color applyColor(AssEvent ev, ColorChoice choice){
        java.awt.Color c = java.awt.Color.WHITE;
        switch(choice){
            case Text -> { c = ev.getStyle().getTextColor(); }
            case Karaoke -> { c = ev.getStyle().getKaraokeColor(); }
            case Outline -> { c = ev.getStyle().getBordColor(); }
            case Shadow -> { c = ev.getStyle().getShadColor(); }
        }
        
        double r = (double)c.getRed() / 255d;
        double g = (double)c.getGreen() / 255d;
        double b = (double)c.getBlue() / 255d;
        double a = (double)c.getAlpha() / 255d;
        
        return new Color(r, g, b, a);
    }
    
    static class Outline {
        
        private final Text xTextRight = new Text();
        private final Text xTextLeft = new Text();
        private final Text yTextBottom = new Text();
        private final Text yTextTop = new Text();
        private final Text xyText =  new Text();
        
        private final List<Text> texts = new ArrayList<>();
        
        private Outline() {
            
        }
        
        public static Outline createXYOutline(String s, double xSize, double ySize){
            Outline outline = new Outline();
            
            outline.xTextRight.setText(s);
            outline.xTextLeft.setText(s);
            outline.yTextBottom.setText(s);
            outline.yTextTop.setText(s);
            outline.xyText.setText(s);

            if(xSize > 0d){
                outline.xTextRight.setTranslateX(xSize);
                outline.xTextRight.setStrokeType(StrokeType.INSIDE);
                outline.xTextRight.setStrokeWidth(xSize);

                outline.xTextLeft.setTranslateX(-xSize);
                outline.xTextLeft.setStrokeType(StrokeType.INSIDE);
                outline.xTextLeft.setStrokeWidth(xSize);
                
                outline.texts.add(outline.xTextRight);
                outline.texts.add(outline.xTextLeft);
            }

            if(ySize > 0d){
                outline.yTextBottom.setTranslateY(ySize);
                outline.yTextBottom.setStrokeType(StrokeType.INSIDE);
                outline.yTextBottom.setStrokeWidth(ySize);
                
                outline.yTextTop.setTranslateY(-ySize);
                outline.yTextTop.setStrokeType(StrokeType.INSIDE);
                outline.yTextTop.setStrokeWidth(ySize);
                
                outline.texts.add(outline.yTextBottom);
                outline.texts.add(outline.yTextTop);
            }

            if(xSize <= 0d && ySize <= 0d){
                outline.xyText.setStrokeType(StrokeType.OUTSIDE);
                outline.xyText.setStrokeWidth(xSize);
                
                outline.texts.add(outline.xyText);
            }

            return outline;
        }

        public List<Text> getTexts() {
            return texts;
        }        
    }
    
    static class Shadow {
        
        private final Text xTextRight = new Text();
        private final Text xTextLeft = new Text();
        private final Text yTextBottom = new Text();
        private final Text yTextTop = new Text();
        private final Text xyText =  new Text();
        
        private final List<Text> texts = new ArrayList<>();
        
        private Shadow() {
            
        }
        
        public static Shadow createXYShadow(String s, double xSize, double ySize, double ox, double oy){
            Shadow shadow = new Shadow();
            
            double outlineX = ox;
            double outlineY = oy;
            
            shadow.xTextRight.setText(s);
            shadow.xTextLeft.setText(s);
            shadow.yTextBottom.setText(s);
            shadow.yTextTop.setText(s);
            shadow.xyText.setText(s);

            if(xSize > 0d){
                shadow.xTextRight.setTranslateX(xSize + outlineX);
                shadow.xTextRight.setStrokeType(StrokeType.INSIDE);
                shadow.xTextRight.setStrokeWidth(xSize + outlineX);

                shadow.xTextLeft.setTranslateX(-xSize - outlineX);
                shadow.xTextLeft.setStrokeType(StrokeType.INSIDE);
                shadow.xTextLeft.setStrokeWidth(xSize + outlineX);
                
                shadow.texts.add(shadow.xTextRight);
                shadow.texts.add(shadow.xTextLeft);
            }

            if(ySize > 0d){
                shadow.yTextBottom.setTranslateY(ySize + outlineY);
                shadow.yTextBottom.setStrokeType(StrokeType.INSIDE);
                shadow.yTextBottom.setStrokeWidth(ySize + outlineY);
                
                shadow.yTextTop.setTranslateY(-ySize - outlineY);
                shadow.yTextTop.setStrokeType(StrokeType.INSIDE);
                shadow.yTextTop.setStrokeWidth(ySize + outlineY);
                
                shadow.texts.add(shadow.yTextBottom);
                shadow.texts.add(shadow.yTextTop);
            }

            if(xSize <= 0d && ySize <= 0d){
                shadow.xyText.setStrokeType(StrokeType.OUTSIDE);
                shadow.xyText.setStrokeWidth(xSize);
                shadow.xyText.setTranslateX(outlineX);
                shadow.xyText.setTranslateY(outlineY);
                
                shadow.texts.add(shadow.xyText);
            }

            return shadow;
        }

        public List<Text> getTexts() {
            return texts;
        }        
    }
}
