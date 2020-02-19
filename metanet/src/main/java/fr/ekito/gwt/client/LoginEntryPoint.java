package fr.ekito.gwt.client;

import org.gwtmultipage.client.UrlPatternEntryPoint;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import fr.ekito.gwt.client.resource.ApplicationResources;
import fr.ekito.gwt.client.ui.login.LoginPanel;

@UrlPatternEntryPoint(value = "login")
public class LoginEntryPoint implements EntryPoint {
	/**
	 * gin injector
	 */
	private final LearnGinjector injector = GWT.create(LearnGinjector.class);

	@Override
	public void onModuleLoad() {
		// ensure resources are injected
		ApplicationResources.INSTANCE.style().ensureInjected();
		// get controler from gin jector
		//MainController controller = injector.getWebAppController();
		// bind event handlers
		//controller.bindHandlers();
		// get main panel
		LoginPanel mainPanel = injector.getLoginPanel();
		// add for display
		RootPanel.get("container").add(mainPanel);

	}

}
