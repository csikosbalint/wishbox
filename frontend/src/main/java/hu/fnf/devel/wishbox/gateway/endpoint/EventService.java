/*
 * EventService.java which is part of the " wishbox ( frontend )" project
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

package hu.fnf.devel.wishbox.gateway.endpoint;

import hu.fnf.devel.wishbox.gateway.WishboxGateway;
import hu.fnf.devel.wishbox.gateway.entity.Enums;
import hu.fnf.devel.wishbox.gateway.entity.Event;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(WishboxGateway.ROOT)
public class EventService {
    @RequestMapping(value = "/event", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEventList() {
        List<hu.fnf.devel.wishbox.gateway.entity.Event> eventList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            hu.fnf.devel.wishbox.gateway.entity.Event event = new hu.fnf.devel.wishbox.gateway.entity.Event();
            event.setTime(new Date());
            event.setText("information about news#" + i);
            event.setType(Enums.Type.NEWS);
            event.setPriority(Enums.Priority.info);
            event.setIcon("truck");
            eventList.add(event);
        }
        return eventList;
    }
}
