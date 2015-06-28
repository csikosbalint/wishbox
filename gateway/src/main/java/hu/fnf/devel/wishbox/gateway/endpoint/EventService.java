/*
 * EventService.java which is part of the " wishbox ( gateway )" project
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
import hu.fnf.devel.wishbox.gateway.entity.Event;
import hu.fnf.devel.wishbox.gateway.entity.User;
import hu.fnf.devel.wishbox.gateway.entity.Wish;
import hu.fnf.devel.wishbox.gateway.entity.repository.EventRepository;
import hu.fnf.devel.wishbox.gateway.entity.repository.UserRepository;
import hu.fnf.devel.wishbox.gateway.entity.repository.WishRepository;
import hu.fnf.devel.wishbox.gateway.security.InterceptorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(WishboxGateway.ROOT + "/event")
public class EventService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private WishRepository wishRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEventList(HttpSession session) {
        String uid = (String) session.getAttribute(InterceptorConfig.SUBJECT_ID);
        List<Event> events = new ArrayList<>();
        for (Wish wish : userRepository.findOne(uid).getWishes()) {
            events.addAll(wish.getEvents());
        }
        return events;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteEvent(@PathVariable("id") String id, HttpSession session) {
        String uid = (String) session.getAttribute(InterceptorConfig.SUBJECT_ID);
        User user = userRepository.findOne(uid);
        for (Wish wish : user.getWishes()) {
            for (Event event : wish.getEvents()) {
                if (event.getId().equals(id)) {
                    Wish w = wishRepository.findOne(wish.getId());
                    w.removeEvent(event);
                    wishRepository.save(w);
                    eventRepository.delete(id);
                    return;
                }
            }
        }
    }
}
