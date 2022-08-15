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
public class Movement extends TagAbstract<MovementAssistant> {
    
    public Movement() {
        init();
    }
    
    private void init(){
        name = "move";
        tag = "\\\\move\\((?<paramA>-?\\d+\\.?\\d?),(?<paramB>-?\\d+\\.?\\d?),(?<paramC>-?\\d+\\.?\\d?),(?<paramD>-?\\d+\\.?\\d?),?(?<paramE>-?\\d?),?(?<paramF>-?\\d?)\\)";
        type = TagEnum.Movement;
        data = new MovementAssistant();
    }
}
