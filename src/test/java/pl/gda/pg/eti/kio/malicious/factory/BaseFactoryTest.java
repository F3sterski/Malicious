/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.factory;

import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import pl.gda.pg.eti.kio.malicious.entity.OtherTestMalicious;
import pl.gda.pg.eti.kio.malicious.entity.SomeTestMalicious;

/**
 *
 * @author Michał Cilińdź
 */
public class BaseFactoryTest {

    @Test
    public void scanAnnotatedClassTest() {
        MaliciousFactory factory = new MaliciousFactory();
        Set<Class<?>> runMalicious = factory.scanAnnotatedClass();

        Assert.assertTrue(runMalicious.size() >= 2);
        Assert.assertTrue(runMalicious.contains(OtherTestMalicious.class));
        Assert.assertTrue(runMalicious.contains(SomeTestMalicious.class));
    }
}
