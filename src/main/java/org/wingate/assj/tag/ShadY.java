/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

import org.wingate.assj.core.TagEnum;
import org.wingate.assj.core.TagAbstract;

/**
 *
 * @author util2
 */
public class ShadY extends TagAbstract<Double> {
    
    public ShadY() {
        init();
    }
    
    private void init(){
        name = "yshad";
        tag = "\\\\yshad(?<param>\\d+\\.?\\d?)";
        type = TagEnum.ShadY;
        data = 0d;
    }
}
