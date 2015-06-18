/*
 * EventTest.java which is part of the " wishbox ( shared )" project
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

import hu.fnf.devel.wishbox.gateway.entity.Event;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by johnnym on 14/06/15.
 */
public class EventTest {
    @Test
    public void testProperties() throws Exception {
        Event event = new Event();
        Event.Type type = Event.Type.FILE;
        event.setType(type);
        String text = "text";
        event.setText(text);
        Date date = new Date();
        event.setTime(date);

        Assert.assertEquals(event.getTime(), date);
        Assert.assertEquals(event.getText(), text);
        Assert.assertEquals(event.getType(), type);
    }
}
