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
public class KaraokeNormal extends TagAbstract<Integer> {
    
    public KaraokeNormal() {
        init();
    }
    
    private void init(){
        name = "k";
        tag = "\\\\k(?<param>\\d+)";
        type = TagEnum.KaraokeNormal;
        data = 0;
    }
}
