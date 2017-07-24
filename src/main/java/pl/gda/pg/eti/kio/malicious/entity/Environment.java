/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.entity;

import java.awt.EventQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.gda.pg.eti.kio.malicious.annotation.CreatableMalicious;
import pl.gda.pg.eti.kio.malicious.attribute.TimeAttribute;
import pl.gda.pg.eti.kio.malicious.attribute.TimeRangeAttribute;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;

/**
 *
 * @author Marek Micał
 */
@CreatableMalicious(name = "environment")
public class Environment extends BaseMalice {

    private TimeAttribute attributes;
    private final Logger log = LogManager.getLogger(Environment.class);
    private String name = getClass().getSimpleName();

    @Override
    protected void onStart() {
        attributes = new TimeAttribute();
        String parameter = (String) (this.getParameter("length"));
        if (!attributes.parseParameter(parameter)) {
            attributes.setRange(2, 8);
        }
    }

    @Override
    protected void execute(MaliciousEvent event) {
        if (attributes == null) {

        }
        if (attributes.isRandom() ) {
            attributes.setRandomTime();
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(attributes.getTime()*1000);
                    log.info("Completed: {} - {}", name, "Environment completed successfully");

                } catch (InterruptedException ex) {
                    log.error("Environment completed unsuccessfully", ex);
                }
            }
        });
    }

    public void setAttributes(String time) {
            attributes = new TimeAttribute();
            attributes.setTime(Integer.valueOf(time));
    }
    
}
