/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.event.generator;

import pl.gda.pg.eti.kio.malicious.annotation.CreatableEventGenerator;

/**
 *
 * @author Michał Cilińdź
 */
@CreatableEventGenerator(name = "someTest")
public class SomeTestEventGenerator extends SingleRunEventGenerator {

    public String getParameter() {
        return this.parameter;
    }
}
