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
import org.gwtmultipage.client.UrlPatternEntryPoint;
import org.gwtmultipage.rebind.EntryPointFactoryImplGenerator;

/**
 * Selects an entry point based on a UrlPatternEntryPoint annotation.
 *
 * @author claudiushauptmann
 * @author brian.ireilly
 */
public class UrlPatternEntryPointSelector extends BaseEntryPointSelector {

	public boolean canSelect(JClassType classType) {
		return getAnnotation(classType) != null;
	}

	@Override
	protected String generateMatchCondition(JClassType classType) {
		UrlPatternEntryPoint annotation = getAnnotation(classType);
		if (annotation != null) {
			return EntryPointFactoryImplGenerator.RELATIVE_PATH_VAR + ".matches(\"" + annotation.value() + "\")";
		} else {
			throw new RuntimeException(classType.getQualifiedSourceName()
					+ " does not have a @MultipageEntryPoint annotation");
		}
	}

	private UrlPatternEntryPoint getAnnotation(JClassType classType) {
		return classType.getAnnotation(UrlPatternEntryPoint.class);
	}
}
