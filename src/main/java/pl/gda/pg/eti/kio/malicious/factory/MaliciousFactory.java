/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.gda.pg.eti.kio.malicious.entity.BaseMalice;
import pl.gda.pg.eti.kio.malicious.annotation.CreatableMalicious;
import pl.gda.pg.eti.kio.malicious.configuration.MaliciousProperty;

/**
 *
 * @author Michał Cilińdź
 */
public class MaliciousFactory extends BaseFactory<CreatableMalicious> {

    private static final Logger log = LogManager.getLogger(MaliciousFactory.class);
    private final EventGeneratorFactory eventGeneratorFactory = new EventGeneratorFactory();

    public List<BaseMalice> createMalicious(List<MaliciousProperty> propertiesList) {
        List<BaseMalice> maliciousList = new ArrayList<>();
        for (MaliciousProperty properties : propertiesList) {
            Class clazz = annotatedClass.get(properties.getName());
            try {
                BaseMalice malicious = (BaseMalice) clazz.newInstance();
                malicious.create(properties.getParameters(), eventGeneratorFactory);
                maliciousList.add(malicious);
            } catch (InstantiationException | IllegalAccessException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return maliciousList;
    }

    public Set<String> getMaliciousNames() {
        return annotatedClass.keySet();
    }

    @Override
    protected String getPackageName() {
        return BaseMalice.class.getPackage().getName();
    }

    @Override
    protected String getAnnotationName(CreatableMalicious annot) {
        return annot.name();
    }
}
