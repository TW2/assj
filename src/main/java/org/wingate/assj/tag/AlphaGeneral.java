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
public class AlphaGeneral extends TagAbstract<Integer> {
    
    public AlphaGeneral() {
        init();
    }
    
    private void init(){
        name = "alpha";
        tag = "\\\\alpha&H(?<param>[0-9A-Fa-f]{2})&";
        type = TagEnum.AlphaGeneral;
        data = 0;
    }
}
