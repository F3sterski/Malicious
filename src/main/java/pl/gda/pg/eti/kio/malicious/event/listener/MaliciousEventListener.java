/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.event.listener;

import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;

/**
 *
 * @author Michał Cilińdź <michal.cilindz@gmail.com>
 */
public interface MaliciousEventListener {

    void handleMaliciousEvent(MaliciousEvent event);
}
