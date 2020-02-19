package fr.ekito.gwt.client;

import org.gwtmultipage.client.UrlPatternEntryPoint;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import fr.ekito.gwt.client.controller.MainController;
import fr.ekito.gwt.client.resource.ApplicationResources;
import fr.ekito.gwt.client.ui.main.MainPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
@UrlPatternEntryPoint(value = "main")
public class LearnEntryPoint implements EntryPoint {

	/**
	 * gin injector
	 */
	private final LearnGinjector injector = GWT.create(LearnGinjector.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// ensure resources are injected
		ApplicationResources.INSTANCE.style().ensureInjected();
		// get controler from gin jector
		MainController controller = injector.getWebAppController();
		// bind event handlers
		controller.bindHandlers();
		// get main panel
		MainPanel mainPanel = injector.getMainPanel();
		// add for display
		RootLayoutPanel.get().add(mainPanel);
	}
}
