/*
 *   WebSecurityConfig.java is part of the "wishbox ( gateway )" project
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

package hu.fnf.devel.wishbox.gateway.security;

import hu.fnf.devel.wishbox.gateway.WishboxGateway;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean(name="myAuthenticationManager")
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//    @Bean
//    public SessionSecurityFilter sessionSecurityFilter() {
//        SessionSecurityFilter sessionSecurityFilter = null;
//        try {
//            sessionSecurityFilter = new SessionSecurityFilter(authenticationManager());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sessionSecurityFilter;
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
//                Token service
                .antMatchers(HttpMethod.POST, WishboxGateway.ROOT + "/token")
//                WebSocket
                .antMatchers(HttpMethod.GET, WishboxGateway.WEBSOCKET)
                .antMatchers(HttpMethod.GET, WishboxGateway.WEBSOCKET + "/info")
                .antMatchers(HttpMethod.GET, WishboxGateway.WEBSOCKET + "/**")
                .antMatchers(HttpMethod.POST, WishboxGateway.WEBSOCKET + "/**")
//                Root
                .antMatchers(HttpMethod.GET, "/*")
//                Static files
                .antMatchers(HttpMethod.GET, "/**/*.js")
                .antMatchers(HttpMethod.GET, "/**/*.map")
                .antMatchers(HttpMethod.GET, "/**/*.css")
                .antMatchers(HttpMethod.GET, "/**/*.woff")
                .antMatchers(HttpMethod.GET, "/**/*.woff2")
                .antMatchers(HttpMethod.GET, "/**/*.ttf")
//                Swagger
                .antMatchers(HttpMethod.GET, "/v2/api-docs")
                .antMatchers(HttpMethod.GET, "/swagger/*")
                .antMatchers(HttpMethod.GET, "/swagger/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .authorizeRequests()
//              .expresssionHandler(Handler)
//               .anyRequest()
                .antMatchers(WishboxGateway.ROOT + "/**")
//               .anyRequest()
//                .hasRole(WishboxGateway.GRANTED_ROLE)
                .permitAll()
//              .access("isAuthorized()")
                .and()
                .addFilter(new SessionSecurityFilter(authenticationManager()));
    }

}
