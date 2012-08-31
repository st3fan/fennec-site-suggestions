package com.mozilla.fennec.typeahead.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mozilla.fennec.typeahead.service.SiteCompletionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Singleton
public class CompleteServlet extends HttpServlet
{
    @Inject
    private SiteCompletionService siteCompletionService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Get a list of 5 sites
        String prefix = request.getParameter("q").toLowerCase();
        List<SiteCompletionService.Site> sites = siteCompletionService.complete(prefix, 5);

       // Return just a very basic JSON array of site names
        String siteNames[] = new String[sites.size()];
        for (int i = 0; i < sites.size(); i++) {
            siteNames[i] = sites.get(i).getName();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        response.setContentType("application/json");
        response.getWriter().println(gson.toJson(siteNames));
    }
}
