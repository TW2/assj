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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import org.wingate.assj.ASS;
import org.wingate.assj.AssEvent;
import org.wingate.assj.AssTime;
import org.wingate.assj.lib.BooleanOp;
import org.wingate.assj.tag.AlignmentLegacy;
import org.wingate.assj.tag.AlignmentNumpad;
import org.wingate.assj.tag.AlphaGeneral;
import org.wingate.assj.tag.AlphaOfKaraoke;
import org.wingate.assj.tag.AlphaOfOutline;
import org.wingate.assj.tag.AlphaOfShadow;
import org.wingate.assj.tag.AlphaOfText;
import org.wingate.assj.tag.Animation;
import org.wingate.assj.tag.Blur;
import org.wingate.assj.tag.BlurEdge;
import org.wingate.assj.tag.Bold;
import org.wingate.assj.tag.BordLegacy;
import org.wingate.assj.tag.BordX;
import org.wingate.assj.tag.BordY;
import org.wingate.assj.tag.ClipDrawing;
import org.wingate.assj.tag.ClipDrawingInvisibility;
import org.wingate.assj.tag.ClipRectangle;
import org.wingate.assj.tag.ClipRectangleInvisibility;
import org.wingate.assj.tag.ColorOfKaraoke;
import org.wingate.assj.tag.ColorOfOutline;
import org.wingate.assj.tag.ColorOfShadow;
import org.wingate.assj.tag.ColorOfText;
import org.wingate.assj.tag.ColorOfTextLegacy;
import org.wingate.assj.tag.Drawing;
import org.wingate.assj.tag.DrawingBaselineOffset;
import org.wingate.assj.tag.FadingComplex;
import org.wingate.assj.tag.FadingNormal;
import org.wingate.assj.tag.FontEncoding;
import org.wingate.assj.tag.FontName;
import org.wingate.assj.tag.FontRotate;
import org.wingate.assj.tag.FontRotateX;
import org.wingate.assj.tag.FontRotateY;
import org.wingate.assj.tag.FontRotateZ;
import org.wingate.assj.tag.FontScale;
import org.wingate.assj.tag.FontScaleX;
import org.wingate.assj.tag.FontScaleY;
import org.wingate.assj.tag.FontSize;
import org.wingate.assj.tag.FontSpacing;
import org.wingate.assj.tag.Italic;
import org.wingate.assj.tag.KaraokeFill;
import org.wingate.assj.tag.KaraokeFillLegacy;
import org.wingate.assj.tag.KaraokeNormal;
import org.wingate.assj.tag.KaraokeOutline;
import org.wingate.assj.tag.Movement;
import org.wingate.assj.tag.Origin;
import org.wingate.assj.tag.Position;
import org.wingate.assj.tag.Reset;
import org.wingate.assj.tag.ShadLegacy;
import org.wingate.assj.tag.ShadX;
import org.wingate.assj.tag.ShadY;
import org.wingate.assj.tag.ShearX;
import org.wingate.assj.tag.ShearY;
import org.wingate.assj.tag.Strikeout;
import org.wingate.assj.tag.Underline;
import org.wingate.assj.tag.WrapStyle;

/**
 *
 * @author util2
 */
public class FxChar {
    
    private final char character;
    private final String tags;
    private final long nanos;
    private final ASS ass;
    private final AssEvent event;
    private final Path path;
    
    /*
    SWING
    */
    private Font font;
    private double strWitdh = -1d;
    private double strHeight = -1d;
    private boolean space = false;
    private double spaceWidth = -1d;
    
    /*--------------------------------------------------------------------------
    PARAMETERS FOR ONE CHAR
    --------------------------------------------------------------------------*/
    private int videoWidth;
    private int videoHeight;
    
    private long karaokeLastMs;
    
