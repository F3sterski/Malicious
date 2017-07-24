/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.configuration.reader;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Michał Cilińdź
 */
public abstract class ConfigReader {

    private static final Pattern COMMENT_PATTERN = Pattern.compile("#.*");

    public final void read(String line) {
        if (!isEmptyOrComment(line)) {
            readLine(line.trim());
        }
    }

    public void start() {
    }

    private boolean isEmptyOrComment(String line) {
        return StringUtils.isEmpty(line) || COMMENT_PATTERN.matcher(line).matches();
    }

    protected abstract void readLine(String line);
}
