/*
 *   ImDbWorker.java is part of the "wishbox ( crawler )" project
 *   Copyright (C)  2015  author:  johnnym
 *
 *   This program is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU General Public License
 *   as published by the Free Software Foundation; either version 2
 *   of the License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package hu.fnf.devel.wishbox.crawler.worker;

import hu.fnf.devel.wishbox.gateway.entity.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ImDbWorker {
    @Autowired
    private WishRepository wishRepository;

    @Async
    public void work() {
        System.out.println("woring");
    }

    @Scheduled(fixedDelay = 5000)
    public void sched() {
        wishRepository.findAll().forEach(u -> u.getId());
    }
}
