/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

import java.awt.Color;

/**
 *
 * @author util2
 */
public class ColorOfText extends TagAbstract<Color> {
    
    public ColorOfText() {
        init();
    }
    
    private void init(){
        name = "1c";
        tag = "\\\\1c&H(?<param>[0-9A-Fa-f]{6})&";
        type = TagEnum.ColorText;
        data = Color.white;
    }    
}