/*
 * NotificationTest.java which is part of the " wishbox ( shared )" project
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

package hu.fnf.devel.wishbox.gateway.entity.test;

import hu.fnf.devel.wishbox.gateway.entity.Enums;
import hu.fnf.devel.wishbox.gateway.entity.Notification;
import org.junit.Assert;
import org.junit.Test;

public class NotificationTest {
    @Test
    public void testProperties() throws Exception {
        Notification notification = new Notification();
        Enums.State state = Enums.State.alert;
        notification.setState(state);
        String text = "text";
        notification.setText(text);

        Assert.assertEquals(notification.getText(), text);
        Assert.assertEquals(notification.getState(), state);
    }
}
