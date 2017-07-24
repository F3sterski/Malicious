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
public class TimeAttribute {

    private long time;
    private boolean random;
    private TimeRangeAttribute range;

    public TimeAttribute() {
        random = false;
    }

    public boolean isRandom() {
        return random;
    }

    public long getTime() {
        return time;
    }

    public boolean parseParameter(String parameter) {
        if (parameter != null && (!parameter.isEmpty())) {
            if (parameter.matches("\\d*")) {
                int s = Integer.parseInt(parameter);
                time = (1000 * s);
                return true;
            }
            if (parameter.matches("\\d*s")) {
                int s = Integer.parseInt(parameter.substring(0, parameter.indexOf('s')));
                time = (1000 * s);
                return true;
            }
            if (parameter.matches("\\d*min")) {
                int min = Integer.parseInt(parameter.substring(0, parameter.indexOf('m')));
                time = (60000 * min);
                return true;
            }
            if (parameter.matches("\\d*min\\d*s")) {
                int min = Integer.parseInt(parameter.substring(0, parameter.indexOf('m')));
                int s = Integer.parseInt(parameter.substring(parameter.indexOf('m') + 3, parameter.indexOf('s')));
                time = (60000 * min + 1000 * s);
                return true;
            }
            if (parameter.matches("\\d*-\\d*")) {
                range = new TimeRangeAttribute(parameter);
                random = true;
                return true;
            }
            if (parameter.matches("\\d*-\\d*s")) {
                int index = parameter.indexOf('s');
                range = new TimeRangeAttribute(parameter.substring(0, index));
                random = true;
                return true;
            }
            if (parameter.matches("\\d*-\\d*min")) {
                int index = parameter.indexOf('m');
                range = new TimeRangeAttribute(parameter.substring(0, index));
                range.multiplyRange(60);
                random = true;
                return true;
            }
        }
        return false;
    }

    public void setRange(int rangeMin, int rangeMax) {
        range = new TimeRangeAttribute(rangeMin, rangeMax);
        random = true;
    }

    public void setRandomTime() {
        time = range.getRandomTime();
    }
    
    public void setTime(long time){
        this.time = time;
    }
}
