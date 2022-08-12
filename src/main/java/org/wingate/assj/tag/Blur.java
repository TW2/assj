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
public class Blur extends TagAbstract<Float> {
    
    public Blur() {
        init();
    }
    
    private void init(){
        name = "blur";
        tag = "\\\\blur(?<param>\\d+\\.?\\d?)";
        type = TagEnum.Blur;
        data = 0f;
    }
}
