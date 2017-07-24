/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.attribute;

/**
 *
 * @author Nela Gabryelska
 */
public class TimeRangeAttribute extends RangeAttribute {

    public TimeRangeAttribute(String parameter) {
        super(parameter);
    }

    public TimeRangeAttribute(int rangeMin, int rangeMax) {
        super(rangeMin, rangeMax);
    }

    public void multiplyRange(int m) {
        this.rangeMax *= m;
        this.rangeMin *= m;
    }

    protected long getRandomTime() {
        if (rangeMin != rangeMax) {
            return (long) (1000 * this.getRandom());
        } else {
            return 0;
        }
    }
}
