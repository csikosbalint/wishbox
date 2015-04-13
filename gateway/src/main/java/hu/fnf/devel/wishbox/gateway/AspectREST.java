/*
 * AspectREST.java which is part of the " wishbox ( gateway )" project
 * Copyright (C)  2015  author:  johnnym
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

package hu.fnf.devel.wishbox.gateway;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

/**
 * Created by johnnym on 13/04/15.
 */
@Aspect
public class AspectREST {
    //@Before("execution(* GatewayREST+.*(..))")
    @Before(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void advice(final JoinPoint pjp) {
        System.out.println(pjp.getSignature().getName());

        for (final Object argument : pjp.getArgs()) {
            if (argument instanceof SecurityContextHolderAwareRequestWrapper) {
                System.out.println("User: " + ((SecurityContextHolderAwareRequestWrapper) argument).getUserPrincipal().getName());
            }
            System.out.println("Parameter value:" + argument);
        }
        System.out.println("advice");
        //throw new Exception("no way");
    }
}
