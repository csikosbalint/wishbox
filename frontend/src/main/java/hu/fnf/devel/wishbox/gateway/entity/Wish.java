/*
 * Wish.java which is part of the " wishbox ( frontend )" project
 * Copyright (C)  2015  author:  johnnym
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

package hu.fnf.devel.wishbox.gateway.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "wishes")
public class Wish extends AbstractEntity {
    private String label;
    private List<String> keywords;
    @DBRef
    private List<Event> events;
    @DBRef
    private List<Notification> notifications;

    public Wish() {
        this.events = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.keywords = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addKeyword(String keyword) {
        keywords.add(keyword);
    }

    public void removeEvent(Event event) {
        for (Event e : events) {
            if (e.getId().equals(event.getId())) {
                System.out.println("events#: " + events.size());
                events.remove(e);
                System.out.println("removing: " + e.getId());
                System.out.println("events#: " + events.size());
                break;
            }
        }
    }

}
