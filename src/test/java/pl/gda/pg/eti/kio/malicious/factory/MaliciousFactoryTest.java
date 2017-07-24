package pl.gda.pg.eti.kio.malicious.factory;

/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import pl.gda.pg.eti.kio.malicious.entity.BaseMalice;
import pl.gda.pg.eti.kio.malicious.entity.OtherTestMalicious;
import pl.gda.pg.eti.kio.malicious.entity.SomeTestMalicious;
import pl.gda.pg.eti.kio.malicious.configuration.MaliciousProperty;

/**
 *
 * @author Michał Cilińdź
 */
public class MaliciousFactoryTest {

    @Test
    public void getMaliciousNamesTest() {
        MaliciousFactory factory = new MaliciousFactory();
        Set<String> names = factory.getMaliciousNames();

        Assert.assertTrue(names.size() >= 2);
        Assert.assertTrue(names.contains("otherMalicius"));
        Assert.assertTrue(names.contains("someMalicius"));
    }

    @Ignore
    @Test
    public void createMaliciousTest() {
        MaliciousFactory factory = new MaliciousFactory();

        Set<Class> testClass = createTestClass();
        List<BaseMalice> malicious = factory.createMalicious(createTestProperties("otherMalicius"));
        removeUsedClass(malicious, testClass);
        Assert.assertTrue(malicious.size() >= 1);
        Assert.assertEquals(1, testClass.size());

        testClass = createTestClass();
        malicious = factory.createMalicious(createTestProperties("otherMalicius", "someMalicius"));
        removeUsedClass(malicious, testClass);
        Assert.assertTrue(malicious.size() >= 2);
        Assert.assertEquals(0, testClass.size());
    }

    private void removeUsedClass(List<BaseMalice> malicious, Set<Class> testClass) {
        for (BaseMalice m : malicious) {
            testClass.remove(m.getClass());
        }
    }

    private List<MaliciousProperty> createTestProperties(String... names) {
        List<MaliciousProperty> properties = new ArrayList<>();
        for (String name : names) {
            properties.add(new MaliciousProperty(name, null));
        }
        return properties;
    }

    private Set<Class> createTestClass() {
        Set<Class> testClass = new HashSet<>();
        testClass.add(OtherTestMalicious.class);
        testClass.add(SomeTestMalicious.class);
        return testClass;
    }
}
