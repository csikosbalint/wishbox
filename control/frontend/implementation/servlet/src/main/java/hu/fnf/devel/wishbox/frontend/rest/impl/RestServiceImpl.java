/*
 * RestServiceImpl.java which is part of the " wishbox ( servlet )" project
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

package hu.fnf.devel.wishbox.frontend.rest.impl;

import hu.fnf.devel.wishbox.Database;
import hu.fnf.devel.wishbox.backend.crawler.api.CrawlerService;
import hu.fnf.devel.wishbox.entity.Item;
import hu.fnf.devel.wishbox.entity.User;
import hu.fnf.devel.wishbox.frontend.rest.api.RestService;
import org.apache.log4j.Logger;

import javax.jws.WebService;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 11/01/15.
 */

@WebService
public class RestServiceImpl implements RestService {

    Database database;

    CrawlerService fnfCrawlerService;
    private Logger logger = Logger.getLogger(RestServiceImpl.class);

    public RestServiceImpl() {
        logger.info("constructor ");
    }

    public void setFnfCrawlerService(CrawlerService fnfCrawlerService) {
        this.fnfCrawlerService = fnfCrawlerService;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public void initMethod() {
        System.out.println("initinitinit " + fnfCrawlerService.sayHello());
        logger.info("initinitinit " + fnfCrawlerService.sayHello());
    }

    @Override
    public String test() {
        return "Hello world: " + fnfCrawlerService.sayHello();
    }

    @Override
    public List<Item> getList(@PathParam("openid") String openid) {
        User user = database.find(User.class, Long.valueOf(openid));
        if (user == null) {
            user = new User(user.getOpenId(), "a@b");
            Collection<Item> items = new ArrayList<Item>();
            items.add(new Item("item1", "pattern1"));
            items.add(new Item("item2", "pattern2"));
            user.setItems(items);
            database.persist(user);
        }
        ArrayList<Item> ret = new ArrayList<Item>();
        ret.addAll(user.getItems());
        return ret;
    }
}
