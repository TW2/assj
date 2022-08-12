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
public class ShadX extends TagAbstract<Double> {
    
    public ShadX() {
        init();
    }
    
    private void init(){
        name = "xshad";
        tag = "\\\\xshad(?<param>\\d+\\.?\\d?)";
        type = TagEnum.ShadX;
        data = 0d;
    }
}
