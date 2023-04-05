/*
 * Copyright (C) 2023 util2
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
package org.wingate.assj.kara;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.wingate.assj.ASS;
import org.wingate.assj.AssEvent;
import org.wingate.assj.AssTime;

/**
 *
 * @author util2
 */
public class Line {
    
    private AssEvent assEvent;
    
    private long start;
    private long end;
    private long middle;
    private long duration;
    
    private AssSize assSize;
    private double width;
    private double height;
    private double posLeft, posCenter, posRight;
    private double posTop, posMiddle, posBottom;
    private double posX, posY;
    
    private String stripped;
    private int layer;
    private String style;
    private String actorOrName;
    private int marginL;
    private int marginR;
    private int marginV;
    private int marginT;
    private int marginB;
    private int lineIndex;
    
    private final List<Syllable> syllables = new ArrayList<>();
    
    private Line(){
        
    }
    
    public static Line create(AssEvent ev){
        Line k = new Line();
        
        ASS ass = ev.getAss();
        k.assEvent = ev;
        
        long s = AssTime.toMillisecondsTime(ev.getStartTime());
        long e = AssTime.toMillisecondsTime(ev.getEndTime());
        
        k.start = Math.min(s, e);
        k.end = Math.max(s, e);
        k.duration = k.end - k.start;
        k.middle = k.start + k.duration / 2;
        
        // TODO
        k.width = 0d;
        k.height = 0d;
        
        // Stripped - remove tags
        try{
            k.stripped = ev.getText().replaceAll("\\{[^\\}]+\\}", "");
        }catch(Exception exc){
            k.stripped = ev.getText();
        }
        
        k.layer = ev.getLayer();
        k.style = ev.getStyle().getName();
        k.actorOrName = ev.getName();
        k.marginL = ev.getMarginL() > 0 ? ev.getMarginL() : ev.getStyle().getMarginL();
        k.marginR = ev.getMarginR() > 0 ? ev.getMarginR() : ev.getStyle().getMarginR();
        k.marginV = ev.getMarginV() > 0 ? ev.getMarginV() : ev.getStyle().getMarginV();
        k.marginT = ev.getStyle().getMarginT();
        k.marginB = ev.getStyle().getMarginB();
        k.lineIndex = ev.getEventIndex();
        
        // Separate syllable
        Pattern p = Pattern.compile("(?<tags>\\{[^\\}]+\\})(?<syllable>[^\\{]?)");
        Matcher m = p.matcher(ev.getText());
        
        long karaokeStart = 0L;
        int index = 0;
        
        while(m.find()){
            String tags = m.group("tags");
            String syllable = m.group("syllable");
            long dur = 0L;
            
            // Find duration
            if(tags.contains("\\k") == true){
                Pattern p2 = Pattern.compile("\\\\k(?<time>\\d+)");
                Matcher m2 = p2.matcher(tags);
                
                if(m.find()) dur = Long.parseLong(m2.group("time")) * 10L;
            }
            
            Syllable syl = Syllable.create(k, tags, syllable, dur, karaokeStart, index);
            k.syllables.add(syl);
            
            karaokeStart += dur;
            index++;
        }
        
        // Positions
        k.assSize = AssSize.create(ass, ev);
        k.width = k.assSize.getLineWidth(); // Width
        k.height = k.assSize.getLineHeight(); // Height
        k.posX = k.assSize.getX(); // X
        k.posY = k.assSize.getY(); // Y
        k.posLeft = k.posX; // Left
        k.posCenter = k.posX + k.width / 2; // Center
        k.posRight = k.posX + k.width; // Right
        k.posTop = k.posY - k.height; // Top
        k.posMiddle = k.posY - k.height / 2; // Middle
        k.posBottom = k.posY; // Bottom
        
        return k;
    }

    public AssEvent getAssEvent() {
        return assEvent;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public long getMiddle() {
        return middle;
    }

    public long getDuration() {
        return duration;
    }

    public AssSize getAssSize() {
        return assSize;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public List<Syllable> getSyllables() {
        return syllables;
    }

    public String getStripped() {
        return stripped;
    }

    public double getPosLeft() {
        return posLeft;
    }

    public double getPosCenter() {
        return posCenter;
    }

    public double getPosRight() {
        return posRight;
    }

    public double getPosTop() {
        return posTop;
    }

    public double getPosMiddle() {
        return posMiddle;
    }

    public double getPosBottom() {
        return posBottom;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public int getLayer() {
        return layer;
    }

    public String getStyle() {
        return style;
    }

    public String getActorOrName() {
        return actorOrName;
    }

    public int getMarginL() {
        return marginL;
    }

    public int getMarginR() {
        return marginR;
    }

    public int getMarginV() {
        return marginV;
    }

    public int getMarginT() {
        return marginT;
    }

    public int getMarginB() {
        return marginB;
    }

    public int getLineIndex() {
        return lineIndex;
    }
    
}
