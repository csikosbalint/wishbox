/*
 * MainPage.java which is part of the " wishbox ( user-interface )" project
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

package hu.fnf.devel.wishbox.ui;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.org.apache.commons.codec.binary.Base64;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 21/02/15.
 */
public class MainPage extends UI {
    //    Logger logger = LoggerFactory.getLogger(MyUI.class);

    private final MenuBar.Command menuCommand = new MenuBar.Command() {
        @Override
        public void menuSelected(final MenuBar.MenuItem selectedItem) {
            Notification.show("Action " + selectedItem.getText(),
                    Notification.Type.TRAY_NOTIFICATION);
        }
    };

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        MenuBar menuBar = new MenuBar();
        menuBar.setWidth(100.0f, Unit.PERCENTAGE);
        MenuBar.MenuItem child = null;
        menuBar.addItem("command", menuCommand);
        setContent(menuBar);

        HorizontalSplitPanel sample = new HorizontalSplitPanel();
        sample.setSizeFull();

        VerticalSplitPanel verticalSplitPanel = new VerticalSplitPanel();
        verticalSplitPanel.setFirstComponent(new Label("elso"));
        verticalSplitPanel.setSecondComponent(new Label("masodik"));

        sample.setSecondComponent(verticalSplitPanel);

        setContent(sample);

        Table grid = new Table();

        grid.setSizeFull();
        for (Object i : getItemContiner().getItemIds()) {
            System.out.println("item ids: " + i.toString());
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

        if (getSession().getAttribute("state") == null) {
            UserService userService = UserServiceFactory.getUserService();
            User user = userService.getCurrentUser();
            getSession().setAttribute("user", user);

            URI uri = null;
            try {
                uri = new URI(("http://jenna.fnf.hu/gateway/persistence/user/" + user.getUserId()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String plainCreds = "API_KEY:API_PASS";

            byte[] plainCredsBytes = plainCreds.getBytes();
            byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
            String base64Creds = new String(base64CredsBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + base64Creds);
            HttpEntity<String> request = new HttpEntity<String>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);

            JsonFactory factory = new JsonFactory();

            ObjectMapper m = new ObjectMapper(factory);
            JsonNode rootNode = null;
            try {
                rootNode = m.readTree(response.getBody());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();
            while (fieldsIterator.hasNext()) {

                Map.Entry<String, JsonNode> field = fieldsIterator.next();
                if (!field.getKey().startsWith("_")) {
                    getSession().setAttribute(field.getKey(), field.getValue().asText());
                } else {

                }
                System.out.println("Key: " + field.getKey() + ":\t" + field.getValue());
            }
            getSession().setAttribute("state", "loaded");
        }
        com.vaadin.data.Item item = container.addItem(((User) getSession().getAttribute("user")).getNickname());
        item.getItemProperty("First").setValue(getSession().getAttribute("firstName").toString());
        item.getItemProperty("Second").setValue(getSession().getAttribute("lastName").toString());


        return container;
    }
}
