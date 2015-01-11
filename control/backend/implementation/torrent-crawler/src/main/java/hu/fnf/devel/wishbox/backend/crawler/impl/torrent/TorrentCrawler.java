/*
 * TorrentCrawler.java which is part of the " wishbox ( torrent-crawler )" project
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

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 11/01/15.
 */
package hu.fnf.devel.wishbox.backend.crawler.impl.torrent;

import hu.fnf.devel.wishbox.backend.crawler.api.CrawlerService;

public class TorrentCrawler implements CrawlerService {
    @Override
    public void init() {
        System.out.println("Crawler service init");
    }

    @Override
    public String sayHello() {
        return "Hello World! I am a service";
    }

}
