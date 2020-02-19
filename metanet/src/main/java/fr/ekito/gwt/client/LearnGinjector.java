package fr.ekito.gwt.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import fr.ekito.gwt.client.controller.MainController;
import fr.ekito.gwt.client.model.ModelHandler;
import fr.ekito.gwt.client.resource.ApplicationResources;
import fr.ekito.gwt.client.resource.Messages;
import fr.ekito.gwt.client.ui.login.LoginPanel;
import fr.ekito.gwt.client.ui.main.MainPanel;

/**
 * Google gin injector
 * 
 * all components to inject
 * @author AGI
 *
 */
@GinModules(LearnGinModule.class)
public interface LearnGinjector extends Ginjector {
	
	public SimpleEventBus getEventBus();
	
	public ApplicationResources getApplicationResources();
	
	public Messages getMessages();
	
	public MainController getWebAppController();
	
	public ModelHandler getModelHandler();
	
	public MainPanel getMainPanel(); 
	
	public LoginPanel getLoginPanel();
}
