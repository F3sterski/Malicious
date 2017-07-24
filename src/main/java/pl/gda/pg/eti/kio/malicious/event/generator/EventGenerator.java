/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.event.generator;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.gda.pg.eti.kio.malicious.attribute.TimeAttribute;
import pl.gda.pg.eti.kio.malicious.entity.BaseMalice;
import pl.gda.pg.eti.kio.malicious.entity.MouseMalice;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;
import pl.gda.pg.eti.kio.malicious.event.MaliciousRemoteEvent;
import pl.gda.pg.eti.kio.malicious.event.listener.MaliciousEventListener;

/**
 *
 * @author Michał Cilińdź
 */
public abstract class EventGenerator implements Runnable {

    private final Logger log = LogManager.getLogger(EventGenerator.class);
    private List<MaliciousEventListener> listeners;
    protected String parameter;
    protected TimeAttribute timeAttributes;

    public void create(Object parameter) {
        this.parameter = parameter.toString();
        this.timeAttributes = new TimeAttribute();
        onCreate();
    }

    public void addMaliciousEventListener(MaliciousEventListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        this.listeners.add(listener);
    }

    public void removeMaliciousEventListener(MaliciousEventListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    @Override
    public void run() {
        if (listeners == null) {
            log.error("Create method wasn't invoked");
        } else {
            try {
                start();
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    protected void invokeEvent(MaliciousEvent event) {
        if (listeners != null) {
            if(event instanceof MaliciousRemoteEvent){
                BaseMalice bm = ((MaliciousRemoteEvent)event).getMalice();
                bm.handleMaliciousEvent(event);                
            } else {
                for (MaliciousEventListener listener : listeners) {
                    listener.handleMaliciousEvent(event);
                }    
            }
        }
    }

    protected void onCreate() {
    }

    protected abstract void start() throws Exception;
}
