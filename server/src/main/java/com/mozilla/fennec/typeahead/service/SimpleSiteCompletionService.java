package com.mozilla.fennec.typeahead.service;

import com.google.inject.Singleton;
import org.ardverk.collection.PatriciaTrie;
import org.ardverk.collection.StringKeyAnalyzer;
import org.ardverk.collection.Trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class SimpleSiteCompletionService implements SiteCompletionService
{
    private static Logger logger = Logger.getLogger(SimpleSiteCompletionService.class.getSimpleName());

    private final Trie<String, Site> trie = new PatriciaTrie<String, Site>(StringKeyAnalyzer.INSTANCE);

    SimpleSiteCompletionService()
    {
        try
        {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("alexa.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                String[] elements = line.split(",");
                int rank = Integer.parseInt(elements[0]);
                if ((rank % 100000) == 0) {
                    logger.log(Level.INFO, "Imported " + rank);
                }
                String domain = elements[1];
                trie.put(domain, new Site(domain, rank));
            }
        }

        catch (IOException e)
        {
            logger.log(Level.SEVERE, "Cannot import domains: " + e.getLocalizedMessage(), e);
        }
    }

    @Override
    public List<Site> complete(String prefix, int n)
    {
        SortedMap<String, Site> map = trie.getPrefixedBy(prefix);
        List<Site> sites = new ArrayList<Site>(map.values());

        Collections.sort(sites, new Comparator<Site>() {
            @Override
            public int compare(Site a, Site b) {
                return a.getRank() - b.getRank();
            }
        });

        return sites.subList(0, n);
    }
}
