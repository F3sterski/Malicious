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
import org.openide.util.Exceptions;
import org.openide.windows.WindowManager;
import pl.gda.pg.eti.kio.malicious.annotation.CreatableMalicious;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;

/**
 *
 * @author Nela
 */
@CreatableMalicious(name = "hide_window")
public class HideWindow extends BaseMalice {

    private final Logger log = LogManager.getLogger(HideWindow.class);
    private String name = getClass().getSimpleName();

    @Override
    protected void execute(MaliciousEvent event) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //Ukrycie głównego okna IDE
                    WindowManager.getDefault().getMainWindow().toBack();
                    log.info("Completed: {} - {}", name, "HideWindow completed successfully");

                } catch (Exception ex) {
                    log.error("HideWindow completed unsuccessfully", ex);
                }
            }
        });
    }
}
