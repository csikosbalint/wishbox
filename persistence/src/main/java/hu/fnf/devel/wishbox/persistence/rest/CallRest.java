/*
 * CallRest.java which is part of the " wishbox ( persistence )" project
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

package hu.fnf.devel.wishbox.persistence.rest;

import hu.fnf.devel.wishbox.persistence.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 01/03/15.
 */
@RestController
public class CallRest {
    private static final String root = "/full";

    @RequestMapping(root + "/user/{id}")
    @ResponseBody
    User home(@PathVariable("id") Long id) throws Exception {
        User retUser = null;//Application.getRepositoryUser().findOne(id);
        if (retUser == null) {
            throw new Exception("No such user!");
        }
        return retUser;
    }
}
