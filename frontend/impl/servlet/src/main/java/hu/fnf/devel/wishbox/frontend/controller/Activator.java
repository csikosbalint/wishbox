/*
 * Activator.java which is part of the " wishbox ( servlet )" project
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

package hu.fnf.devel.wishbox.frontend.controller;

/**
 * Created by johnnym on 03/01/15.
 */

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class Activator implements BundleActivator {

    private ServiceRegistration<HelloController> registration;

    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("activator start");
        Dictionary props = new Hashtable();

        props.put("service.exported.interfaces", "*");
        props.put("service.exported.configs", "org.apache.cxf.ws");
        props.put("org.apache.cxf.ws.address", "http://localhost:9090/test");

        registration = bundleContext.registerService(HelloController.class, new HelloController(), props);
    }

    public void stop(BundleContext bundleContext) throws Exception {
        registration.unregister();
    }


    public void onRegisterService(final HelloController service,
                                  final Map properties) {
        System.out.println("HelloController registered - output: "
                + service.helloWorld());
    }

    public void onUnregisterService(final HelloController service,
                                    final Map properties) {

    }

}
