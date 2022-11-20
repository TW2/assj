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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javax.imageio.ImageIO;
import org.wingate.assj.ASS;
import org.wingate.assj.AssTime;
import org.wingate.assj.Assj;
import org.wingate.assj.core.Render;

/**
 *
 * @author util2
 */
public class BasicUsage {
    
    public static void main(String[] args) {
        try{
            long before = System.currentTimeMillis();

            ASS ass = ASS.Read(args[0]);
            File output = new File(args[1]);
            long nanos = Long.parseLong(args[2]);

            // Initialize JavaFX
            JFXPanel fxPanel = new JFXPanel();

            // Get images
            Platform.runLater(() -> {
                Render r = new Render();
                List<BufferedImage> images = r.getImages(ass, nanos);

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

                try {
                    ImageIO.write(blended, "png", output);
                } catch (IOException ex) {
                    Logger.getLogger(Assj.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            long elapsed = System.currentTimeMillis() - before;
            System.out.println(String.format(
                    "Time elapsed: %fs",
                    AssTime.getLengthInSeconds(AssTime.create(elapsed))
            ));

            Platform.exit();
        }catch(NumberFormatException ex){
            Logger.getLogger(Assj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
