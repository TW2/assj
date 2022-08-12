/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

import org.wingate.assj.AssTime;

/**
 *
 * @author util2
 */
public class FadingComplexAssistant {
    
    private int beginningAlpha = 0;
    private int meantimeAlpha = 0;
    private int endingAlpha = 0;
    
    private AssTime startTimeBegin = AssTime.create(0L);
    private AssTime startTimeEnd = AssTime.create(0L);
    private AssTime endTimeBegin = AssTime.create(0L);
    private AssTime endTimeEnd = AssTime.create(0L);
    
    public FadingComplexAssistant() {
    }

    public FadingComplexAssistant(
            int beginningAlpha, int meantimeAlpha, int endingAlpha,
            AssTime startTimeBegin, AssTime startTimeEnd, 
            AssTime endTimeBegin, AssTime endTimeEnd) {
        
        this.beginningAlpha = beginningAlpha;
        this.meantimeAlpha = meantimeAlpha;
        this.endingAlpha = endingAlpha;
        this.startTimeBegin = startTimeBegin;
        this.startTimeEnd = startTimeEnd;
        this.endTimeBegin = endTimeBegin;
        this.endTimeEnd = endTimeEnd;
    }

    public int getBeginningAlpha() {
        return beginningAlpha;
    }

    public void setBeginningAlpha(int beginningAlpha) {
        this.beginningAlpha = beginningAlpha;
    }

    public int getMeantimeAlpha() {
        return meantimeAlpha;
    }

    public void setMeantimeAlpha(int meantimeAlpha) {
        this.meantimeAlpha = meantimeAlpha;
    }

    public int getEndingAlpha() {
        return endingAlpha;
    }

    public void setEndingAlpha(int endingAlpha) {
        this.endingAlpha = endingAlpha;
    }

    public AssTime getStartTimeBegin() {
        return startTimeBegin;
    }

    public void setStartTimeBegin(AssTime startTimeBegin) {
        this.startTimeBegin = startTimeBegin;
    }

    public AssTime getStartTimeEnd() {
        return startTimeEnd;
    }

    public void setStartTimeEnd(AssTime startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
    }

    public AssTime getEndTimeBegin() {
        return endTimeBegin;
    }

    public void setEndTimeBegin(AssTime endTimeBegin) {
        this.endTimeBegin = endTimeBegin;
    }

    public AssTime getEndTimeEnd() {
        return endTimeEnd;
    }

    public void setEndTimeEnd(AssTime endTimeEnd) {
        this.endTimeEnd = endTimeEnd;
    }
    
    
}
