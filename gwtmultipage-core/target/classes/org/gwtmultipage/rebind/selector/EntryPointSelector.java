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
package org.gwtmultipage.rebind.selector;

import com.google.gwt.core.ext.typeinfo.JClassType;

import java.io.PrintWriter;

/**
 * A strategy for generating code to select a particular entry point class.
 *
 * @author brian.ireilly
 */
public interface EntryPointSelector {

	/**
	 * Determines whether or not this entry point selector can generate code to
	 * select the specified entry point. Using an annotation is suggested, but
	 * other criteria may also be used.
	 *
	 * @param classType the GWT class type for the entry point
	 * @return true if this selector can handle the entry point; false otherwise
	 */
	boolean canSelect(JClassType classType);

	/**
	 * Generates and outputs the code required to select the specified entry
	 * point. Selects an entry point by generating code to set the variable
	 * named by ENTRY_POINT_VAR to JClassType.getQualifiedSourceName() based on
	 * the specified classType and the relativePath and javascriptToken
	 * variables.
	 *
	 * @param classType the GWT class type for the entry point
	 * @param pw the print writer for this code generation
	 */
	void writeSelectionCode(JClassType classType, PrintWriter pw);
}
