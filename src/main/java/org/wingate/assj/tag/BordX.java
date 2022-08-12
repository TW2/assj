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
public class BordX extends TagAbstract<Double> {
    
    public BordX() {
        init();
    }
    
    private void init(){
        name = "xbord";
        tag = "\\\\xbord(?<param>\\d+\\.?\\d?)";
        type = TagEnum.BordX;
        data = 0d;
    }
}
