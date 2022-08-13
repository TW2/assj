package org.wingate.assj;

import com.formdev.flatlaf.FlatLightLaf;
import org.wingate.assj.sample.SampleFrame;

/**
 *
 * @author util2
 */
public class Assj {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        // Launch sample
        FlatLightLaf.setup();
        SampleFrame sf = new SampleFrame();
        sf.setSize(1920, 1200);
        sf.setLocationRelativeTo(null);
        sf.setVisible(true);
    }
}
