/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.gda.pg.eti.kio.malicious.annotation.CreatableEventGenerator;
import pl.gda.pg.eti.kio.malicious.event.generator.EventGenerator;
import pl.gda.pg.eti.kio.malicious.event.generator.RemoteRunEventGenerator;
import pl.gda.pg.eti.kio.malicious.event.generator.SingleRunEventGenerator;

/**
 *
 * @author Michał Cilińdź
 */
public class EventGeneratorFactory extends BaseFactory<CreatableEventGenerator> {

    private static final Logger log = LogManager.getLogger(EventGeneratorFactory.class);

    public List<EventGenerator> createEventGenerators(Map<String, Object> parameters) {
        List<EventGenerator> generators = new ArrayList<>();
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                Class clazz = annotatedClass.get(key);
                try {
                    addGenerator(clazz, parameters.get(key), generators);
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        }else  if(parameters.get("remote") != null){
            for (String key : parameters.keySet()) {
                Class clazz = annotatedClass.get(key);
                try {
                    addRemoteRun(clazz,generators);
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }

        } else {
            addSingleRun(generators);
        }
        return generators;
    }

    private void addGenerator(Class clazz, Object param,
            List<EventGenerator> generators) throws Exception {

        if (clazz != null) {
            EventGenerator generator = (EventGenerator) clazz.newInstance();
            generator.create(param);
            generators.add(generator);
        }
    }

    private void addSingleRun(List<EventGenerator> generators) {
        if (generators.isEmpty()) {
            SingleRunEventGenerator generator = new SingleRunEventGenerator();
            generator.create(0);
            generators.add(generator);
        }
    }
    
    private void addRemoteRun(Class clazz, List<EventGenerator> generators) throws Exception {
        if (clazz != null) {
             RemoteRunEventGenerator generator = new RemoteRunEventGenerator();
             generator.create(0);
             generators.add(generator);
        }
    }

    @Override
    protected String getPackageName() {
        return EventGenerator.class.getPackage().getName();
    }

    @Override
    protected String getAnnotationName(CreatableEventGenerator annot) {
        return annot.name();
    }
}