    private List<Animation> transforms; // Par défaut pas d'animation
    private GeneralPath visibility;
    private int alignment; // Par défaut >> 2
    private float textAlpha; // Par défaut >> opaque (00)
    private float karaAlpha; // Par défaut >> opaque (00)
    private float bordAlpha; // Par défaut >> opaque (00)
    private float shadAlpha; // Par défaut >> opaque (00)
    private Color textColor; // Par défaut >> blanc
    private Color karaColor; // Par défaut >> jaune
    private Color bordColor; // Par défaut >> noir
    private Color shadColor; // Par défaut >> noir
    private float blur; // Par défaut >> 0
    private float blurEdge; // Par défaut >> 0
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private boolean strikeout;
    private final double[] bord = new double[2];
    private final double[] shad = new double[2];
    private String fontName;
    private float fontSize;
    private int fontEncoding;
    private final float[] fontRotate = new float[3];
    private final float[] fontScale = new float[2];
    private float fontSpacing;
    private final double[] shear = new double[2];
    private int wrapStyle;
    private String reset;
    private final double[] move = new double[6]; // Et position
    private final double[] origin = new double[2];
    private Shape drawing;
    private int drawingOffset; // pbo
    //==========================================================================
    
    public FxChar(char c, String tags, long nanos, ASS ass, AssEvent ev, Font font,
            double lineWidth, double lineHeight, double spaceWidth){
        this.character = c; 
        this.tags = tags;
        this.nanos = nanos;
        this.ass = ass;
        this.event = ev;
        this.font = font;
        
        strWitdh = lineWidth;
        strHeight = lineHeight;
        
        if(Character.isSpaceChar(c) == true){
            space = true;
            this.spaceWidth = spaceWidth;
        }
        
        videoWidth = Integer.parseInt(ass.getResX());
        videoHeight = Integer.parseInt(ass.getResY());
        
        path = createPath();
    }
    
