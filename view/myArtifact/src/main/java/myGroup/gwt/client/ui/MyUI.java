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

import com.vaadin.annotations.Push;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * Created by Balint Csikos (csikos.balint@fnf.hu) on 11/01/15.
 */
@Push(PushMode.DISABLED)
public class MyUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        // Main window is the primary browser window
        final Window main = new Window("Hello window");
        addWindow(main);
        // "Hello world" text is added to window as a Label component
        main.setContent(new Label("Hello World!"));
    }
}
