/*
 * Copyright 2008 Claudius Hauptmann
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
package org.gwtmultipage.client;

import org.gwtmultipage.client.core.EntryPointFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;

public class EntrypointDispatcher implements EntryPoint {
	public void onModuleLoad() {
		try {
			EntryPointFactory u = (EntryPointFactory) GWT
					.create(EntryPointFactory.class);
			u.onModuleLoad();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
