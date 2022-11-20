/*
 * Copyright (C) 2022 util2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.wingate.assj.test;

import java.util.concurrent.TimeUnit;
import javax.swing.Timer;
import org.wingate.assj.ASS;
import org.wingate.assj.listen.SubEvent;
import org.wingate.assj.listen.SubsImageEvent;
import org.wingate.assj.listen.SubsListener;

/**
 *
 * @author util2
 */
public class AdvancedUsage {

    private ASS ass = null;
    private SubEvent controller = new SubEvent();
    
    private long nanosPosition = 0L;
    
    public AdvancedUsage() {
        
        // Listen
        controller.addSubsListener(new SubsListener() {
            @Override
            public void getImage(SubsImageEvent event) {
                System.out.println("Do what you want with image!");
            }
        });
        
        // Update with 
        new Timer(50, e -> {
            if (ass != null) {
                //From timer -> millis
                //Required -> nanos
                nanosPosition += TimeUnit.MILLISECONDS.toNanos(50L);
                controller.createFX(ass, nanosPosition);
            }
        }).start();
    }
    
}
