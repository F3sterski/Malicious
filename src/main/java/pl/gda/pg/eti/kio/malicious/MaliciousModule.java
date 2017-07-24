/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;
import pl.gda.pg.eti.kio.malicious.entity.BaseMalice;
import pl.gda.pg.eti.kio.malicious.configuration.ConfigurationManager;
import pl.gda.pg.eti.kio.malicious.configuration.MaliciousProperty;
import pl.gda.pg.eti.kio.malicious.configuration.reader.LogConfigReader;
import pl.gda.pg.eti.kio.malicious.configuration.reader.MaliciousConfigReader;
import pl.gda.pg.eti.kio.malicious.factory.MaliciousFactory;

@ActionID(category = "Build", id = "malicious.Malicious")
@ActionRegistration(displayName = "#CTL_Malicious")
@ActionReference(path = "Shortcuts", name = "DOS-M")
@Messages("CTL_Malicious=mal")
public final class MaliciousModule extends org.openide.modules.ModuleInstall implements ActionListener {

    private final Logger log = LogManager.getLogger(MaliciousModule.class);
    List<BaseMalice> maliceList = createMaliceList();
    
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    // Method call on plugin startup
    @Override
    public void restored() {
        log.info("{} - Start", getClass().getSimpleName());
        Thread one = new Thread() {
            public void run(){
                while(true){
                    try {
                        for (BaseMalice malicious : maliceList) {
                            WindowManager.getDefault().invokeWhenUIReady(malicious);
                        } 
                        Thread.sleep(4000);
                    } catch (InterruptedException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        };
        one.start();
    }

    @Override
    public void close() {
        log.info("{} - Close", getClass().getSimpleName());
        super.close();
    }

    private List<BaseMalice> createMaliceList() {
        MaliciousFactory factory = new MaliciousFactory();
        Set<String> names = factory.getMaliciousNames();
        List<MaliciousProperty> properties = readConfiguration(names);
        return factory.createMalicious(properties);
    }

    private List<MaliciousProperty> readConfiguration(Set<String> names) {
        ConfigurationManager manager = new ConfigurationManager();
        MaliciousConfigReader maliciousReader = new MaliciousConfigReader(names);
        LogConfigReader logReader = new LogConfigReader();

        manager.addReader(maliciousReader);
        manager.addReader(logReader);
        manager.load();

        return maliciousReader.getConfiguration();
    }
}
