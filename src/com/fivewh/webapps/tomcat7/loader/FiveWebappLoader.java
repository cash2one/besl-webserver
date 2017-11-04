/**
 * (C) 2010 Fivewh platform fivewh.com
 */
package com.fivewh.webapps.tomcat7.loader;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.loader.WebappLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class FiveWebappLoader extends WebappLoader {
    private boolean debug;
    List<URL> repositoryURLs = Collections.emptyList();

    public FiveWebappLoader() {
        super();

        log("Constructor");
    }

    public FiveWebappLoader(ClassLoader parent) {
        super(parent);

        log("Constructor with parent=" + parent);
    }

    public void initInternal() {
        log("initInternal starts.");

        try {
            super.initInternal();
        } catch (LifecycleException e) {
            log("init error " + e.getMessage());
        }

        log("initInternal completed.");
    }

    public void startInternal() throws LifecycleException {
        log("startInternal starts.");

        super.startInternal();

        for (Iterator<URL> i = repositoryURLs.iterator(); i.hasNext();) {
            URL url = i.next();
            String path = url.getPath();
            if (path.endsWith("!/")) {
                path = path.substring(0, path.length() - 2);
            }

            log("file:" + path);
            addRepository("file:" + path);
        }

        log("startInternal completed.");
    }

    protected List<URL> getClassPathURLs(String[] repositories) {
        List<URL> urls = new ArrayList<URL>();

        try {
            for (int i = 0; i < repositories.length; i++) {
                File file = new File(repositories[i]);
                if (file.exists()) {
                    if (file.isDirectory()) {
                        urls.add(new URL("file", "", -1, file.getAbsolutePath() + "/"));
                        File[] files = file.listFiles();
                        for (int j = 0; j < files.length; j++) {
                            file = files[j];
                            if (file.isFile() && file.canRead() && file.getAbsolutePath().endsWith(".jar")) {
                                urls.add(new URL("jar", "", -1, file.getAbsolutePath() + "!/"));
                            }
                        }
                    } else if (file.isFile() && file.canRead() && file.getAbsolutePath().endsWith(".jar")) {
                        urls.add(new URL("jar", "", -1, file.getAbsolutePath() + "!/"));
                    } else {
                        log("Type not known: " + file);
                    }
                } else {
                    log("File/Directory does not exist: " + file);
                }
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // sort urls
        Collections.sort(urls, new FiveWebappLoader.URLSort());

        return urls;
    }

    public void setExtraRepositories(String value) {
        log("setExtraRepo value=" + value);

        String[] repositories = value.split(",");
        for (int i = 0; i < repositories.length; i++) {
            log("repo: " + repositories[i]);
        }

        repositoryURLs = getClassPathURLs(repositories);
        for (Iterator<URL> i = repositoryURLs.iterator(); i.hasNext();) {
            URL url = i.next();
            log("repo url: " + url.toString());
        }
    }

    public void setDebug(boolean value) {
        debug = value;
    }

    private void log(String msg) {
        if (debug) {
            System.out.println("[ FiveWebappLoader] " + msg);
        }
    }

    private static class URLSort implements Comparator {
        public int compare(Object a, Object b) {
            URL url1 = (URL) a;
            URL url2 = (URL) b;

            String url1Str = url1.toString().toLowerCase();
            String url2Str = url2.toString().toLowerCase();

            return url1Str.compareTo(url2Str);
        }

        public boolean equals(Object obj) {
            if (obj instanceof FiveWebappLoader.URLSort) {
                return true;
            }

            return false;
        }
    }
}
