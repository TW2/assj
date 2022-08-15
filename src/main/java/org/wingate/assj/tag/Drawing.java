/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

import org.wingate.assj.core.TagEnum;
import org.wingate.assj.core.TagAbstract;
import java.awt.Shape;

/**
 *
 * @author util2
 */
public class Drawing extends TagAbstract<Shape> {
    
    public Drawing() {
        init();
    }
    
    private void init(){
        name = "p";
        tag = "\\\\p(?<paramA>\\d+)\\}(?<paramB>[^\\{]+)\\{\\\\p0\\}";
        type = TagEnum.Drawing;
        data = null;
    }
}
