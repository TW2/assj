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
package org.wingate.assj.core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Shear;
import org.wingate.assj.ASS;
import org.wingate.assj.AssEvent;
import org.wingate.assj.AssStyle;
import org.wingate.assj.AssTime;

/**
 *
 * @author util2
 */
public class Render extends JFXPanel {
    
    public Render(){
        
    }
    
    public static void main(String[] args) {
        System.out.println("START");
        
        ASS ass = ASS.NoFileToLoad();
        
        AssStyle style = new AssStyle();
        style.setFontname("Tahoma");
        style.setFontsize(25d);
        style.setAlignment(1);
        
        AssEvent event = new AssEvent();
        event.setText("{\\org(10000,10)\\frz0.02\\1c&HFF00FF&\\3c&HFFFFFF&}Imbé{\\1c&H0000FF&}cile{\\r} heureux !");
        event.setLayer(1);
        event.setLineType(AssEvent.LineType.Dialogue);
        event.setStartTime(AssTime.create(0L));
        event.setEndTime(AssTime.create(1000L));
        event.setStyle(style);
        
        ass.getStyles().put(style.getName(), style);
        ass.getEvents().add(event);
        
        List<FxChar> fxChars = FxCharList.getFxChars(ass, event,
                TimeUnit.MILLISECONDS.toNanos(500L)).getFxCharList();
        
        for(FxChar fc : fxChars){            
            System.out.println("C: " + fc.getCharacter() + "; tags: " + fc.getTags());
        }
        
        System.out.println("EXIT");
    }
    
    public List<BufferedImage> getImages(ASS ass, long nanos){
        return getImages(ass, nanos, 0L, 0L);
    }
    
    public List<BufferedImage> getImages(ASS ass, long nanos, long startlimit, long stoplimit){
        // Liste à retourner
        List<BufferedImage> images = new ArrayList<>();
        
        // On traite tous les événements
        for(AssEvent ev : ass.getEvents()){
            if(ev.getLineType() != AssEvent.LineType.Dialogue ) continue;
            long start = TimeUnit.MILLISECONDS.toNanos(AssTime.toMillisecondsTime(ev.getStartTime()));
            long end = TimeUnit.MILLISECONDS.toNanos(AssTime.toMillisecondsTime(ev.getEndTime()));
            if(startlimit != 0L || stoplimit != 0L){
                if(startlimit <= nanos && nanos < stoplimit){
                    images.add(render(ass, ev, nanos));
                }
            }else if(start <= nanos && nanos < end){
                images.add(render(ass, ev, nanos));
            }
        }
        
        // TODO prendre en charge les couches
        
        return images;
    }
    
    private BufferedImage render(ASS ass, AssEvent ev, long nanos){
        BufferedImage img = new BufferedImage(
                Integer.parseInt(ass.getResX()),
                Integer.parseInt(ass.getResY()),
                BufferedImage.TYPE_INT_ARGB
        );
                
        Group root = new Group();

        List<FxChar> fxChars = FxCharList.getFxChars(ass, ev, nanos).getFxCharList();
        
        if(fxChars.isEmpty()) return img;
        
        double videoWidth = fxChars.get(0).getVideoWidth();
        double videoHeight = fxChars.get(0).getVideoHeight();
        
        double betweenLetters = fxChars.get(0).getPath().getTranslateX();
        double lineLetters = fxChars.get(0).getPath().getTranslateY();
        
        // We have a preconfigured path for each char (included punctuation)
        for(FxChar fc : fxChars){
            
            // JavaFX Path for this char
            Path path = fc.getPath();
            
            // Apply position
            path.setTranslateX(betweenLetters);
            path.setTranslateY(lineLetters);
            
            // Apply underline, strikeout
            
            
            // Apply Shear fax, fay
            Shear shear = new Shear();
            shear.setPivotX(Double.parseDouble(ass.getResX()));
            shear.setPivotY(fc.getMove()[1]);
            shear.setX(fc.getShear()[0]);
            shear.setY(fc.getShear()[1]);
            path.getTransforms().add(shear);
            
            // Couleur de la forme et transparence
            // Apply Shadow color
            path.setFill(applyColor(ev, fc, ColorChoice.Shadow));
            
            // Apply Outline color
            path.setStrokeType(StrokeType.OUTSIDE);
            path.setStrokeWidth(videoHeight / 576d * fc.getBord()[1]); // 384x288
            path.setStroke(applyColor(ev, fc, ColorChoice.Outline));
            
            // Apply Karaoke color
            path.setFill(applyColor(ev, fc, ColorChoice.Karaoke));
            
            // Apply Text color
            path.setFill(applyColor(ev, fc, ColorChoice.Text));
            
            root.getChildren().add(path);

            betweenLetters += fc.isSpace() ? fc.getSpaceWidth() : path.getLayoutBounds().getWidth();
        }

        Scene scene = new Scene(root, videoWidth, videoHeight, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.TRANSPARENT);

        boolean fixedEyeAtCameraZero = false;
        PerspectiveCamera camera = new PerspectiveCamera(fixedEyeAtCameraZero);
        scene.setCamera(camera);

        setScene(scene);

        WritableImage screenshot = scene.snapshot(null);
        img = SwingFXUtils.fromFXImage(screenshot, null);
        
        return img;
    }
    
    public enum ColorChoice {
        Text, Karaoke, Outline, Shadow;
    }
    
    private java.awt.Color mixColorAndAlpha(java.awt.Color c, int alpha){
        return new java.awt.Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
    }
    
    private Color applyColor(AssEvent ev, FxChar fc, ColorChoice choice){
        java.awt.Color c = java.awt.Color.WHITE;
        switch(choice){
            case Text -> {
                c = mixColorAndAlpha(ev.getStyle().getTextColor(), ev.getStyle().getTextAlphaStr());
                try{ c = mixColorAndAlpha(fc.getTextColor(), Math.round(fc.getTextAlpha() * 255f)); }
                catch(Exception exc){ }
            }
            case Karaoke -> {
                c = mixColorAndAlpha(ev.getStyle().getKaraokeColor(), ev.getStyle().getKaraokeAlphaStr());
                try{ c = mixColorAndAlpha(fc.getKaraColor(), Math.round(fc.getKaraAlpha() * 255f)); }
                catch(Exception exc){ }
            }
            case Outline -> {
                c = mixColorAndAlpha(ev.getStyle().getBordColor(), ev.getStyle().getBordAlphaStr());
                try{ c = mixColorAndAlpha(fc.getBordColor(), Math.round(fc.getBordAlpha() * 255f)); }
                catch(Exception exc){ }
            }
            case Shadow -> {
                c = mixColorAndAlpha(ev.getStyle().getShadColor(), ev.getStyle().getShadAlphaStr());
                try{ c = mixColorAndAlpha(fc.getShadColor(), Math.round(fc.getShadAlpha() * 255f)); }
                catch(Exception exc){ }
            }
        }
        
        double r = (double)c.getRed() / 255d;
        double g = (double)c.getGreen() / 255d;
        double b = (double)c.getBlue() / 255d;
        double a = (double)c.getAlpha() / 255d;
        
        return new Color(r, g, b, a);
    }
}
