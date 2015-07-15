/*
 * WishService.java which is part of the " wishbox ( gateway )" project
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

import java.util.List;
import javax.servlet.http.HttpSession;

import hu.fnf.devel.wishbox.gateway.WishboxGateway;
import hu.fnf.devel.wishbox.gateway.entity.Enums;
import hu.fnf.devel.wishbox.gateway.entity.Event;
import hu.fnf.devel.wishbox.gateway.entity.Notification;
import hu.fnf.devel.wishbox.gateway.entity.User;
import hu.fnf.devel.wishbox.gateway.entity.Wish;
import hu.fnf.devel.wishbox.gateway.entity.repository.EventRepository;
import hu.fnf.devel.wishbox.gateway.entity.repository.NotificationRepository;
import hu.fnf.devel.wishbox.gateway.entity.repository.UserRepository;
import hu.fnf.devel.wishbox.gateway.entity.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(WishboxGateway.ROOT + "/wish")
public class WishService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Wish> getWishList(HttpSession session) {
        String uid = (String) session.getAttribute( WishboxGateway.SUBJECT_ID );
        return userRepository.findOne(uid).getWishes();
    }

    @RequestMapping(value = "/{id}/event", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEventList(@PathVariable("id") String id, HttpSession session) {
        String uid = (String) session.getAttribute( WishboxGateway.SUBJECT_ID );
        // TODO: optimalize!
        for (Wish w : userRepository.findOne(uid).getWishes()) {
            if (w.getId().equals(id)) {
                return w.getEvents();
            }
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void addWish(@RequestBody Wish w, HttpSession session) {
        Wish wish = new Wish(w);
        Notification notification = new Notification();
        notification.setText("New Wish: " + "\"" + wish.getLabel() + "\"");
        notification.setPriority(Enums.Priority.info);
        notificationRepository.save(notification);

        Event event = new Event();
        event.setTitle(wish.getLabel());
        event.setText("New Wish has been made with label \"" + wish.getLabel() +
                "\". The label automatically has been added as a search keyword. You can add relevant information to the Wish at any time.");
        event.setPriority(Enums.Priority.info);
        event.setIcon("magic");
        eventRepository.save(event);

        wish.addEvent(event);
        wishRepository.save(wish);

        String uid = (String) session.getAttribute( WishboxGateway.SUBJECT_ID );
        User user = userRepository.findOne(uid);
        user.addNotification(notification);
        user.addWish(wish);
        userRepository.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteWish(@PathVariable("id") String id, HttpSession session) {
        String uid = (String) session.getAttribute( WishboxGateway.SUBJECT_ID );
        User user = userRepository.findOne(uid);
        for (Wish wish : user.getWishes()) {
            if (wish.getId().equals(id)) {
                user.removeWish(wish);
                wishRepository.delete(id);
                Notification notification = new Notification();
                notification.setText("Deleted Wish: " + "\"" + wish.getLabel() + "\"");
                notification.setPriority(Enums.Priority.warning);
                notificationRepository.save(notification);

                user.addNotification(notification);
                userRepository.save(user);
                return;
            }
        }
    }
}
