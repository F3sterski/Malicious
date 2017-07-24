/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.configuration.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RandomAccessFileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 *
 * @author Michał Cilińdź
 */
public class LoggerCreator {

    private static final String LOGGER_NAME = "pl.gda.pg.eti.kio.malicious";
    private static final String APP_APPENDER_NAME = "reportApender";
    private static final String APP_APPENDER_PATTERN = "%d: %m%n";
    private static final String ERROR_APPENDER_NAME = "errorApender";
    private static final String ERROR_APPENDER_PATTERN = "%d %p %c{1.}: %m%n";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String CHARSET = "UTF-8";

    private LoggerCreator() {
    }

    public static void createAppLogger(String file) {
        Filter filter = ThresholdFilter.createFilter("ERROR", "DENY", "ACCEPT");
        createAppender(file, APP_APPENDER_NAME, APP_APPENDER_PATTERN, filter);
    }

    public static void createErrorLogger(String file) {
        Filter filter = ThresholdFilter.createFilter("ERROR", "ACCEPT", "DENY");
        createAppender(file, ERROR_APPENDER_NAME, ERROR_APPENDER_PATTERN, filter);
    }

    private static void createAppender(String file, String name, String pattern, Filter filter) {
        Configuration config = getConfiguration();
        Logger logger = getLogger();
        Layout layout = PatternLayout.createLayout(pattern, config, null, CHARSET, TRUE);
        Appender appender = RandomAccessFileAppender.createAppender(file, TRUE, name, TRUE, TRUE, layout, filter, FALSE, "", config);
        logger.addAppender(appender);
        appender.start();
    }

    private static Configuration getConfiguration() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext();
        return ctx.getConfiguration();
    }

    private static Logger getLogger() {
        return (Logger) LogManager.getLogger(LOGGER_NAME);
    }
}
