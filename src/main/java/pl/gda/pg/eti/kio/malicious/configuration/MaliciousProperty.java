/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.configuration;

import java.util.HashMap;

/**
 *
 * @author Nela Gabryelska
 */
public class MaliciousProperty {

    private String name;
    private HashMap parameters;
    private Class clazz;

    public MaliciousProperty(String name, HashMap parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public HashMap getParameters() {
        return parameters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParameters(HashMap parameters) {
        this.parameters = parameters;
    }
}
