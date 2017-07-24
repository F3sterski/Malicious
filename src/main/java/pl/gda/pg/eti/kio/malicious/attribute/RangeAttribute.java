/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.attribute;

import java.util.Random;

/**
 *
 * @author Nela Gabryelska
 */
public class RangeAttribute {

    protected int rangeMin;
    protected int rangeMax;

    public RangeAttribute(String parameter) {
        int index = parameter.indexOf('-');
        rangeMin = Integer.parseInt(parameter.substring(0, index));
        rangeMax = Integer.parseInt(parameter.substring(index + 1));
        if (rangeMin > rangeMax) {
            int z = rangeMin;
            rangeMin = rangeMax;
            rangeMax = z;
        }
    }

    public RangeAttribute(int rangeMin, int rangeMax) {
        if (rangeMin < rangeMax) {
            this.rangeMin = rangeMin;
            this.rangeMax = rangeMax;
        } else {
            this.rangeMin = rangeMax;
            this.rangeMax = rangeMin;
        }
    }

    public int getRandom() {
        Random rand = new Random();
        int randomNum = rand.nextInt((rangeMax - rangeMin) + 1) + rangeMin;
        return randomNum;
    }
}
