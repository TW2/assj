/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

/**
 *
 * @author util2
 */
public class FadingNormalAssistant {
    
    private int beginning = 0;
    private int ending = 0;

    public FadingNormalAssistant() {
    }

    public FadingNormalAssistant(int beginning, int ending) {
        this.beginning = beginning;
        this.ending = ending;
    }

    public int getBeginning() {
        return beginning;
    }

    public void setBeginning(int beginning) {
        this.beginning = beginning;
    }

    public int getEnding() {
        return ending;
    }

    public void setEnding(int ending) {
        this.ending = ending;
    }
    
}
