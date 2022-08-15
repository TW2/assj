/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

import org.wingate.assj.core.TagEnum;
import org.wingate.assj.core.TagAbstract;

/**
 *
 * @author util2
 */
public class AlphaOfShadow extends TagAbstract<Integer> {
    
    public AlphaOfShadow() {
        init();
    }
    
    private void init(){
        name = "4a";
        tag = "\\\\4a&H(?<param>[0-9A-Fa-f]{2})&";
        type = TagEnum.AlphaShadow;
        data = 0;
    }
}
