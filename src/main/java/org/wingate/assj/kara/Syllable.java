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

/**
 *
 * @author util2
 */
public class Syllable {
    
    private int syllableIndex;
    
    private long absoluteStart;
    private long absoluteEnd;
    private long absoluteMiddle;
    private long absoluteDuration;
    
    private long relativeStart;
    private long relativeEnd;
    private long relativeMiddle;
    private long relativeDuration;
    
    private double syllableWidth;
    private double syllableHeight;
    private double syllableAscent;
    private double syllableDescent;
    private double syllableLeading;
    private double syllableAdvance;
    private double posSylLeft, posSylCenter, posSylRight;
    private double posSylTop, posSylMiddle, posSylBottom;
    private double posSylX, posSylY;
    
    private String tags;
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
    
    private final List<Letter> letters = new ArrayList<>();
    
    private Syllable(){
        
    }
    
    public static Syllable create(Line line, String tags, String syl, long time, long startTime, int index){
        Syllable k = new Syllable();
        
        k.syllableIndex = index;
        
        k.relativeStart = startTime;
        k.relativeEnd = startTime + time;
        k.relativeDuration = time;
        k.relativeMiddle = startTime + time /2;
        
        k.absoluteStart = index == 0 ? line.getStart() : line.getStart() + startTime;
        k.absoluteEnd = k.absoluteStart + time;
        k.absoluteDuration = time;
        k.absoluteMiddle = k.absoluteStart + time / 2;
        
        k.tags = tags;
        k.stripped = syl;
        
        k.layer = line.getLayer();
        k.style = line.getStyle();
        k.actorOrName = line.getActorOrName();
        k.marginL = line.getMarginL();
        k.marginR = line.getMarginR();
        k.marginV = line.getMarginV();
        k.marginT = line.getMarginT();
        k.marginB = line.getMarginB();
        k.lineIndex = line.getLineIndex();
        
        int indexLetters = 0;
        int size, z;
        for(Syllable c : line.getSyllables()){
            size = c.getLetters().size();
            z = 0;
            while(z < size){
                indexLetters++;
                z++;
            }
        }
        
        long letterStart = 0L;
        int chars = syl.toCharArray().length;
        long minDur = (long)Math.floor(time / chars);
        long remain = time;
        for(int i=0; i<chars; i++){
            String ch = Character.toString(syl.toCharArray()[i]);
            long dur = i<chars-1 ? minDur : remain;
            Letter letter = Letter.create(line, k, tags, ch, dur, letterStart, i, indexLetters + i);
            k.letters.add(letter);
            remain -= minDur;
            letterStart += dur;
        }
        
        // Positions
        // [0] = width
        // [1] = height
        // [2] = ascent
        // [3] = leading
        // [4] = descent        
        AssSize assSize = line.getAssSize();
        double[] d = assSize.getDimension(syl, line.getAssEvent().getStyle().getFont());
        k.syllableWidth = d[0]; // Width
        k.syllableHeight = d[1]; // Height
        k.syllableAscent = d[2];
        k.syllableLeading = d[3];
        k.syllableDescent = d[4];
        k.syllableAdvance = 0d;
        k.posSylX = assSize.getX(); // X
        k.posSylY = assSize.getY(); // Y
        k.posSylTop = line.getPosTop(); // Top
        k.posSylMiddle = line.getPosMiddle(); // Middle
        k.posSylBottom = line.getPosBottom(); // Bottom
        
        for(int i=0; i<line.getSyllables().size(); i++){
            Syllable s = line.getSyllables().get(i);
            if(i<=index){
                k.posSylX += s.syllableWidth; // X
                k.posSylLeft = k.posSylX; // Left
                k.posSylCenter = k.posSylX + k.syllableWidth / 2; // Center
                k.posSylRight = k.posSylX + k.syllableWidth; // Right                
            }else{
                break;
            }
        }
        
        return k;
    }

    public int getSyllableIndex() {
        return syllableIndex;
    }

    public long getAbsoluteStart() {
        return absoluteStart;
    }

    public long getAbsoluteEnd() {
        return absoluteEnd;
    }

    public long getAbsoluteMiddle() {
        return absoluteMiddle;
    }

    public long getAbsoluteDuration() {
        return absoluteDuration;
    }

    public long getRelativeStart() {
        return relativeStart;
    }

    public long getRelativeEnd() {
        return relativeEnd;
    }

    public long getRelativeMiddle() {
        return relativeMiddle;
    }

    public long getRelativeDuration() {
        return relativeDuration;
    }

    public double getSyllableWidth() {
        return syllableWidth;
    }

    public double getSyllableHeight() {
        return syllableHeight;
    }

    public double getSyllableAscent() {
        return syllableAscent;
    }

    public double getSyllableDescent() {
        return syllableDescent;
    }

    public double getSyllableLeading() {
        return syllableLeading;
    }

    public double getSyllableAdvance() {
        return syllableAdvance;
    }

    public String getTags() {
        return tags;
    }

    public String getStripped() {
        return stripped;
    }

    public List<Letter> getLetters() {
        return letters;
    }

    public double getPosSylLeft() {
        return posSylLeft;
    }

    public double getPosSylCenter() {
        return posSylCenter;
    }

    public double getPosSylRight() {
        return posSylRight;
    }

    public double getPosSylTop() {
        return posSylTop;
    }

    public double getPosSylMiddle() {
        return posSylMiddle;
    }

    public double getPosSylBottom() {
        return posSylBottom;
    }

    public double getPosSylX() {
        return posSylX;
    }

    public double getPosSylY() {
        return posSylY;
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