    private Path createPath(){
        // On veut un objet Path en JavaFx pour notre valeur de retour
        Path p = new Path();
        
        // On initialise les effets
        List<TagAbstract> tas = TagSetter.getTags(tags);

        for(TagAbstract ta : tas){
            switch(ta.getType()){
                case AlignLegacy -> { apply((AlignmentLegacy)ta); }
                case AlignNumpad -> { apply((AlignmentNumpad)ta); }
                case AlphaGeneral -> { apply((AlphaGeneral)ta); }
                case AlphaText -> { apply((AlphaOfText)ta); }
                case AlphaKaraoke -> { apply((AlphaOfKaraoke)ta); }
                case AlphaOutline -> { apply((AlphaOfOutline)ta); }
                case AlphaShadow -> { apply((AlphaOfShadow)ta); }
                case ColorTextLegacy -> { apply((ColorOfTextLegacy)ta); }
                case ColorText -> { apply((ColorOfText)ta); }
                case ColorKaraoke -> { apply((ColorOfKaraoke)ta); }
                case ColorOutline -> { apply((ColorOfOutline)ta); }
                case ColorShadow -> { apply((ColorOfShadow)ta); }
                case Blur -> { apply((Blur)ta); }
                case BlurEdge -> { apply((BlurEdge)ta); }
                case FontName -> { apply((FontName)ta); }
                case FontSize -> { apply((FontSize)ta); }
                case FontRot -> { apply((FontRotate)ta); }
                case FontRotX -> { apply((FontRotateX)ta); }
                case FontRotY -> { apply((FontRotateY)ta); }
                case FontRotZ -> { apply((FontRotateZ)ta); }
                case FontScale -> { apply((FontScale)ta); }
                case FontScaleX -> { apply((FontScaleX)ta); }
                case FontScaleY -> { apply((FontScaleY)ta); }
                case FontSpacing -> { apply((FontSpacing)ta); }
                case FontEncoding -> { apply((FontEncoding)ta); }
                case ShearX -> { apply((ShearX)ta); }
                case ShearY -> { apply((ShearY)ta); }
                case Bold -> { apply((Bold)ta); }
                case Italic -> { apply((Italic)ta); }
                case Underline -> { apply((Underline)ta); }
                case StrikeOut -> { apply((Strikeout)ta); }
                case BordLegacy -> { apply((BordLegacy)ta); }
                case BordX -> { apply((BordX)ta); }
                case BordY -> { apply((BordY)ta); }
                case ShadLegacy -> { apply((ShadLegacy)ta); }
                case ShadX -> { apply((ShadX)ta); }
                case ShadY -> { apply((ShadY)ta); }
                case WrapStyle -> { apply((WrapStyle)ta); }
                case Reset -> { apply((Reset)ta); }
                case Position -> { apply((Position)ta); }
                case Movement -> { apply((Movement)ta); }
                case Origin -> { apply((Origin)ta); }
                case ClipRect -> { apply((ClipRectangle)ta); }
                case ClipDrawing -> { apply((ClipDrawing)ta); }
                case IClipRect -> { apply((ClipRectangleInvisibility)ta); }
                case IClipDrawing -> { apply((ClipDrawingInvisibility)ta); }
                case KaraokeNormal -> { apply((KaraokeNormal)ta); }
                case KaraokeFillLegacy -> { apply((KaraokeFillLegacy)ta); }
                case KaraokeFill -> { apply((KaraokeFill)ta); }
                case KaraokeOutline -> { apply((KaraokeOutline)ta); }
                case FadingNormal -> { apply((FadingNormal)ta); }
                case FadingComplex -> { apply((FadingComplex)ta); }
                case Drawing -> { apply((Drawing)ta); }
                case DrawingBaselineOffset -> { apply((DrawingBaselineOffset)ta); }
                case Animation -> { apply((Animation)ta); }
            }
        }
        
        // On va d'abord étudier la forme avec l'API Java
        
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setFont(font);
        
        TextLayout textLayout = new TextLayout(Character.toString(character), font, g2d.getFontRenderContext());
        
        // On obtient une forme avec l'API Java
        AffineTransform tr = new AffineTransform();
//        tr.scale((double)fontScale[0], (double)fontScale[1]); // fscx, fscy
        Shape sh = textLayout.getOutline(tr);
        
        // On décompose la forme avec l'API Java pour faire la forme avec l'API JavaFx
        PathIterator pi = sh.getPathIterator(null);
        // Un objet où seront stockés les points parsés
        double[] pts = new double[6];
        // Un objet qui récupère les valeurs du dernier MOVE pour fermer si besoin avec CLOSE
        double[] lastMove = new double[2];
        
        while(pi.isDone() == false){
            int type = pi.currentSegment(pts);
            
            switch(type){
                case PathIterator.SEG_MOVETO -> {
                    p.getElements().add(new MoveTo(pts[0], pts[1]));
                    lastMove[0] = pts[0]; lastMove[1] = pts[1];
                }
                case PathIterator.SEG_LINETO -> {
                    p.getElements().add(new LineTo(pts[0], pts[1]));
                }
                case PathIterator.SEG_QUADTO -> {
                    p.getElements().add(new QuadCurveTo(pts[0], pts[1], pts[2], pts[3]));
                }
                case PathIterator.SEG_CUBICTO -> {
                    p.getElements().add(new CubicCurveTo(pts[0], pts[1], pts[2], pts[3], pts[4], pts[5]));
                }
                case PathIterator.SEG_CLOSE -> {
                    p.getElements().add(new LineTo(lastMove[0], lastMove[1]));
                }
            }
            
            pi.next();
        }
        
        // On libère les ressources utilisées pour le contexte graphique
        // et on retourne le chemin en JavaFx
        g2d.dispose();
        
        return p;
    }

    public Path getPath() {
        return path;
    }

    public char getCharacter() {
        return character;
    }

    public String getTags() {
        return tags;
    }
    
    //--------------------------------------------------------------------------
    
    public void apply(AlignmentLegacy a){
        switch(a.getData()){
            case 1 -> { alignment = 1; }
            case 2 -> { alignment = 2; }
            case 3 -> { alignment = 3; }
            case 5 -> { alignment = 7; }
            case 6 -> { alignment = 8; }
            case 7 -> { alignment = 9; }
            case 9 -> { alignment = 4; }
            case 10 -> { alignment = 5; }
            case 11 -> { alignment = 6; }
        }
    }
    
    public void apply(AlignmentNumpad an){
        alignment = an.getData();
    }
    
    public void apply(AlphaGeneral alpha){
        textAlpha = (255 - alpha.getData()) / 255f;
        karaAlpha = (255 - alpha.getData()) / 255f;
        bordAlpha = (255 - alpha.getData()) / 255f;
        shadAlpha = (255 - alpha.getData()) / 255f;
    }
    
