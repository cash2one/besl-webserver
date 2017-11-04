/**
 * (C) 2010 Fivewh platform fivewh.com
 */
package com.fivewh.webapps.tomcat7.listener;

import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

/**
 * @Auther: <a mailto:yinpengyi@fivewh.com>Yin Pengyi</a>
 */
public class FiveLifecycleListener implements LifecycleListener {
    private boolean debug;

    public void lifecycleEvent(LifecycleEvent event) {
        //log("event: " + event.getType());
    }

    public void setDebug(boolean value) {
        this.debug = value;

        log("Set the debug to " + debug);
    }

    public void setEnvProps(String value) {
        log("Set the env props file to " + value);

        System.setProperty("env.props.file", value);
    }

    public void setServProps(String value) {
        log("Set the serv props file to " + value);

        System.setProperty("serv.props.file", value);
    }

    private void log(String msg) {
        if (debug) {
            System.out.println("[ FiveLifecycleListener ] " + msg);
        }
    }
}
