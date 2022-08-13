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
package org.wingate.assj.sample;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author util2
 */
public class View extends JPanel {

    private List<BufferedImage> images = new ArrayList<>();
    
    public View() {
    }
    
    public void updateView(List<BufferedImage> images){
        this.images = images;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(new Color(63,128,255));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        for(BufferedImage img : images){
            g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        }
    }
    
}
