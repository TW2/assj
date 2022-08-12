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
public class KaraokeFillLegacy extends TagAbstract<Integer> {
    
    public KaraokeFillLegacy() {
        init();
    }
    
    private void init(){
        name = "K";
        tag = "\\\\K(?<param>\\d+)";
        type = TagEnum.KaraokeFillLegacy;
        data = 0;
    }
}
