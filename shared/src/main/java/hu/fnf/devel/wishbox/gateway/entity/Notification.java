/*
 * Notification.java which is part of the " wishbox ( shared )" project
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

public class Notification {
    private String text;
    private Enums.State state;
    private Enums.Priority priority;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Enums.State getState() {
        return state;
    }

    public void setState(Enums.State state) {
        this.state = state;
    }

    public Enums.Priority getPriority() {
        return priority;
    }

    public void setPriority(Enums.Priority priority) {
        this.priority = priority;
    }
}
