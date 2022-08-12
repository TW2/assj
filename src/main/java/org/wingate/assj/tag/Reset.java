/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

import org.wingate.assj.AssStyle;

/**
 *
 * @author util2
 */
public class Reset extends TagAbstract<AssStyle> {
    
    public Reset() {
        init();
    }
    
    private void init(){
        name = "r";
        tag = "\\\\r(?<param>[^\\\\]?)";
        type = TagEnum.Reset;
        data = new AssStyle();
    }
}
