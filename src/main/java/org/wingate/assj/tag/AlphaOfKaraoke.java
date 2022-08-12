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
public class AlphaOfKaraoke extends TagAbstract<Integer> {
    
    public AlphaOfKaraoke() {
        init();
    }
    
    private void init(){
        name = "2a";
        tag = "\\\\2a&H(?<param>[0-9A-Fa-f]{2})&";
        type = TagEnum.AlphaKaraoke;
        data = 0;
    }
}
