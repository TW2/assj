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
public class ShearY extends TagAbstract<Double> {
    
    public ShearY() {
        init();
    }
    
    private void init(){
        name = "fay";
        tag = "\\\\fay(?<param>\\d+\\.?\\d?)";
        type = TagEnum.ShearY;
        data = 0d;
    }
}