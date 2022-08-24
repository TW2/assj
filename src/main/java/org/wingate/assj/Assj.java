package org.wingate.assj;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javax.imageio.ImageIO;
import org.wingate.assj.core.Render;
import org.wingate.assj.sample.SampleFrame;

/**
 *
 * @author util2
 */
public class Assj {

    public static void main(String[] args) {
        switch(args.length) {
            case 0 -> {
                System.out.println("Please use command line like that:");
                System.out.println("java -jar Assj.jar \"<ass-path>\" \"<nanoseconds>\" ");
                System.out.println("Example:");     
                System.out.println("java -jar Assj.jar \"c:\\users\\GothicLolita\\Darling.ass\" \"19148236200\"");
                String cmd;
                try (Scanner sc = new Scanner(System.in)) {
                    System.out.println("Type 'demo' to launch the demo or 'quit' to stop. And press Enter.");
                    cmd = sc.next();
                }
                if(cmd.equalsIgnoreCase("demo")){
                    // Launch sample
                    FlatLightLaf.setup();
                    SampleFrame sf = new SampleFrame();
                    sf.setSize(1920, 1200);
                    sf.setLocationRelativeTo(null);
                    sf.setVisible(true);
                }else if(cmd.equalsIgnoreCase("quit")){
                    System.exit(0);
                }
            }
            case 2 -> {
                long before = System.currentTimeMillis();
                try{
                    ASS ass = ASS.Read(args[0]);
                    long nanos = Long.parseLong(args[1]);
                    
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
                            ImageIO.write(blended, "png", new File("img" + nanos + ".png"));
                        } catch (IOException ex) {
                            Logger.getLogger(Assj.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }catch(NumberFormatException exc){
                    
                }
                long elapsed = System.currentTimeMillis() - before;
                System.out.println(String.format(
                        "Time elapsed: %fms",
                        AssTime.getLengthInSeconds(AssTime.create(elapsed))
                ));
            }
        }
    }
}
