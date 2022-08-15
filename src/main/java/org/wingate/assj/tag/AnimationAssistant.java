/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

import org.wingate.assj.core.TagAbstract;
import java.util.ArrayList;
import java.util.List;
import org.wingate.assj.AssTime;

/**
 *
 * @author util2
 */
public class AnimationAssistant {
    
    private AssTime startTime = AssTime.create(0L);
    private AssTime endTime = AssTime.create(0L);
    
    private float acceleration = 0f;
    
    private List<TagAbstract> effects = new ArrayList<>();

    public AnimationAssistant() {
    }

    public AnimationAssistant(AssTime startTime, AssTime endTime, float acceleration) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.acceleration = acceleration;
    }

    public AssTime getStartTime() {
        return startTime;
    }

    public void setStartTime(AssTime startTime) {
        this.startTime = startTime;
    }

    public AssTime getEndTime() {
        return endTime;
    }

    public void setEndTime(AssTime endTime) {
        this.endTime = endTime;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public List<TagAbstract> getEffects() {
        return effects;
    }

    public void setEffects(List<TagAbstract> effects) {
        this.effects = effects;
    }
}
