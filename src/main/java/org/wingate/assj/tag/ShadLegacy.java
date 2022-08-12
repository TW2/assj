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
public class ShadLegacy extends TagAbstract<Double> {
    
    public ShadLegacy() {
        init();
    }
    
    private void init(){
        name = "shad";
        tag = "\\\\shad(?<param>\\d+\\.?\\d?)";
        type = TagEnum.ShadLegacy;
        data = 0d;
    }
}
