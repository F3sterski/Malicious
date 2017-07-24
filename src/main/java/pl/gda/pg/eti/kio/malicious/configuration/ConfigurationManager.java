/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.gda.pg.eti.kio.malicious.configuration.reader.ConfigReader;

/**
 *
 * @author Nela Gabryelska
 */
public class ConfigurationManager {

    private static final String PROPERTIES = "pl.gda.pg.eti.kio.malicious.Bundle";
    private static final String PROPERTIES_FILE_NAME = "Configuration-File";
    private static final String DEFAULT_FILE_NAME = "malicious.cfg";
    private final Logger log = LogManager.getLogger(ConfigurationManager.class);
    private final String fileName;
    private List<ConfigReader> readers;

    public ConfigurationManager() {
        this.fileName = getFileName();
        this.readers = new ArrayList<>();
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader("./" + fileName))) {
            String line;
            startReading();
            while ((line = reader.readLine()) != null) {
                read(line);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public void addReader(ConfigReader reader) {
        readers.add(reader);
    }

    private void startReading() {
        for (ConfigReader reader : readers) {
            reader.start();
        }
    }

    private void read(String line) {
        for (ConfigReader reader : readers) {
            reader.read(line);
        }
    }

    private String getFileName() {
        ResourceBundle rb = ResourceBundle.getBundle(PROPERTIES);
        String name = rb == null ? null : rb.getString(PROPERTIES_FILE_NAME);
        if (name != null && !name.isEmpty()) {
            return name;
        }
        return DEFAULT_FILE_NAME;
    }
}
