package fr.ekito.gwt.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import fr.ekito.gwt.client.controller.MainController;
import fr.ekito.gwt.client.model.ModelHandler;
import fr.ekito.gwt.client.resource.ApplicationResources;
import fr.ekito.gwt.client.resource.Messages;
import fr.ekito.gwt.client.ui.main.MainPanel;

/**
 * Google gin module configuration
 * 
 * @author AGI
 *
 */
public class LearnGinModule extends AbstractGinModule{

	@Override
	protected void configure() {
		// Resources
		bind(Messages.class).in(Singleton.class);
		bind(ApplicationResources.class).in(Singleton.class);
		
		// Core
		bind(SimpleEventBus.class).in(Singleton.class);
		bind(MainController.class).in(Singleton.class);
		bind(ModelHandler.class).in(Singleton.class);
		
		// UI
		bind(MainPanel.class).in(Singleton.class);
	}

}
