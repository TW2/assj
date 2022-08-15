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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author util2
 */
public class TagLetter {
    
    private String sentence;
    private List<String> tags = new ArrayList<>();

    public TagLetter() {
    }

    public TagLetter(String sentence) {
        this.sentence = sentence;
    }
    
    public TagLetter(String sentence, List<String> tags) {
        this.sentence = sentence;
        this.tags = tags;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
}
