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
package org.wingate.assj.tag;

import java.util.ArrayList;
import java.util.List;
import org.wingate.assj.AssEvent;

/**
 *
 * @author util2
 */
public class TagLettersWith {
    
    /*
    Pour séparer les mots, syllabes, lettres ou espaces, on a besoin
    de les parser individuellement.
    */
    private final List<TagLetter> listWords = new ArrayList<>();

    private TagLettersWith() {
    }
    
    public static TagLettersWith create(AssEvent evt){
        TagLettersWith with = new TagLettersWith();
        
        // Si la phrase n'a aucun effet, on définit le TagLetter suivant
        // et on sort de cette méthode de recherche de tags
        if(evt.getText().contains("{\\") == false){
            TagLetter tl = new TagLetter(evt.getText());
            with.listWords.add(tl);
            return with;
        }
        
        // Si on est arrivé ici c'est qu'on a un ou plusieurs tags
        // On sépare les mots avec ou sans effets
        String text = evt.getText().replace("}{", "");
        String[] separations = text.split("\\{");
        
        // On cherche les effets par mots
        for(int i=1; i<separations.length; i++){
            String z = separations[i];
            
            // On sépare les effets, du mot            
            String[] y = z.split("\\}");
            
            // On renseigne le TagLetter
            TagLetter tl = new TagLetter(y[1]);
            
            // On sépare les effets sans prendre en compte les animations
            String[] t = y[0].split("\\\\");
            
            // On regroupe effets et animations
            List<String> re = new ArrayList<>();
            String reformAnimation = null;
            for(String a : t){
                
                if(reformAnimation != null){
                    if(a.startsWith("clip") || a.startsWith("iclip") || a.contains(")")){
                        reformAnimation += "\\";
                        reformAnimation += a;
                        re.add(reformAnimation);
                        reformAnimation = null;
                    }else{
                        reformAnimation += "\\";
                        reformAnimation += a;
                    }
                    continue;
                }

                if(a.startsWith("t") == true){
                    reformAnimation = a;
                }

                if(reformAnimation == null){
                    re.add(a);
                }
            }
            
            for(int j=0; j<re.size(); j++){
                tl.getTags().add(re.get(j));
            }
            
            with.listWords.add(tl);
        }
                
//        for(TagLetter tagLetter : with.listWords){
//            System.out.printf("Word : '%s'\n with %d tag(s)", tagLetter.getWriteable(), tagLetter.getTags().size() - 1);
//            for(String tag : tagLetter.getTags()){
//                System.out.println("Tag : " + tag);
//            }
//            System.out.println("---");
//        }
        
        return with;
    }

    public List<TagLetter> getListWords() {
        return listWords;
    }
    
}