    public void apply(AlphaOfText alpha){
        textAlpha = (255 - alpha.getData()) / 255f;
    }
    
    public void apply(AlphaOfKaraoke alpha){
        karaAlpha = (255 - alpha.getData()) / 255f;
    }
    
    public void apply(AlphaOfOutline alpha){
        bordAlpha = (255 - alpha.getData()) / 255f;
    }
    
    public void apply(AlphaOfShadow alpha){
        shadAlpha = (255 - alpha.getData()) / 255f;
    }
    
    public void apply(ColorOfTextLegacy color){
        textColor = color.getData();
    }
    
    public void apply(ColorOfText color){
        textColor = color.getData();
    }
    
    public void apply(ColorOfKaraoke color){
        karaColor = color.getData();
    }
    
    public void apply(ColorOfOutline color){
        bordColor = color.getData();
    }
    
    public void apply(ColorOfShadow color){
        shadColor = color.getData();
    }
    
    public void apply(Blur value){
        blur = value.getData();
    }
    
    public void apply(BlurEdge value){
        blurEdge = value.getData();
    }
    
    public void apply(Bold value){
        bold = value.getData();
    }
    
    public void apply(Italic value){
        italic = value.getData();
    }
    
    public void apply(Underline value){
        underline = value.getData();
    }
            
    public void apply(Strikeout value){
        strikeout = value.getData();
    }
    
    public void apply(BordLegacy value){
        bord[0] = value.getData();
        bord[1] = value.getData();
    }
    
    public void apply(BordX value){
        bord[0] = value.getData();
    }
    
    public void apply(BordY value){
        bord[1] = value.getData();
    }
    
    public void apply(ShadLegacy value){
        shad[0] = value.getData();
        shad[1] = value.getData();
    }
    
    public void apply(ShadX value){
        shad[0] = value.getData();
    }
    
    public void apply(ShadY value){
        shad[1] = value.getData();
    }
    
    public void apply(FontName value){
        fontName = value.getData();
    }
    
    public void apply(FontSize value){
        fontSize = value.getData();
    }
    
    public void apply(FontEncoding value){
        fontEncoding = value.getData();
    }
    
    public void apply(FontRotate value){
        fontRotate[2] = value.getData(); // FontRotateZ
    }
    
    public void apply(FontRotateX value){
        fontRotate[0] = value.getData();
    }
    
    public void apply(FontRotateY value){
        fontRotate[1] = value.getData();
    }
    
    public void apply(FontRotateZ value){
        fontRotate[2] = value.getData();
    }
    
    public void apply(FontScale value){
        fontScale[0] = value.getData();
        fontScale[1] = value.getData();
    }
    
    public void apply(FontScaleX value){
        fontScale[0] = value.getData();
    }
    
    public void apply(FontScaleY value){
        fontScale[1] = value.getData();
    }
    
    public void apply(FontSpacing value){
        fontSpacing = value.getData();
    }
    
    public void apply(ShearX value){
        shear[0] = value.getData();
    }
    
    public void apply(ShearY value){
        shear[1] = value.getData();
    }
    
    public void apply(WrapStyle value){
        wrapStyle = value.getData();
    }
    
    public void apply(Reset value){
        reset = value.getData();
    }
    
    public void apply(Position value){
        // move = new double[]{0d, 0d, 0d, 0d, 0d, 0d};
        move[0] = value.getData().getX();
        move[1] = value.getData().getY();
        move[2] = value.getData().getX();
        move[3] = value.getData().getY();
        move[4] = 0d;
        move[5] = 0d;
    }
    
    public void apply(Movement value){
        // move = new double[]{0d, 0d, 0d, 0d, 0d, 0d};
        move[0] = value.getData().getStartCoordinates().getX();
        move[1] = value.getData().getStartCoordinates().getY();
        move[2] = value.getData().getEndCoordinates().getX();
        move[3] = value.getData().getEndCoordinates().getY();
        move[4] = AssTime.toMillisecondsTime(value.getData().getStart());
        move[5] = AssTime.toMillisecondsTime(value.getData().getEnd());
    }
    
