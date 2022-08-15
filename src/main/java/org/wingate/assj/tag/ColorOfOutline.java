/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

import org.wingate.assj.core.TagEnum;
import org.wingate.assj.core.TagAbstract;
import java.awt.Color;

/**
 *
 * @author util2
 */
public class ColorOfOutline extends TagAbstract<Color> {
    
    public ColorOfOutline() {
        init();
    }
    
    private void init(){
        name = "3c";
        tag = "\\\\3c&H(?<param>[0-9A-Fa-f]{6})&";
        type = TagEnum.ColorOutline;
        data = Color.black;
    }
}
