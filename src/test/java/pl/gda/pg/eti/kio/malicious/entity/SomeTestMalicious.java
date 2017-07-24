/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.entity;

import pl.gda.pg.eti.kio.malicious.entity.BaseMalice;
import pl.gda.pg.eti.kio.malicious.annotation.CreatableMalicious;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;

/**
 *
 * @author Michał Cilińdź
 */
@CreatableMalicious(name = "someMalicius")
public class SomeTestMalicious extends BaseMalice {

    @Override
    protected void execute(MaliciousEvent event) {
    }
}
