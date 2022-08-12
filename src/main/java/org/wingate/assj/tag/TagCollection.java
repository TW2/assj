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
package org.wingate.assj.tag;

import java.util.ArrayList;
import java.util.List;

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
