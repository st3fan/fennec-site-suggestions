package com.mozilla.fennec.typeahead.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.mozilla.fennec.typeahead.handlers.CompleteServlet;
import com.mozilla.fennec.typeahead.service.SimpleSiteCompletionService;
import com.mozilla.fennec.typeahead.service.SiteCompletionService;

public class MyServletContextListener extends GuiceServletContextListener
{
    @Override
    protected Injector getInjector()
    {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets()
            {
                bind(SiteCompletionService.class).to(SimpleSiteCompletionService.class);
                serve("/complete").with(CompleteServlet.class);
            }
        });
    }
}
