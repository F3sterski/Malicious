/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.configuration.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import pl.gda.pg.eti.kio.malicious.configuration.log.LoggerCreator;

/**
 *
 * @author Michał Cilińdź
 */
public class LogConfigReader extends ConfigReader {

    public static String QUEUE = "";
    
    private static final String APP_LOG_NAME = "malicious_log";
    private static final String ERROR_LOG_NAME = "error_log";
    private static final String QUEUE_IP = "queue_ip";
    private static final Pattern LOGGER_PATTERN;

    static {
        String pattern = String.format("(%s|%s|%s)\\s*=\\s*\".*\"", APP_LOG_NAME, ERROR_LOG_NAME,QUEUE_IP);
        LOGGER_PATTERN = Pattern.compile(pattern);
    }

    @Override
    protected void readLine(String line) {
        if (LOGGER_PATTERN.matcher(line).matches()) {
            createLogger(line);
        }
    }

    private void createLogger(String line) {
        String file = parseFileName(line);
        if (isAppLogger(line)) {
            LoggerCreator.createAppLogger(file);
        } else if (isErrorLogger(line)) {
            LoggerCreator.createErrorLogger(file);
        } else if (isQueue(line)){
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(line);
            while (m.find()) {
              QUEUE = m.group(1);
            }
        }
    }

    private String parseFileName(String line) {
        return StringUtils.substringBetween(line, "\"");
    }

    private boolean isAppLogger(String line) {
        return StringUtils.contains(line, APP_LOG_NAME);
    }

    private boolean isErrorLogger(String line) {
        return StringUtils.contains(line, ERROR_LOG_NAME);
    }
    
    private boolean isQueue(String line) {
        return StringUtils.contains(line, QUEUE_IP);
    }
}
