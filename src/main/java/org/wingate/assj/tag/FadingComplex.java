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
public class FadingComplex extends TagAbstract<FadingComplexAssistant> {
    
    public FadingComplex() {
        init();
    }
    
    private void init(){
        name = "fade";
        tag = "\\\\fade\\((?<paramA>\\d+),(?<paramB>\\d+),(?<paramC>\\d+),(?<paramD>\\d+),(?<paramE>\\d+),(?<paramF>\\d+),(?<paramG>\\d+)\\)";
        type = TagEnum.FadingComplex;
        data = new FadingComplexAssistant();
    }
}
