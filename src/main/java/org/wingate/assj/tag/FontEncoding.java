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
public class FontEncoding extends TagAbstract<Integer> {
    
    public FontEncoding() {
        init();
    }
    
    private void init(){
        name = "fe";
        tag = "\\\\fe(?<param>\\d+)";
        type = TagEnum.FontEncoding;
        data = 1;
    }
}
