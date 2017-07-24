/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.entity;

import java.awt.EventQueue;
import java.awt.Frame;
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
@CreatableMalicious(name = "minimalize_window")
public class MinimalizeWindow extends BaseMalice {

    private final Logger log = LogManager.getLogger(MinimalizeWindow.class);
    private String name = getClass().getSimpleName();

    @Override
    protected void execute(MaliciousEvent event) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //Minimalizowanie głównego okna IDE
                    WindowManager.getDefault().getMainWindow().setState(Frame.ICONIFIED);
                    log.info("Completed: {} - {}", name, "MinimalizeWindow completed successfully");

                } catch (Exception ex) {
                    log.error("MinimalizeWindow completed unsuccessfully", ex);
                }
            }
        });
    }
}
