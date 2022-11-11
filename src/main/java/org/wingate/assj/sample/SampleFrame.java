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

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.wingate.assj.ASS;
import org.wingate.assj.AssEvent;
import org.wingate.assj.AssStyle;
import org.wingate.assj.AssTime;
import org.wingate.assj.core.Render;

/**
 *
 * @author util2
 */
public class SampleFrame extends javax.swing.JFrame {
    
    private final View view = new View();
    
    private volatile long nanosTimeNow = 50L;

    /**
     * Creates new form SampleFrame
     */
    public SampleFrame() {
        initComponents();
        panASS.setLayout(new BorderLayout());
        panASS.add(view, BorderLayout.CENTER);
        
        JFXPanel fxPanel = new JFXPanel();
    }
    
    public ASS createJunkAss(String sentence, long msStart, long msEnd){
        ASS ass = ASS.NoFileToLoad();
        
        AssEvent ev = new AssEvent();
        ev.setText(sentence);
        ev.setStartTime(AssTime.create(msStart));
        ev.setEndTime(AssTime.create(msEnd));
        
        AssStyle sty = new AssStyle();
        sty.setName("DefaultStyle");
        sty.setFontname("Essai");
        sty.setFontsize(70d);
        
        ev.setStyle(sty);
        
        ass.getEvents().add(ev);
        ass.getStyles().put(sty.getName(), sty);
        
        return ass;
    }
    
    public void refreshDrawing(long nanos){
        Platform.runLater(() -> {
            Render render = new Render();

            long nanosStart = TimeUnit.MILLISECONDS.toNanos(0L);
            long nanosEnd = TimeUnit.MILLISECONDS.toNanos(3000L);
            
            ASS ass = createJunkAss(tfText.getText(), 0L, 3000L);
            List<BufferedImage> images = render.getImages(ass, nanos, nanosStart, nanosEnd);

            view.updateView(images);
        });        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnPlay = new javax.swing.JButton();
        tfText = new javax.swing.JTextField();
        panASS = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        btnPlay.setText("Test");
        btnPlay.setFocusable(false);
        btnPlay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPlay.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPlay);

        tfText.setText("The black cat is in the house of Denise.");
        jToolBar1.add(tfText);

        panASS.setBackground(new java.awt.Color(102, 153, 255));

        javax.swing.GroupLayout panASSLayout = new javax.swing.GroupLayout(panASS);
        panASS.setLayout(panASSLayout);
        panASSLayout.setHorizontalGroup(
            panASSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panASSLayout.setVerticalGroup(
            panASSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
            .addComponent(panASS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panASS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        // Play
        refreshDrawing(nanosTimeNow);
    }//GEN-LAST:event_btnPlayActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SampleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SampleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SampleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SampleFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SampleFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPlay;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel panASS;
    private javax.swing.JTextField tfText;
    // End of variables declaration//GEN-END:variables
}
