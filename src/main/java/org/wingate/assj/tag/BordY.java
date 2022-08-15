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
public class BordY extends TagAbstract<Double> {
    
    public BordY() {
        init();
    }
    
    private void init(){
        name = "ybord";
        tag = "\\\\ybord(?<param>\\d+\\.?\\d?)";
        type = TagEnum.BordY;
        data = 0d;
    }
}
