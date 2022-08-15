/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

import org.wingate.assj.core.TagEnum;
import org.wingate.assj.core.TagAbstract;
import java.awt.Shape; // Shape as Rectangle2D

/**
 *
 * @author util2
 */
public class ClipRectangleInvisibility extends TagAbstract<Shape> {
    
    public ClipRectangleInvisibility() {
        init();
    }
    
    private void init(){
        name = "iclip";
        tag = "\\\\iclip\\((?<paramA>\\d+\\.?\\d?),(?<paramB>\\d+\\.?\\d?),(?<paramC>\\d+\\.?\\d?),(?<paramD>\\d+\\.?\\d?)\\)";
        type = TagEnum.IClipRect;
        data = null;
    }
}
