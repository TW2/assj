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
public class ColorOfKaraoke extends TagAbstract<Color> {
    
    public ColorOfKaraoke() {
        init();
    }
    
    private void init(){
        name = "2c";
        tag = "\\\\2c&H(?<param>[0-9A-Fa-f]{6})&";
        type = TagEnum.ColorKaraoke;
        data = Color.red;
    }
}
