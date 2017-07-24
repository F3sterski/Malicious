/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.event;

/**
 *
 * @author Michał Cilińdź
 */
public class MaliciousEvent {

    protected String description;

    public MaliciousEvent() {
        description = getClass().getSimpleName();
    }

    public MaliciousEvent(String description) {
        this.description = description;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
