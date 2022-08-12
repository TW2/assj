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
public class AlphaOfOutline extends TagAbstract<Integer> {
    
    public AlphaOfOutline() {
        init();
    }
    
    private void init(){
        name = "3a";
        tag = "\\\\3a&H(?<param>[0-9A-Fa-f]{2})&";
        type = TagEnum.AlphaOutline;
        data = 0;
    }
}
