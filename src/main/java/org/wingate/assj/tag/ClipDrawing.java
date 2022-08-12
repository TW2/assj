/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

import java.awt.Shape;

/**
 *
 * @author util2
 */
public class ClipDrawing extends TagAbstract<Shape> {
    
    public ClipDrawing() {
        init();
    }
    
    private void init(){
        name = "clip";
        tag = "\\\\clip\\((?<param>[^\\)]+)";
        type = TagEnum.ClipDrawing;
        data = null;
    }
}
