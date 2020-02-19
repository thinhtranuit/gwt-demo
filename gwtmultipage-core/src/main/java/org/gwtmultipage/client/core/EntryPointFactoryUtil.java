/*
 * Copyright 2010 Brian Reilly
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gwtmultipage.client.core;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Utilities for the generated code to use. It's much easier to write this code
 * than it is to write code that generates this code.
 *
 * @author claudiushauptmann
 * @author brian.ireilly
 */
public class EntryPointFactoryUtil {

    public interface EntryPointCallback {
        void onClick(String entryPoint);
    }

	public static Widget makeEntryPointMenu(String[] entryPoints, final EntryPointCallback callback) {
		// TODO: consider rendering this in a more stylish way
        VerticalPanel menu = new VerticalPanel();
        for (final Map.Entry<String, Set<String>> entry : makePackageToClassMap(entryPoints).entrySet()) {
            final String packageName = entry.getKey();
            Set<String> classNames = entry.getValue();
            Label packageLabel = new Label(packageName);
            packageLabel.getElement().getStyle().setColor("#999999");
            packageLabel.getElement().getStyle().setFontSize(8, Style.Unit.PT);
            menu.add(packageLabel);
            for (final String className : classNames) {
                Label menuItem = new Label(className);
                menuItem.getElement().getStyle().setColor("#0000FF");
                menuItem.getElement().getStyle().setFontSize(10, Style.Unit.PT);
                menuItem.getElement().getStyle().setTextDecoration(Style.TextDecoration.UNDERLINE);
                menuItem.getElement().getStyle().setCursor(Style.Cursor.POINTER);
                menuItem.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent clickEvent) {
                        RootPanel.get().clear();
                        callback.onClick(packageName + "." + className);
                    }
                });
                menu.add(menuItem);
            }
        }
        return menu;
    }

    public static String getRelativePath() {
        String moduleBaseURL = GWT.getModuleBaseURL();
        String href = Window.Location.getHref();
        int endIndex = moduleBaseURL.lastIndexOf("/", moduleBaseURL.length()-2);
	    return href.substring(endIndex+1, href.length());
    }

    private static Map<String, Set<String>> makePackageToClassMap(
		    String[] entryPoints) {
        Map<String, Set<String>> map = new TreeMap<String, Set<String>>();
        for (String entryPoint : entryPoints) {
            int splitPoint = entryPoint.lastIndexOf(".");
            String packageName = entryPoint.substring(0, splitPoint);
            String className = entryPoint.substring(splitPoint + 1);
            Set<String> classNames = map.get(packageName);
            if (classNames == null) {
                classNames = new TreeSet<String>();
                map.put(packageName, classNames);
            }
            classNames.add(className);
        }
        return map;
    }
}
