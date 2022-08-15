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
public class KaraokeFill extends TagAbstract<Integer> {
    
    public KaraokeFill() {
        init();
    }
    
    private void init(){
        name = "kf";
        tag = "\\\\kf(?<param>\\d+)";
        type = TagEnum.KaraokeFill;
        data = 0;
    }
}
