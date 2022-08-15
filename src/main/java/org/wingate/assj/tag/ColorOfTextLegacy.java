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
public class ColorOfTextLegacy extends TagAbstract<Color> {
    
    public ColorOfTextLegacy() {
        init();
    }
    
    private void init(){
        name = "c";
        tag = "\\\\c&H(?<param>\\d{6})&";
        type = TagEnum.ColorTextLegacy;
        data = Color.white;
    }
}
