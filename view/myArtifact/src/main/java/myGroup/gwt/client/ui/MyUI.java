/*
 * MyUI.java which is part of the " wishbox ( myArtifact )" project
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

package myGroup.gwt.client.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import hu.fnf.devel.wishbox.entity.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;


/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 11/01/15.
 * http://demo.vaadin.com/sampler/#databinding/declarative-validation
 */
public class MyUI extends UI {
    //    Logger logger = LoggerFactory.getLogger(MyUI.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalSplitPanel sample = new HorizontalSplitPanel();
        sample.setSizeFull();
//        sample.setSplitPosition(150.0f, PIXELS);

        sample.setSecondComponent(new Label("korte"));

        setContent(sample);

        Table grid = new Table();

        grid.setSizeFull();
        for (Object i : getItemContiner().getItemIds()) {
            System.out.println(i.toString());
        }
        grid.setContainerDataSource(getItemContiner());
        grid.setSelectable(true);
        grid.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                final String valueString = String.valueOf(valueChangeEvent.getProperty()
                        .getValue());
                Notification.show("Value changed:", valueString,
                        Notification.Type.TRAY_NOTIFICATION);
            }
        });
        sample.setFirstComponent(grid);


//            Main window is the primary browser window
        final Window main = new Window("Hello window");
        addWindow(main);
        // "Hello world" text is added to window as a Label component
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        if (user != null) {
            String email = user.getEmail();
        } else {
            // no user logged in
        }
        assert user != null;

        main.setContent(new Label(user.getUserId()));
    }

    private Container getItemContiner() {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("First", String.class, "1st");
        container.addContainerProperty("Second", String.class, "2nd");
//        WebClient client = WebClient.create("http://195.228.45.136:8181/cxf/test");
//        client = client.accept("application/json")
//                .type("application/json")
//                .path("/say/list");
//        TestResp testResp = client.get(TestResp.class);
        URL url = null;
        URLConnection connection = null;
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        try {
            url = new URL("http://195.228.45.136:8181/cxf/test/user/" + user.getUserId() + "/item");
            connection = url.openConnection();
            connection.addRequestProperty("Referer", "WishBox frontend");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        ObjectMapper mapper = new ObjectMapper();
        Collection<Item> testRespList = null;
        try {
            testRespList = mapper.readValue(builder.toString(), new TypeReference<Collection<Item>>() {
            });
        } catch (IOException e) {
            System.out.println("input: " + builder.toString());
            System.out.println("hiba: " + e.getMessage());
            e.printStackTrace();
        }
        //TestResp[] testResps = gson.fromJson(builder.toString(), TestResp[].class);


        for (Item testResp : testRespList) {
            com.vaadin.data.Item item = container.addItem(testResp.toString());
            item.getItemProperty("First").setValue(testResp.getName());
            item.getItemProperty("Second").setValue(testResp.getPattern());
        }
        return container;
    }
}
