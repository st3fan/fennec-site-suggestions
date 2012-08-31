package com.mozilla.fennec.typeahead.service;

import java.util.List;

public interface SiteCompletionService
{
    public List<Site> complete(String prefix, int n);

    public static class Site
    {
        private final int rank;
        private final String name;

        public Site(String name, int rank)
        {
            this.name = name;
            this.rank = rank;
        }

        @SuppressWarnings("UnusedDeclaration")
        public int getRank()
        {
            return rank;
        }

        @SuppressWarnings("UnusedDeclaration")
        public String getName()
        {
            return name;
        }
    }
}
