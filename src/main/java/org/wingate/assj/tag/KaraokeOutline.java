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
public class KaraokeOutline extends TagAbstract<Integer> {
    
    public KaraokeOutline() {
        init();
    }
    
    private void init(){
        name = "ko";
        tag = "\\\\ko(?<param>\\d+)";
        type = TagEnum.KaraokeOutline;
        data = 0;
    }
}
