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

import hu.fnf.devel.wishbox.backend.crawler.api.CrawlerService;
import hu.fnf.devel.wishbox.frontend.rest.api.RestService;
import hu.fnf.devel.wishbox.frontend.rest.api.TestResp;
import org.apache.log4j.Logger;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 11/01/15.
 */

@WebService
public class RestServiceImpl implements RestService {

    CrawlerService fnfCrawlerService;
    private Logger logger = Logger.getLogger(RestServiceImpl.class);

    public RestServiceImpl() {
        logger.info("constructor ");
    }

    public void setFnfCrawlerService(CrawlerService fnfCrawlerService) {
        this.fnfCrawlerService = fnfCrawlerService;
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
    public List<TestResp> getList() {
        List<TestResp> l = new ArrayList<TestResp>();
        TestResp a = new TestResp();
        a.setFirstName("a");
        a.setLastName("b");
        l.add(a);
        TestResp b = new TestResp();
        b.setFirstName("c");
        b.setLastName("d");
        l.add(b);
        return l;
    }
}
