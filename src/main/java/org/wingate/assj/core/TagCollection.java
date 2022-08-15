/*
 * Copyright (C) 2021 util2
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

import java.util.ArrayList;
import java.util.List;
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
import org.wingate.assj.tag.Strikeout;
import org.wingate.assj.tag.Underline;
import org.wingate.assj.tag.WrapStyle;

/**
 *
 * @author util2
 */
public class TagCollection {

    public TagCollection() {
    }
    
    public static List<TagAbstract> getTags(){
        List<TagAbstract> patterns = new ArrayList<>();
        
        // Outer tags :
        patterns.add(new AlignmentLegacy());
        patterns.add(new AlignmentNumpad());
        patterns.add(new Position());
        patterns.add(new Movement());
        patterns.add(new Bold());
        patterns.add(new Italic());
        patterns.add(new Underline());
        patterns.add(new Strikeout());
        patterns.add(new FontName());
        patterns.add(new FontSize());
        patterns.add(new FontEncoding());
        patterns.add(new WrapStyle());
        patterns.add(new Origin());
        patterns.add(new ClipDrawing());
        patterns.add(new ClipDrawingInvisibility());
        
        // Inner tags :
        patterns.add(new BordLegacy());
        patterns.add(new BordX());
        patterns.add(new BordY());
        patterns.add(new ShadLegacy());
        patterns.add(new ShadX());
        patterns.add(new ShadY());
        patterns.add(new BlurEdge());
        patterns.add(new Blur());
        patterns.add(new FontScale());
        patterns.add(new FontScaleX());
        patterns.add(new FontScaleY());
        patterns.add(new FontSpacing());        
        patterns.add(new FontRotate());
        patterns.add(new FontRotateX());
        patterns.add(new FontRotateY());
        patterns.add(new FontRotateZ());
        patterns.add(new ColorOfTextLegacy());
        patterns.add(new ColorOfText());
        patterns.add(new ColorOfKaraoke());
        patterns.add(new ColorOfOutline());
        patterns.add(new ColorOfShadow());
        patterns.add(new AlphaGeneral());
        patterns.add(new AlphaOfText());
        patterns.add(new AlphaOfKaraoke());
        patterns.add(new AlphaOfOutline());
        patterns.add(new AlphaOfShadow());        
        patterns.add(new KaraokeNormal());
        patterns.add(new KaraokeFillLegacy());
        patterns.add(new KaraokeFill());
        patterns.add(new KaraokeOutline());        
        patterns.add(new Reset());        
        patterns.add(new FadingNormal());
        patterns.add(new FadingComplex());
        patterns.add(new Animation());
        patterns.add(new ClipRectangle());
        patterns.add(new ClipRectangleInvisibility());
        patterns.add(new Drawing());
        patterns.add(new DrawingBaselineOffset());
        
        return patterns;
    }
    
}
