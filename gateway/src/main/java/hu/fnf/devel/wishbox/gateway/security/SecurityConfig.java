/*
 * SecurityConfig.java which is part of the " wishbox ( gateway )" project
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

package hu.fnf.devel.wishbox.gateway.security;

import hu.fnf.devel.wishbox.gateway.WishboxGateway;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure( WebSecurity web ) throws Exception {
        web.ignoring()
                .antMatchers( HttpMethod.POST, WishboxGateway.ROOT + "/token" )
                .antMatchers( HttpMethod.GET, "/websocket" )

                .antMatchers( HttpMethod.GET, "/*" )
                .antMatchers( HttpMethod.GET, "/**/*.js" )
                .antMatchers( HttpMethod.GET, "/**/*.map" )
                .antMatchers( HttpMethod.GET, "/**/*.css" )
                .antMatchers( HttpMethod.GET, "/**/*.woff" )
                .antMatchers( HttpMethod.GET, "/**/*.woff2" )
                .antMatchers( HttpMethod.GET, "/**/*.ttf" );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests() //expresssionHandler(Handler)
                .antMatchers( WishboxGateway.ROOT + "/**" )
                .permitAll() //access("isAuthorized()")
                .and()
                .addFilter( new SessionSecurityFilter() );
    }

}
