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

/**
 *
 * @author util2
 */
public class Letter {
    
    private int letterInnerIndex;
    private int letterOuterIndex;
    private int syllableIndex;
    
    private long absoluteStart;
    private long absoluteEnd;
    private long absoluteMiddle;
    private long absoluteDuration;
    
    private long relativeStart;
    private long relativeEnd;
    private long relativeMiddle;
    private long relativeDuration;
    
    private double letterWidth;
    private double letterHeight;
    private double letterAscent;
    private double letterDescent;
    private double letterLeading;
    private double letterAdvance;
    private double posLetLeft, posLetCenter, posLetRight;
    private double posLetTop, posLetMiddle, posLetBottom;
    private double posLetX, posLetY;
    
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

    private Letter() {
        
    }
    
    public static Letter create(Line line, Syllable syllable, String tags, String syl,
            long time, long startTime, int innerIndex, int outerIndex){
        Letter k = new Letter();
        
        k.letterInnerIndex = innerIndex;
        k.letterOuterIndex = outerIndex;
        k.syllableIndex = syllable.getSyllableIndex();
        
        k.relativeStart = startTime;
        k.relativeEnd = startTime + time;
        k.relativeDuration = time;
        k.relativeMiddle = startTime + time /2;
        
        k.absoluteStart = syllable.getAbsoluteStart() + startTime;
        k.absoluteEnd = k.absoluteStart + time;
        k.absoluteDuration = time;
        k.absoluteMiddle = k.absoluteStart + time / 2;
        
        // TODO
        k.letterWidth = 0d;
        k.letterHeight = 0d;
        k.letterAscent = 0d;
        k.letterDescent = 0d;
        k.letterAdvance = 0d;
        
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
        
        // Positions
        // [0] = width
        // [1] = height
        // [2] = ascent
        // [3] = leading
        // [4] = descent        
        AssSize assSize = line.getAssSize();
        double[] d = assSize.getDimension(syl, line.getAssEvent().getStyle().getFont());
        k.letterWidth = d[0]; // Width
        k.letterHeight = d[1]; // Height
        k.letterAscent = d[2];
        k.letterLeading = d[3];
        k.letterDescent = d[4];
        k.letterAdvance = 0d;
        k.posLetX = syllable.getPosSylX(); // X
        k.posLetY = assSize.getY(); // Y
        k.posLetTop = line.getPosTop(); // Top
        k.posLetMiddle = line.getPosMiddle(); // Middle
        k.posLetBottom = line.getPosBottom(); // Bottom
        
        for(int i=0; i<syllable.getLetters().size(); i++){
            Letter l = syllable.getLetters().get(i);
            if(i<=innerIndex){
                k.posLetX += l.letterWidth; // X
                k.posLetLeft = k.posLetX; // Left
                k.posLetCenter = k.posLetX + k.letterWidth / 2; // Center
                k.posLetRight = k.posLetX + k.letterWidth; // Right                
            }else{
                break;
            }
        }
        
        return k;
    }

    public int getLetterInnerIndex() {
        return letterInnerIndex;
    }

    public int getLetterOuterIndex() {
        return letterOuterIndex;
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

    public double getLetterWidth() {
        return letterWidth;
    }

    public double getLetterHeight() {
        return letterHeight;
    }

    public double getLetterAscent() {
        return letterAscent;
    }

    public double getLetterDescent() {
        return letterDescent;
    }

    public double getLetterLeading() {
        return letterLeading;
    }

    public double getLetterAdvance() {
        return letterAdvance;
    }

    public String getTags() {
        return tags;
    }

    public String getStripped() {
        return stripped;
    }

    public double getPosLetLeft() {
        return posLetLeft;
    }

    public double getPosLetCenter() {
        return posLetCenter;
    }

    public double getPosLetRight() {
        return posLetRight;
    }

    public double getPosLetTop() {
        return posLetTop;
    }

    public double getPosLetMiddle() {
        return posLetMiddle;
    }

    public double getPosLetBottom() {
        return posLetBottom;
    }

    public double getPosLetX() {
        return posLetX;
    }

    public double getPosLetY() {
        return posLetY;
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
