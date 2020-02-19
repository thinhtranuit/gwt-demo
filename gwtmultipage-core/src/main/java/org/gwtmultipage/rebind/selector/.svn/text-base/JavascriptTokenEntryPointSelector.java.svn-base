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
import org.gwtmultipage.client.JavascriptTokenEntryPoint;
import org.gwtmultipage.rebind.EntryPointFactoryImplGenerator;

/**
 * Selects an entry point based on a JavascriptTokenEntryPoint annotation.
 *
 * @author brian.ireilly
 */
public class JavascriptTokenEntryPointSelector extends BaseEntryPointSelector {

	public boolean canSelect(JClassType classType) {
		return getAnnotation(classType) != null;
	}

	@Override
	protected String generateMatchCondition(JClassType classType) {
		JavascriptTokenEntryPoint annotation = getAnnotation(classType);
		if (annotation != null) {
			return "\"" + annotation.value() + "\".equals(" + EntryPointFactoryImplGenerator.JAVASCRIPT_TOKEN_VAR + ")";
		} else {
			throw new RuntimeException(classType.getQualifiedSourceName()
					+ " does not have a @JavascriptTokenEntryPoint annotation");
		}
	}

	private JavascriptTokenEntryPoint getAnnotation(JClassType classType) {
		return classType.getAnnotation(JavascriptTokenEntryPoint.class);
	}
}
