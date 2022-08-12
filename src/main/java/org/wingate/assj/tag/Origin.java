/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.assj.tag;

/**
 *
 * @author util2
 */
public class Origin extends TagAbstract<Coordinates> {
    
    public Origin() {
        init();
    }
    
    private void init(){
        name = "org";
        tag = "\\\\org\\((?<paramA>\\d+\\.?\\d?),(?<paramB>\\d+\\.?\\d?)\\)";
        type = TagEnum.Origin;
        data = new Coordinates();
    }
}
