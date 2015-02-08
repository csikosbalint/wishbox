/*
 * Item.java which is part of the " wishbox ( persistence )" project
 * Copyright (C)  2015  author:  Balint Csikos (csikos.balint@fnf.hu)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package hu.fnf.devel.wishbox.api.entity;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 25/01/15.
 */
@Embeddable
public class Item {
    @Basic
    private long id;
    @Basic
    private String name;
//    @ElementCollection
//    @CollectionTable(name = "patterns")
//    private Collection<Pattern> patterns;

    public Item() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Collection<Pattern> getPatterns() {
//        return patterns;
//    }
//
//    public void setPatterns(Collection<Pattern> patterns) {
//        this.patterns = patterns;
//    }
}
