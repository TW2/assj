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
package org.wingate.assj.listen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javax.swing.event.EventListenerList;
import org.wingate.assj.ASS;
import org.wingate.assj.core.Render;

/**
 *
 * @author util2
 */
public class SubEvent {

    public SubEvent() {
        // Initialize JavaFX
        JFXPanel fxPanel = new JFXPanel();
    }
    
    public void createFX(ASS ass, long nanos){
        try{
            // Get images
            Platform.runLater(() -> {
                Render r = new Render();
                List<BufferedImage> images = r.getImages(ass, nanos);
                
                BufferedImage subsImage = new BufferedImage(
                        Integer.parseInt(ass.getResX()),
                        Integer.parseInt(ass.getResY()),
                        BufferedImage.TYPE_INT_ARGB
                );

                if(images.isEmpty() == false){
                    BufferedImage blended = new BufferedImage(
                            images.get(0).getWidth(),
                            images.get(0).getHeight(),
                            BufferedImage.TYPE_INT_ARGB
                    );

                    Graphics2D g2d = blended.createGraphics();

                    for(BufferedImage img : images){
                        g2d.drawImage(img, 0, 0, null);
                    }

                    g2d.dispose();

                    subsImage = blended;
                }
                
                fireSubsImage(new SubsImageEvent(subsImage));
            });
        }catch(Exception ex){
            System.out.println("CreateFX error!");
        }        
    }
    
    // <editor-fold defaultstate="collapsed" desc="Evénements">
    
    private final EventListenerList listeners = new EventListenerList();
    
    public void addSubsListener(ISubsListener listener) {
        listeners.add(SubsListener.class, (SubsListener)listener);
    }

    public void removeSubsListener(ISubsListener listener) {
        listeners.remove(SubsListener.class, (SubsListener)listener);
    }

    public Object[] getListeners() {
        return listeners.getListenerList();
    }
    
    protected void fireSubsImage(SubsImageEvent event) {
        for(Object o : getListeners()){
            if(o instanceof SubsListener listen){
                listen.getImage(event);
                break;
            }
        }
    }
    
    // </editor-fold>
    
}
