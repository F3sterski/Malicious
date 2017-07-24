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
import org.openide.windows.WindowManager;
import pl.gda.pg.eti.kio.malicious.annotation.CreatableMalicious;
import pl.gda.pg.eti.kio.malicious.attribute.TimeAttribute;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;

/**
 *
 * @author Marek Micał
 */
@CreatableMalicious(name = "vanish_window")
public class VanishWindow extends BaseMalice {

    private TimeAttribute attributes;
    private final Logger log = LogManager.getLogger(VanishWindow.class);
    private String name = getClass().getSimpleName();

    @Override
    protected void onStart() {
        attributes = new TimeAttribute();
        String parameter = (String) (this.getParameter("length"));
        if (!attributes.parseParameter(parameter)) {
            attributes.setRange(1, 8);
        }
    }

    public void setAttributes(TimeAttribute attributes) {
        this.attributes = attributes;
    }

    @Override
    protected void execute(MaliciousEvent event) {
        if (attributes.isRandom()) {
            attributes.setRandomTime();
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //Zniknięcie głównego okna IDE
                    WindowManager.getDefault().getMainWindow().setVisible(false);
                    Thread.sleep(attributes.getTime());
                    //Przywrócenie widoku głównego okna IDE
                    WindowManager.getDefault().getMainWindow().setVisible(true);
                    log.info("Completed: {} - {}", name, "VanishWindow completed successfully");
                } catch (Exception ex) {
                    log.error("VanishWindow completed unsuccessfully", ex);
                }
            }
        });
    }
}
