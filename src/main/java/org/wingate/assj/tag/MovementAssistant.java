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
public class MovementAssistant {
    
    private Coordinates startCoordinates = new Coordinates(0, 0);
    private Coordinates endCoordinates = new Coordinates(0, 0);
    
    private AssTime start = AssTime.create(0L);
    private AssTime end = AssTime.create(0L);

    public MovementAssistant() {
    }

    public MovementAssistant(Coordinates startCoordinates, Coordinates endCoordinates) {
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;
    }
    
    public MovementAssistant(Coordinates startCoordinates, Coordinates endCoordinates, AssTime start, AssTime end) {
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;
        this.start = start;
        this.end = end;
    }

    public Coordinates getStartCoordinates() {
        return startCoordinates;
    }

    public void setStartCoordinates(Coordinates startCoordinates) {
        this.startCoordinates = startCoordinates;
    }

    public Coordinates getEndCoordinates() {
        return endCoordinates;
    }

    public void setEndCoordinates(Coordinates endCoordinates) {
        this.endCoordinates = endCoordinates;
    }

    public AssTime getStart() {
        return start;
    }

    public void setStart(AssTime start) {
        this.start = start;
    }

    public AssTime getEnd() {
        return end;
    }

    public void setEnd(AssTime end) {
        this.end = end;
    }
    
    
}
