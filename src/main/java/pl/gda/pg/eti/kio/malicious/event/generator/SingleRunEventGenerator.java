/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.event.generator;

import pl.gda.pg.eti.kio.malicious.annotation.CreatableEventGenerator;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;

/**
 *
 * @author Michał Cilińdź
 */
@CreatableEventGenerator(name = "single")
public class SingleRunEventGenerator extends EventGenerator {

    private static final String EVENT_DESCRIPTION = "single run event";

    @Override
    protected void start() throws InterruptedException {
        if (!timeAttributes.parseParameter(parameter)) {
            timeAttributes.setRange(30, 300);
            timeAttributes.setRandomTime();
        }
        Thread.sleep(timeAttributes.getTime());
        invokeEvent(new MaliciousEvent(EVENT_DESCRIPTION));
    }
}
