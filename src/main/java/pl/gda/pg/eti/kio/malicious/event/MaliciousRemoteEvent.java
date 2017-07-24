package pl.gda.pg.eti.kio.malicious.event;

import pl.gda.pg.eti.kio.malicious.entity.BaseMalice;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author szkol_000
 */
public class MaliciousRemoteEvent extends MaliciousEvent{

    private BaseMalice malice;
    
    public MaliciousRemoteEvent(String desc, BaseMalice malice) {
        super(desc);
        this.malice = malice;
    }

    public BaseMalice getMalice() {
        return malice;
    }

    public void setMalice(BaseMalice malice) {
        this.malice = malice;
    }
    
    
    
}
