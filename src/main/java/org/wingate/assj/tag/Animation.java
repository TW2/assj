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
public class Animation extends TagAbstract<AnimationAssistant> {
    
    public Animation() {
        init();
    }
    
    private void init(){
        name = "t";
        tag = "\\\\t(?<paramA>\\d?),?(?<paramB>\\d?),?(?<paramC>\\d?\\.?\\d?),?(?<paramD>.+)";
        type = TagEnum.Animation;
        data = new AnimationAssistant();
    }
}
