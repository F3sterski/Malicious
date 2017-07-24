/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.entity;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;
import pl.gda.pg.eti.kio.malicious.event.generator.EventGenerator;
import pl.gda.pg.eti.kio.malicious.event.listener.MaliciousEventListener;
import pl.gda.pg.eti.kio.malicious.factory.EventGeneratorFactory;

/**
 *
 * @author Michał Cilińdź
 */
public abstract class BaseMalice implements Runnable, MaliciousEventListener {

    private final Logger log = LogManager.getLogger(BaseMalice.class);
    private String name = getClass().getSimpleName();
    private List<EventGenerator> eventGenerators;
    private Map<String, Object> parameters;

    public void create(Map<String, Object> parameters, EventGeneratorFactory factory) {
        this.parameters = parameters;
        eventGenerators = factory.createEventGenerators(parameters);
        onCreate();
    }

    @Override
    public void run() {
        try {
            start();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public synchronized void handleMaliciousEvent(MaliciousEvent event) {
        log.info("Execute: {} - {}", name, event.getDescription());
        execute(event);
    }

    protected boolean hasParameter(String key) {
        return key == null ? false : parameters.containsKey(key);
    }

    protected Object getParameter(String key) {
        return key == null ? null : parameters.get(key);
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void onCreate() {
    }

    protected void onStart() {
    }

    private void start() {
        for (EventGenerator generator : eventGenerators) {
            generator.addMaliciousEventListener(this);
            new Thread(generator).start();
        }
        onStart();
    }

    abstract protected void execute(MaliciousEvent event);

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
    
    
}