    public void apply(Origin value){
        origin[0] = value.getData().getX();
        origin[1] = value.getData().getY();
    }
    
    public void apply(Drawing value){
        drawing = value.getData();
    }
    
    public void apply(ClipRectangle value){
        visibility = new GeneralPath(value.getData());
    }
    
    public void apply(ClipDrawing value){
        visibility = new GeneralPath(value.getData());
    }
    
    public void apply(ClipRectangleInvisibility value){
        Shape visible = new Rectangle2D.Double(0, 0, videoWidth, videoHeight);
        visibility = new GeneralPath(BooleanOp.getSubstract(visible, value.getData()));
    }
    
    public void apply(ClipDrawingInvisibility value){
        Shape visible = new Rectangle2D.Double(0, 0, videoWidth, videoHeight);
        visibility = new GeneralPath(BooleanOp.getSubstract(visible, value.getData()));
    }
    
    public void apply(KaraokeNormal value){
        
    }
    
    public void apply(KaraokeFillLegacy value){
        
    }
    
    public void apply(KaraokeFill value){
        
    }
    
    public void apply(KaraokeOutline value){
        
    }
    
    public void apply(FadingNormal value){
        
    }
    
    public void apply(FadingComplex value){
        
    }
    
    public void apply(DrawingBaselineOffset value){
        drawingOffset = value.getData();
    }
    
    public void apply(Animation value){
        transforms.add(value);
    }
    
    //---====================================================================---
    
    public ASS getASS(){
        return ass;
    }

    public List<Animation> getTransforms() {
        return transforms;
    }

    public GeneralPath getVisibility() {
        return visibility;
    }

    public int getAlignment() {
        return alignment;
    }

    public float getTextAlpha() {
        return textAlpha;
    }

    public float getKaraAlpha() {
        return karaAlpha;
    }

    public float getBordAlpha() {
        return bordAlpha;
    }

    public float getShadAlpha() {
        return shadAlpha;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getKaraColor() {
        return karaColor;
    }

    public Color getBordColor() {
        return bordColor;
    }

    public Color getShadColor() {
        return shadColor;
    }

    public float getBlur() {
        return blur;
    }

    public float getBlurEdge() {
        return blurEdge;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public boolean isUnderline() {
        return underline;
    }

    public boolean isStrikeout() {
        return strikeout;
    }

    public double[] getBord() {
        return bord;
    }

    public double[] getShad() {
        return shad;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontEncoding() {
        return fontEncoding;
    }

    public void setFontEncoding(int fontEncoding) {
        this.fontEncoding = fontEncoding;
    }

    public float getFontSpacing() {
        return fontSpacing;
    }

    public void setFontSpacing(float fontSpacing) {
        this.fontSpacing = fontSpacing;
    }

    public int getWrapStyle() {
        return wrapStyle;
    }

    public void setWrapStyle(int wrapStyle) {
        this.wrapStyle = wrapStyle;
    }

    public String getReset() {
        return reset;
    }

    public void setReset(String reset) {
        this.reset = reset;
    }

    public Shape getDrawing() {
        return drawing;
    }

    public void setDrawing(Shape drawing) {
        this.drawing = drawing;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public long getKaraokeLastMs() {
        return karaokeLastMs;
    }

    public void setKaraokeLastMs(long karaokeLastMs) {
        this.karaokeLastMs = karaokeLastMs;
    }

    public int getDrawingOffset() {
        return drawingOffset;
    }

    public void setDrawingOffset(int drawingOffset) {
        this.drawingOffset = drawingOffset;
    }
    
    public float[] getFontRotate() {
        return fontRotate;
    }

    public float[] getFontScale() {
        return fontScale;
    }

    public double[] getShear() {
        return shear;
    }

    public double[] getMove() {
        return move;
    }

    public double[] getOrigin() {
        return origin;
    }
    
    //==========================================================================
    
    public double getStrWitdh() {
        return strWitdh;
    }

    public void setStrWitdh(double strWitdh) {
        this.strWitdh = strWitdh;
    }

    public boolean isSpace() {
        return space;
    }

    public double getSpaceWidth() {
        return spaceWidth;
    }
    
}
