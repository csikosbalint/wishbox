/*
 *   User.java is part of the "wishbox ( model )" project
 *   Copyright (C)  2015  author:  johnnym
 *
 *   This program is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU General Public License
 *   as published by the Free Software Foundation; either version 2
 *   of the License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package hu.fnf.devel.wishbox.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User extends AbstractEntity {

    private String firstName;
    private String lastName;
    @DBRef
    private List<Wish> wishes;
    @DBRef
    private List<Notification> notifications;

    public User(String id, String firstName, String lastName) {
        super.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.notifications = new ArrayList<>();
        this.wishes = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public void addWish(Wish wish) {
        this.wishes.add(wish);
    }

    public void removeWish(Wish wish) {
        for (Wish w : this.wishes) {
            if (w.getId().equals(wish.getId())) {
                this.wishes.remove(w);
                return;
            }
        }
    }

    public void removeNotification(Notification notification) {
        for (Notification n : this.notifications) {
            if (n.getId().equals(notification.getId())) {
                this.notifications.remove(n);
                return;
            }
        }
    }
}
