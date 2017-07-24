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
 * @author Nela Gabryelska
 */
@CreatableEventGenerator(name = "repeated")
public class RepeatedRunEventGenerator extends EventGenerator {

    private static final String EVENT_DESCRIPTION = "repeated run event";

    @Override
    protected void start() throws Exception {
        if (!timeAttributes.parseParameter(parameter)) {
            timeAttributes.setRange(60, 240);
        }
        while (true) {
            if (timeAttributes.isRandom()) {
                timeAttributes.setRandomTime();
            }
            Thread.sleep(timeAttributes.getTime());
            invokeEvent(new MaliciousEvent(EVENT_DESCRIPTION));
        }
    }
}
