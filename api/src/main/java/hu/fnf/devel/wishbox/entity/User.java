/*
 * User.java which is part of the " wishbox ( api )" project
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

package hu.fnf.devel.wishbox.entity;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 25/01/15.
 */
@Entity
public class User {
    @Id
    private long openId;
    @Basic
    private String mailAddress;
    @ElementCollection
    private Collection<Item> items;

    public User(long openId, String mailAddress) {
        this.openId = openId;
        this.mailAddress = mailAddress;
    }

    public long getOpenId() {
        return openId;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }
}