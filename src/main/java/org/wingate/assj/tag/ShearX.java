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
public class ShearX extends TagAbstract<Double> {
    
    public ShearX() {
        init();
    }
    
    private void init(){
        name = "fax";
        tag = "\\\\fax(?<param>\\d+\\.?\\d?)";
        type = TagEnum.ShearX;
        data = 0d;
    }
}