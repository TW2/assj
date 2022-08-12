/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

import java.awt.Shape; // Shape as Rectangle2D

/**
 *
 * @author util2
 */
public class ClipRectangle extends TagAbstract<Shape> {
    
    public ClipRectangle() {
        init();
    }
    
    private void init(){
        name = "clip";
        tag = "\\\\clip\\((?<paramA>\\d+\\.?\\d?),(?<paramB>\\d+\\.?\\d?),(?<paramC>\\d+\\.?\\d?),(?<paramD>\\d+\\.?\\d?)\\)";
        type = TagEnum.ClipRect;
        data = null;
    }
}
