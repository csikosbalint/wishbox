/*
 * Tester.java which is part of the " wishbox ( servlet )" project
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

import hu.fnf.devel.wishbox.api.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 25/01/15.
 */
public class Tester {
    @PersistenceContext(unitName = "openjpa")
    private EntityManagerFactory factory;

    private Logger logger = Logger.getLogger(Tester.class.getName());

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void start() throws Exception {
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        User user = new User();
        user.setMailAddress("a@b.c");

        logger.info("User: " + user.toString());
        try {

            entityTransaction.begin();

            entityManager.persist(user);
            entityManager.flush();

            entityTransaction.commit();

            logger.info("User is persisted with " + entityManager.toString());

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }


//        try {
////            em.getTransaction().begin();
//
//            User foundUser = entityManager.find(User.class, user.getOpenId());
//            logger.info("User found: " + foundUser.getMailAddress());
//
////            em.getTransaction().commit();
//
//        } catch (Exception e) {
//            logger.warning(e.getMessage());
//        }


    }

}
