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
public class ClipDrawingInvisibility extends TagAbstract<Shape> {
    
    public ClipDrawingInvisibility() {
        init();
    }
    
    private void init(){
        name = "iclip";
        tag = "\\\\iclip\\((?<param>[^\\)]+)";
        type = TagEnum.IClipDrawing;
        data = null;
    }
}
