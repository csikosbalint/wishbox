/*
 * WishboxDatabase.java which is part of the " wishbox ( api )" project
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

package hu.fnf.devel.wishbox.impl.database;

import hu.fnf.devel.wishbox.Database;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 25/01/15.
 */
public class WishboxDatabase implements Database {
    private EntityManager entityManager;

    private Logger logger = Logger.getLogger(WishboxDatabase.class.getName());

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }

    public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
        return entityManager.find(entityClass, primaryKey, properties);
    }

    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
        return entityManager.find(entityClass, primaryKey, lockMode);
    }

    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
        return entityManager.find(entityClass, primaryKey, lockMode, properties);
    }

    public void persist(Object entity) {
        entityManager.persist(entity);
    }
}
