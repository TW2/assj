/*
 * Copyright (C) 2021 util2
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

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.wingate.assj.AssTime;
import org.wingate.assj.tag.AnimationAssistant;
import org.wingate.assj.tag.Coordinates;
import org.wingate.assj.tag.FadingComplexAssistant;
import org.wingate.assj.tag.FadingNormalAssistant;
import org.wingate.assj.tag.MovementAssistant;

/**
 *
 * @author util2
 */
public class TagSetter {
    
    public TagSetter() {
    }
    
    public static List<TagAbstract> getTags(String s){
        return getTags(new TagLetter("", Arrays.asList(s.split("\\\\"))));
    }
    
    public static List<TagAbstract> getTags(TagLetter tl){
        List<TagAbstract> tas = new ArrayList<>();        
        
        Pattern p;
        Matcher m;
        
        TagAbstract neo;
        
        for(TagAbstract<?> ta : TagCollection.getTags()){
            p = Pattern.compile(ta.getTag());
            
            for (String z : tl.getTags()) {
                m = p.matcher("\\" + z);
            
                while(m.find()){

                    neo = ta;                        

                    switch(ta.getName()){
                        // Outer tags
                        case "a" -> {
                            neo.setData(Integer.parseInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "an" -> {
                            neo.setData(Integer.parseInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "pos" -> {
                            float a = Float.parseFloat(m.group("paramA"));
                            float b = Float.parseFloat(m.group("paramB"));
                            Coordinates coordinates = new Coordinates(a, b);
                            neo.setData(coordinates);
                            tas.add(neo);
                        }
                        case "move" -> {
                            float a = Float.parseFloat(m.group("paramA"));
                            float b = Float.parseFloat(m.group("paramB"));
                            float c = Float.parseFloat(m.group("paramC"));
                            float d = Float.parseFloat(m.group("paramD"));
                            long e = 0L, f = 0L;
                            if(m.groupCount() > 5){
                                String start = m.group("paramE");
                                String end = m.group("paramF");
                                e = start.isEmpty() == false & end.isEmpty() == false ? Long.parseLong(start) : 0L;
                                f = start.isEmpty() == false & end.isEmpty() == false ? Long.parseLong(end) : 0L;
                            }
                            Coordinates startCoordinates = new Coordinates(a, b);
                            Coordinates endCoordinates = new Coordinates(c, d);
                            MovementAssistant ma = new MovementAssistant(startCoordinates, endCoordinates);
                            if(m.groupCount() > 5 && e != f){
                                ma.setStart(AssTime.create(e));
                                ma.setEnd(AssTime.create(f));
                            }
                            neo.setData(ma);
                            tas.add(neo);
                        }
                        case "b" -> {
                            neo.setData(m.group("param").equals("1"));
                            tas.add(neo);
                        }
                        case "i" -> {
                            neo.setData(m.group("param").equals("1"));
                            tas.add(neo);
                        }
                        case "u" -> {
                            neo.setData(m.group("param").equals("1"));
                            tas.add(neo);
                        }
                        case "s" -> {
                            neo.setData(m.group("param").equals("1"));
                            tas.add(neo);
                        }
                        case "fn" -> {
                            neo.setData(m.group("param"));
                            tas.add(neo);
                        }
                        case "fs" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "fe" -> {
                            neo.setData(Integer.parseInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "q" -> {
                            neo.setData(Integer.parseInt(m.group("param")));
                        }
                        case "org" -> {
                            float a = Float.parseFloat(m.group("paramA"));
                            float b = Float.parseFloat(m.group("paramB"));
                            Coordinates coordinates = new Coordinates(a, b);
                            neo.setData(coordinates);
                            tas.add(neo);
                        }

                        // Inner tags
                        case "bord" -> {
                            neo.setData(Double.parseDouble(m.group("param")));
                            tas.add(neo);
                        }
                        case "xbord" -> {
                            neo.setData(Double.parseDouble(m.group("param")));
                            tas.add(neo);
                        }
                        case "ybord" -> {
                            neo.setData(Double.parseDouble(m.group("param")));
                            tas.add(neo);
                        }
                        case "shad" -> {
                            neo.setData(Double.parseDouble(m.group("param")));
                            tas.add(neo);
                        }
                        case "xshad" -> {
                            neo.setData(Double.parseDouble(m.group("param")));
                            tas.add(neo);
                        }
                        case "yshad" -> {
                            neo.setData(Double.parseDouble(m.group("param")));
                            tas.add(neo);
                        }
                        case "be" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "blur" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "fscx" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "fscy" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "fsp" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "fr" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "frx" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "fry" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "frz" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "fax" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "fay" -> {
                            neo.setData(Float.parseFloat(m.group("param")));
                            tas.add(neo);
                        }
                        case "c" -> {
                            neo.setData(bgrhexToColor(m.group("param")));
                            tas.add(neo);
                        }
                        case "1c" -> {
                            neo.setData(bgrhexToColor(m.group("param")));
                            tas.add(neo);
                        }
                        case "2c" -> {
                            neo.setData(bgrhexToColor(m.group("param")));
                            tas.add(neo);
                        }
                        case "3c" -> {
                            neo.setData(bgrhexToColor(m.group("param")));
                            tas.add(neo);
                        }
                        case "4c" -> {
                            neo.setData(bgrhexToColor(m.group("param")));
                            tas.add(neo);
                        }
                        case "alpha" -> {
                            neo.setData(alphahexToInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "1a" -> {
                            neo.setData(alphahexToInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "2a" -> {
                            neo.setData(alphahexToInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "3a" -> {
                            neo.setData(alphahexToInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "4a" -> {
                            neo.setData(alphahexToInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "k" -> {
                            neo.setData(Integer.parseInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "K" -> {
                            neo.setData(Integer.parseInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "kf" -> {
                            neo.setData(Integer.parseInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "ko" -> {
                            neo.setData(Integer.parseInt(m.group("param")));
                            tas.add(neo);
                        }
                        case "r" -> {
                            neo.setData(m.groupCount() > 1 ? m.group("param") : "");
                            tas.add(neo);
                        }
                        case "fad" -> {
                            int a = Integer.parseInt(m.group("paramA"));
                            int b = Integer.parseInt(m.group("paramB"));
                            FadingNormalAssistant fad = new FadingNormalAssistant(a, b);
                            neo.setData(fad);
                            tas.add(neo);
                        }
                        case "fade" -> {
                            int a = Integer.parseInt(m.group("paramA"));
                            int b = Integer.parseInt(m.group("paramB"));
                            int c = Integer.parseInt(m.group("paramC"));
                            AssTime d = AssTime.create(Long.parseLong(m.group("paramD")));
                            AssTime e = AssTime.create(Long.parseLong(m.group("paramE")));
                            AssTime f = AssTime.create(Long.parseLong(m.group("paramF")));
                            AssTime g = AssTime.create(Long.parseLong(m.group("paramG")));
                            FadingComplexAssistant fade = new FadingComplexAssistant(a, b, c, d, e, f, g);
                            neo.setData(fade);
                            tas.add(neo);
                        }
                        case "t" -> {
                            AssTime start = AssTime.create(0L), end = AssTime.create(0L);
                            float acceleration = 0f;
                            List<String> clips = getClips(z);
                            String str = removeClips(z);
                            
                            if(str.isEmpty()){
                                AnimationAssistant anim = new AnimationAssistant(start, end, acceleration);
                                for(String clip : clips){
                                    anim.getEffects().addAll(getTags(clip));
                                }
                                neo.setData(anim);
                                tas.add(neo);
                                continue;
                            }
                            
                            String x = str.replace("t(", "(");
                            x = x.substring(1, x.length()-1);
                            String[] tz = x.split(",");
                            if(tz[0].matches("\\d+.?\\d?") && tz.length == 2){
                                acceleration = Float.parseFloat(tz[0]);
                            }
                            if(tz[0].matches("\\d+") && tz[1].matches("\\d+") && (tz.length == 3 || tz.length == 4)){
                                start = AssTime.create(Long.parseLong(tz[0]));
                                end = AssTime.create(Long.parseLong(tz[1]));
                            }
                            if(tz[2].matches("\\d+.?\\d?") && tz.length == 4){
                                acceleration = Float.parseFloat(tz[2]);
                            }
                            AnimationAssistant anim = new AnimationAssistant(start, end, acceleration);                            
                            anim.setEffects(getTags(tz[tz.length-1]));
                            for(String clip : clips){
                                anim.getEffects().addAll(getTags(clip));
                            }
                            neo.setData(anim);
                            tas.add(neo);                            
                        }

                        // Misc. tags
                        case "clip" -> {
                            // On cherche la lettre m pour savoir
                            // si c'est un clip de dessin
                            // If there is a m letter this is a drawing
                            if(m.group(0).contains("m") == true){
                                // Drawing
                                neo.setData(commandsToShape(m.group("param"), 1f));
                                tas.add(neo);
                            }else if(m.group(0).contains(",") && ta.getType() == TagEnum.ClipRect){
                                // Rectangle
                                float x = Float.parseFloat(m.group("paramA"));
                                float y = Float.parseFloat(m.group("paramB"));
                                float w = Float.parseFloat(m.group("paramC"));
                                float h = Float.parseFloat(m.group("paramD"));
                                neo.setData(new Rectangle2D.Float(x, y, w, h));
                                tas.add(neo);
                            }
                        }
                        case "iclip" -> {
                            // On cherche la lettre m pour savoir
                            // si c'est un clip de dessin
                            // If there is a m letter this is a drawing
                            if(m.group(0).contains("m") == true){
                                // Drawing
                                neo.setData(commandsToShape(m.group("param"), 1f));
                                tas.add(neo);
                            }else if(m.group(0).contains(",") && ta.getType() == TagEnum.IClipRect){
                                // Rectangle
                                float x = Float.parseFloat(m.group("paramA"));
                                float y = Float.parseFloat(m.group("paramB"));
                                float w = Float.parseFloat(m.group("paramC"));
                                float h = Float.parseFloat(m.group("paramD"));
                                neo.setData(new Rectangle2D.Float(x, y, w, h));
                                tas.add(neo);
                            }
                        }
                        case "p" -> {
                            neo.setData(commandsToShape(m.group("paramB"), Float.parseFloat(m.group("paramA"))));
                            tas.add(neo);
                        }
                        case "pbo" -> {
                            neo.setData(Integer.parseInt(m.group("param")));
                            tas.add(neo);
                        }
                    }     
                }
            }
        }
        
        return tas;
    }
    
    private static Color bgrhexToColor(String hexa){
        int r = Integer.parseInt(hexa.substring(4), 16);
        int g = Integer.parseInt(hexa.substring(2, 4), 16);
        int b = Integer.parseInt(hexa.substring(0, 2), 16);
        return new Color(r, g, b);
    }
    
    private static Integer alphahexToInt(String hexa){
        return Integer.parseInt(hexa, 16);
    }
    
    private static Shape commandsToShape(String commands, float scale){
        GeneralPath gp = new GeneralPath();
        
        String[] t = commands.split(" ");
        
        int i = 0;
        Coordinates firstPoint;
        
        while(i < t.length){
            switch(t[i]){
                case "m" -> {
                    firstPoint = new Coordinates(
                            Float.parseFloat(t[i+1]),
                            Float.parseFloat(t[i+2])
                    );
                    gp.moveTo(firstPoint.getX(), firstPoint.getY());
                    i += 3;
                }
                case "n" -> {
                    gp.moveTo(Float.parseFloat(t[i+1]), Float.parseFloat(t[i+2]));
                    i += 3;
                }
                case "l" -> {
                    int j = 1;
                    boolean end = false;
                    while(end == false){
                        String a = t[i+j];
                        String b = t[i+j+1];
                        if(a.matches("\\d+\\.?\\d?") && b.matches("\\d+\\.?\\d?")){
                            gp.lineTo(Float.parseFloat(a), Float.parseFloat(b));
                            j += 2;
                        }else{
                            end = true;
                            i += j;
                        }
                    }
                }
                case "b" -> {
                    int j = 1;
                    boolean end = false;
                    while(end == false){
                        String a = t[i+j];
                        String b = t[i+j+1];
                        String c = t[i+j+2];
                        String d = t[i+j+3];
                        String e = t[i+j+4];
                        String f = t[i+j+5];
                        if(a.matches("\\d+\\.?\\d?") && b.matches("\\d+\\.?\\d?")
                                && c.matches("\\d+\\.?\\d?") && d.matches("\\d+\\.?\\d?")
                                && e.matches("\\d+\\.?\\d?") && f.matches("\\d+\\.?\\d?")){
                            gp.curveTo(
                                    Float.parseFloat(a), Float.parseFloat(b),
                                    Float.parseFloat(c), Float.parseFloat(d),
                                    Float.parseFloat(e), Float.parseFloat(f)
                            );
                            j += 6;
                        }else{
                            end = true;
                            i += j;
                        }
                    }
                }
                case "s" -> {
                    int j = 1;
                    boolean end = false;
                    boolean useNext = false;
                    float c_ = 0f;
                    float d_ = 0f;
                    float e_ = 0f;
                    float f_ = 0f;
                    while(end == false){
                        String a = t[i+j];
                        String b = t[i+j+1];
                        String c = t[i+j+2];
                        String d = t[i+j+3];
                        String e = t[i+j+4];
                        String f = t[i+j+5];
                        if(useNext == false && a.matches("\\d+\\.?\\d?") && b.matches("\\d+\\.?\\d?")
                                && c.matches("\\d+\\.?\\d?") && d.matches("\\d+\\.?\\d?")
                                && e.matches("\\d+\\.?\\d?") && f.matches("\\d+\\.?\\d?")){
                            c_ = Float.parseFloat(c);
                            d_ = Float.parseFloat(d);
                            e_ = Float.parseFloat(e);
                            f_ = Float.parseFloat(f);
                            gp.curveTo(
                                    Float.parseFloat(a), Float.parseFloat(b),
                                    Float.parseFloat(c), Float.parseFloat(d),
                                    Float.parseFloat(e), Float.parseFloat(f)
                            );
                            j += 6;
                            useNext = true;
                        }else if(useNext == true && a.matches("\\d+\\.?\\d?") && b.matches("\\d+\\.?\\d?")){
                            // p            = (1-t)^2 *P0 + 2*(1-t)*t*P1 + t*t*P2
                            // 2*(1-t)*t*P1 = (1-t)^2 *P0 + t*t*P2 - p
                            // TODO calculation of p1 (quad) with cubic to get correct quad
                            j += 2;
                        }else{
                            end = true;
                            i += j;
                        }
                    }
                }
                case "p" -> {
                    gp.lineTo(Float.parseFloat(t[i+1]), Float.parseFloat(t[i+2]));
                    i += 3;
                }
                case "c" -> {
                    gp.closePath();
                    i += 1;
                }
            }
        }
        
        AffineTransform tr = new AffineTransform();
        tr.setToScale(1/scale, 1/scale);
        return gp.createTransformedShape(tr);
    }
    
    private static List<String> getClips(String s){
        List<String> list = new ArrayList<>();
        
        Pattern p = Pattern.compile("\\\\i?clip\\(\\d+,\\d+,\\d+,\\d+\\)");
        Matcher m = p.matcher(s);
        
        while(m.find()){
            list.add(m.group(0));
        }
        
        return list;
    }
    
    private static String removeClips(String s){
        String str = s;
        List<Integer> starts = new ArrayList<>();
        List<Integer> ends = new ArrayList<>();
        
        Pattern p = Pattern.compile("\\\\i?clip\\(\\d+,\\d+,\\d+,\\d+\\)");
        Matcher m = p.matcher(s);
        
        while(m.find()){
            starts.add(m.regionStart());
            ends.add(m.regionEnd());
        }
        
        for(int i=starts.size() - 1; i>=0; i--){
            if(ends.get(i) == str.length() - 1){
                str = str.substring(0, starts.get(i));
            }else{
                str = str.substring(0, starts.get(i)) + str.substring(ends.get(i));
            }            
        }
        
        return str;
    }
}
