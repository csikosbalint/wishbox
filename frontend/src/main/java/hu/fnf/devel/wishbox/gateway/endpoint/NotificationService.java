/*
 * NotificationService.java which is part of the " wishbox ( frontend )" project
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
import hu.fnf.devel.wishbox.gateway.entity.Notification;
import hu.fnf.devel.wishbox.gateway.entity.User;
import hu.fnf.devel.wishbox.gateway.entity.Wish;
import hu.fnf.devel.wishbox.gateway.entity.repository.UserRepository;
import hu.fnf.devel.wishbox.gateway.security.InterceptorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(WishboxGateway.ROOT + "/notification")
public class NotificationService {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Notification> getNotificationList(HttpSession session) {
        String id = (String) session.getAttribute(InterceptorConfig.SUBJECT_ID);
        User user = userRepository.findOne(id);
        List<Notification> notifications = new ArrayList<>();
        notifications.addAll(user.getNotifications());
        for (Wish wish : user.getWishes()) {
            notifications.addAll(wish.getNotifications());
        }
        return notifications;
    }
}