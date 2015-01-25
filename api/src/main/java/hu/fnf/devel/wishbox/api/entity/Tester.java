/*
 * Tester.java which is part of the " wishbox ( api )" project
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

package hu.fnf.devel.wishbox.api.entity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 25/01/15.
 */
public class Tester {
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void start() throws Exception {

        User user = new User();
        user.setMailAddress("a@b.c");
        final Item item = new Item();
        item.setName("cica");

        Collection<Item> items = new ArrayList<Item>();
        items.add(item);

        user.setSearchItems(items);

        em.persist(item);
        em.persist(user);
        em.flush();

        User foundUser = em.find(User.class, user.getOpenId());
        System.out.println("find: " + foundUser.getSearchItems().size());
        System.out.println("      " + foundUser.getMailAddress());
    }

}
