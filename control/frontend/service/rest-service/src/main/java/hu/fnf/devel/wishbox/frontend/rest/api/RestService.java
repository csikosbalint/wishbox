/*
 * RestService.java which is part of the " wishbox ( rest-service )" project
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
package hu.fnf.devel.wishbox.frontend.rest.api;

import hu.fnf.devel.wishbox.entity.Item;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 11/01/15.
 */
@Path("/")
public interface RestService {

    public void initMethod();

    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String test();

    @GET
    @Path("/user/{openid}/item")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Item> getList(@PathParam("openid") String openid);
}
