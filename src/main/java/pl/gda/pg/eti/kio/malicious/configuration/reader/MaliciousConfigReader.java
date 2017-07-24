/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.configuration.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import pl.gda.pg.eti.kio.malicious.configuration.MaliciousProperty;

/**
 *
 * @author Nela Gabryelska
 */
public class MaliciousConfigReader extends ConfigReader {

    private static final Pattern PARAMETER_PATTERN = Pattern.compile("-\\w+(\\s+\\S+\\s*(\\s+-[\\w]+\\s+\\S+\\s*)*)?");
    private Set<String> malicousNames;
    private List<MaliciousProperty> configuration;

    public MaliciousConfigReader(Set<String> malicousNames) {
        this.malicousNames = malicousNames;
    }

    @Override
    public void start() {
        configuration = new ArrayList<>();
    }

    @Override
    protected void readLine(String line) {
        String name;

        int index = line.indexOf('-');
        if (index == -1) {
            name = line.trim();
            index = 0;
        } else {
            name = line.substring(0, index).trim();
        }

        if (!malicousNames.contains(name)) {
            return;
        }

        line = line.substring(index);
        if (PARAMETER_PATTERN.matcher(line).matches()) {

            HashMap map = new HashMap();
            String[] splits = line.split("\\s");

            if(splits.length == 1){
                map.put(splits[0].trim().substring(1), "0");
            }else{
                for (int i = 0; i < splits.length; i += 2) {
                    map.put(splits[i].trim().substring(1), splits[i + 1].trim());
                }  
            }

            configuration.add(new MaliciousProperty(name, map));
        }
    }

    public List<MaliciousProperty> getConfiguration() {
        return configuration;
    }
}
