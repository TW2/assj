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
public class AlignmentLegacy extends TagAbstract<Integer> {
    
    public AlignmentLegacy() {
        init();
    }
    
    private void init(){
        name = "a";
        tag = "\\\\a(?<param>\\d+)";
        type = TagEnum.AlignLegacy;
        data = 2;
    }
}