/*
 * Gateway.java which is part of the " wishbox ( gateway-engine )" project
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

package hu.fnf.devel.wishbox.gateway.engine.rest;

import hu.fnf.devel.wishbox.gateway.GatewayREST;
import hu.fnf.devel.wishbox.persistence.User;

import javax.ws.rs.PathParam;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 21/02/15.
 */
public class Gateway implements GatewayREST {


    private Logger logger = Logger.getLogger(Gateway.class.getCanonicalName());

    @Override
    public void initMethod() {
        logger.info(this.getClass().getCanonicalName() + " has been initalized.");
    }

    @Override
    public String test() {
        return "Hello world!";
    }

    @Override
    public List<User> getList(@PathParam("openid") String openid) {
        //TODO: implement gateway data retrieve mechanism
        return null;
    }
}
