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
public class BlurEdge extends TagAbstract<Float> {
    
    public BlurEdge() {
        init();
    }
    
    private void init(){
        name = "be";
        tag = "\\\\be(?<param>\\d+\\.?\\d?)";
        type = TagEnum.BlurEdge;
        data = 0f;
    }
}
