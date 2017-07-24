/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import pl.gda.pg.eti.kio.malicious.event.generator.EventGenerator;
import pl.gda.pg.eti.kio.malicious.event.generator.SingleRunEventGenerator;
import pl.gda.pg.eti.kio.malicious.event.generator.SomeTestEventGenerator;

/**
 *
 * @author Michał Cilińdź
 */
public class EventGeneratorFactoryTest {

    private static final String TEST_PARAM = "999";

    @Ignore
    @Test
    public void createGeneratorsWithEmptyParamTest() {
        EventGeneratorFactory factory = new EventGeneratorFactory();
        List<EventGenerator> generators = factory.createEventGenerators(null);

        Assert.assertEquals(1, generators.size());
        Assert.assertEquals(SingleRunEventGenerator.class, generators.get(0).getClass());
    }

    @Test
    public void createEventGeneratorsTest() {
        EventGeneratorFactory factory = new EventGeneratorFactory();
        List<EventGenerator> generators = factory.createEventGenerators(getTestParameters());
        Assert.assertEquals(2, generators.size());

        SomeTestEventGenerator generator = getTestGenerator(generators);
        Assert.assertNotNull(generator);
        Assert.assertEquals(generator.getParameter(), TEST_PARAM);
    }

    private SomeTestEventGenerator getTestGenerator(List<EventGenerator> generators) {
        for (EventGenerator gen : generators) {
            if (gen instanceof SomeTestEventGenerator) {
                return (SomeTestEventGenerator) gen;
            }
        }
        return null;
    }

    private Map<String, Object> getTestParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("single", 0);
        parameters.put("non-parse", 0);
        parameters.put("someTest", TEST_PARAM);
        return parameters;
    }
}
