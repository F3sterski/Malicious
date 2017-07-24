/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.attribute;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nela Gabryelska
 */
public class QuantityAttribute {

    private Map<String, Integer> counters;
    private Map<String, Integer> parameters;
    private RangeAttribute range;

    public QuantityAttribute(String parameter) {
        counters = new HashMap();
        parameters = new HashMap();
        range = new RangeAttribute(parameter);
    }

    public QuantityAttribute(int rangeMin, int rangeMax) {
        counters = new HashMap();
        parameters = new HashMap();
        range = new RangeAttribute(rangeMin, rangeMax);
    }

    public void addCounter(String key) {
        counters.put(key, 0);
        parameters.put(key, 1);
    }

    public void incrementCounter(String key) {
        int value = counters.get(key).intValue();
        counters.put(key, value + 1);
    }

    public boolean checkCounter(String key) {
        incrementCounter(key);
        if (counters.get(key).intValue() == parameters.get(key).intValue()) {
            counters.put(key, 0);
            parameters.put(key, 0);
            return true;
        }
        return false;
    }
}
