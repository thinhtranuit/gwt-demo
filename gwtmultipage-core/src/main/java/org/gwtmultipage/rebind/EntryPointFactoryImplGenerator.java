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
package org.gwtmultipage.rebind;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

import org.gwtmultipage.rebind.selector.EntryPointSelector;
import org.gwtmultipage.rebind.selector.JavascriptTokenEntryPointSelector;
import org.gwtmultipage.rebind.selector.UrlPatternEntryPointSelector;

/**
 * @author claudiushauptmann
 * @author brian.ireilly
 */
public class EntryPointFactoryImplGenerator extends Generator {

	/**
	 * The variable name for storing the selected entry point. Available in
	 * EntryPointFactoryImpl.onModuleLoad().
	 */
	public static final String ENTRY_POINT_VAR = "entryPoint";

	/**
	 * The variable name where the relative path is stored. Available in
	 * EntryPointFactoryImpl.onModuleLoad().
	 */
	public static final String RELATIVE_PATH_VAR = "relativePath";

	/**
	 * The variable name where the javascript token is stored. Available in
	 * EntryPointFactoryImpl.onModuleLoad().
	 */
	public static final String JAVASCRIPT_TOKEN_VAR = "javascriptToken";

	private static final String MENU_ELEMENT_ID_CONFIG_PROPERTY =
			"GwtMultipage.menuElementId";
	private static final String JAVASCRIPT_TOKEN_VARIABLE_CONFIG_PROPERTY =
			"GwtMultipage.javascriptTokenVariable";

	private static final EntryPointSelector[] selectors = {
			new JavascriptTokenEntryPointSelector(),
			new UrlPatternEntryPointSelector()
	};

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {

		PrintWriter pw = context.tryCreate(logger,
				"org.gwtmultipage.client",
				"EntryPointFactoryImpl");

		if (pw != null) {

			String javascriptTokenVariable
					= getJavascriptTokenVariable(logger, context);
			String menuElementId = getMenuElementId(logger, context);


			// TODO: consider using ClassSourceFileComposerFactory
			pw.println("package org.gwtmultipage.client;");
			pw.println();
			pw.println("import com.google.gwt.core.client.GWT;");
			pw.println("import com.google.gwt.core.client.RunAsyncCallback;");
			pw.println("import com.google.gwt.core.client.EntryPoint;");
			pw.println("import com.google.gwt.user.client.Window;");
			pw.println("import com.google.gwt.user.client.ui.RootPanel;");
			pw.println("import com.google.gwt.user.client.ui.Widget;");
			pw.println("import org.gwtmultipage.client.core.EntryPointFactory;");
			pw.println("import org.gwtmultipage.client.core.EntryPointFactoryUtil;");

			pw.println();
			pw.println("public class EntryPointFactoryImpl implements EntryPointFactory {");
			pw.println();
			pw.println("	private native String getJavascriptToken() /*-{");
			pw.println("		return $wnd." + javascriptTokenVariable + ";");
			pw.println("	}-*/;");
			pw.println();
			pw.println("	public void onModuleLoad() {");

			pw.println("		String moduleBaseURL = GWT.getModuleBaseURL();");
			pw.println("		String href = Window.Location.getHref();");
			pw.println("		int endIndex = moduleBaseURL.lastIndexOf(\"/\", moduleBaseURL.length()-2);");
			pw.println("		String " + RELATIVE_PATH_VAR + " = href.substring(endIndex+1, href.length());");
			pw.println();
			pw.println("		String " + JAVASCRIPT_TOKEN_VAR + " = getJavascriptToken();");
			pw.println();
			pw.println("		String " + ENTRY_POINT_VAR + " = null;");

			List<String> entryPoints = new ArrayList<String>();
			String elseClause = "";
			for (JPackage pack : getPackages(context)) {
				for (JClassType classtype : pack.getTypes()) {
					for (EntryPointSelector selector : selectors) {
						if (selector.canSelect(classtype)) {
							entryPoints.add(classtype.getQualifiedSourceName());
							pw.println("		" + elseClause);
							elseClause = "else";
							selector.writeSelectionCode(classtype, pw);
						}
					}
				}
			}

			pw.println();
			pw.println("		if (" + ENTRY_POINT_VAR + " != null) {");
			pw.println("			launchEntryPoint(" + ENTRY_POINT_VAR + ");");
			pw.println("		} else {");
			pw.println("			String[] entryPointArray = {");
			String comma = " ";
			for (String entryPoint : entryPoints) {
				pw.println("				" + comma + "\"" + entryPoint + "\"");
				comma = ",";
			}
			pw.println("			};");
			pw.println("			Widget menu = EntryPointFactoryUtil.makeEntryPointMenu(entryPointArray, new EntryPointFactoryUtil.EntryPointCallback() {");
			pw.println("				public void onClick(String entryPoint) {");
			pw.println("					launchEntryPoint(entryPoint);");
			pw.println("				}");
			pw.println("			});");
			pw.println("			RootPanel.get(" + makeRootPanelGetParam(menuElementId) + ").add(menu);");
			pw.println("		}");
			pw.println("	}");
			pw.println();

			pw.println("	private void launchEntryPoint(final String entryPoint) {");
			for (String entryPoint : entryPoints) {
				pw.println("		if (\"" + entryPoint + "\".equals(entryPoint)) {");
				pw.println("			GWT.runAsync(new RunAsyncCallback() {");
				pw.println("				public void onFailure(Throwable caught) {");
				pw.println("					Window.alert(\"Code download failed\");");
				pw.println("				}");
				pw.println("				public void onSuccess() {");
				pw.println("					EntryPoint ep = new " + entryPoint + "();");
				pw.println("					ep.onModuleLoad();");
				pw.println("				}");
				pw.println("			});");
				pw.println("		}");
			}
			pw.println("	}");

			pw.println("}");
			context.commit(logger, pw);
		}

		return "org.gwtmultipage.client.EntryPointFactoryImpl";
	}

	private String makeRootPanelGetParam(String menuElementId) {
		if (menuElementId == null) {
			return "";
		} else {
			return "\"" + menuElementId + "\"";
		}
	}

	private JPackage[] getPackages(GeneratorContext context) {
		TypeOracle oracle = context.getTypeOracle();
		return oracle.getPackages();
	}

	private String getJavascriptTokenVariable(TreeLogger logger,
	                                          GeneratorContext context)
			throws UnableToCompleteException {

		return getSingleValuedConfigurationProperty(JAVASCRIPT_TOKEN_VARIABLE_CONFIG_PROPERTY, logger, context);
	}

	private String getMenuElementId(TreeLogger logger,
	                                GeneratorContext context)
			throws UnableToCompleteException {
		return getSingleValuedConfigurationProperty(MENU_ELEMENT_ID_CONFIG_PROPERTY, logger, context);
	}

	private String getSingleValuedConfigurationProperty(String propertyName, TreeLogger logger, GeneratorContext context) throws UnableToCompleteException {
		ConfigurationProperty property;
		try {
			property = context.getPropertyOracle().getConfigurationProperty(
					propertyName);
		} catch (BadPropertyValueException e) {
			logger.log(TreeLogger.Type.ERROR,
					"Missing configuration parameter: "
							+ propertyName, e);
			throw new UnableToCompleteException();
		}

		// non-multi-valued property
		return property.getValues().get(0);
	}
}
