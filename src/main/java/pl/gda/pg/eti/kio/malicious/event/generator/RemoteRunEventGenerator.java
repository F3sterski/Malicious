/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kio.malicious.event.generator;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.gda.pg.eti.kio.malicious.annotation.CreatableEventGenerator;
import pl.gda.pg.eti.kio.malicious.attribute.QuantityAttribute;
import pl.gda.pg.eti.kio.malicious.attribute.TimeAttribute;
import pl.gda.pg.eti.kio.malicious.configuration.reader.LogConfigReader;
import pl.gda.pg.eti.kio.malicious.entity.BaseMalice;
import pl.gda.pg.eti.kio.malicious.entity.Clipboard;
import pl.gda.pg.eti.kio.malicious.entity.DeleteSemicolons;
import pl.gda.pg.eti.kio.malicious.entity.Environment;
import pl.gda.pg.eti.kio.malicious.entity.HideWindow;
import pl.gda.pg.eti.kio.malicious.entity.KeyMalice;
import pl.gda.pg.eti.kio.malicious.entity.MinimalizeWindow;
import pl.gda.pg.eti.kio.malicious.entity.MouseMalice;
import pl.gda.pg.eti.kio.malicious.entity.Replace;
import pl.gda.pg.eti.kio.malicious.entity.VanishWindow;
import pl.gda.pg.eti.kio.malicious.event.MaliciousEvent;
import pl.gda.pg.eti.kio.malicious.event.MaliciousRemoteEvent;

/**
 *
 * @author szkol_000
 */
@CreatableEventGenerator(name = "remote")
public class RemoteRunEventGenerator extends EventGenerator{

    private final Logger log = LogManager.getLogger(RemoteRunEventGenerator.class);
        
    private static final String EVENT_DESCRIPTION = "remote run event";

    Connection connection;
    Session session;
    MessageConsumer consumer;
    
    @Override
    protected void start() throws Exception {
               try {

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(LogConfigReader.QUEUE);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("TEST.FOO");
            consumer = session.createConsumer(destination);
            Message message = consumer.receive(1000);
            log.info("Request time: " + LocalDateTime.now().toString());
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                log.info("Received: " + text);
                String time = "2";
                String fromString = "";
                String toString = "";
                if(text.split(";").length == 2 ){
                    time = text.split(";")[1];                    
                }
                if(text.split(";").length == 3 ){
                    fromString = text.split(";")[1]; 
                    toString = text.split(";")[2]; 
                }
                if(text.contains("Clipboard")) invokeEvent(new MaliciousRemoteEvent(EVENT_DESCRIPTION,new Clipboard()));
                if(text.contains("Delete Semicolons")) invokeEvent(new MaliciousRemoteEvent(EVENT_DESCRIPTION,new DeleteSemicolons()));
                if(text.contains("Environment")){
                    Environment e = new Environment();
                    e.setAttributes(time);
                    invokeEvent(new MaliciousRemoteEvent(EVENT_DESCRIPTION,e));
                }
                if(text.contains("Hide Window")) invokeEvent(new MaliciousRemoteEvent(EVENT_DESCRIPTION,new HideWindow()));
                if(text.contains("Key Malice")){
                    KeyMalice e = new KeyMalice();
                    e.setParameters(new HashMap<String, Object>() {{
                      put("single",1);
                        }});
                    invokeEvent(new MaliciousRemoteEvent(EVENT_DESCRIPTION,e));
                }
                if(text.contains("Minimalize window")) invokeEvent(new MaliciousRemoteEvent(EVENT_DESCRIPTION,new MinimalizeWindow()));
                if(text.contains("Mouse Malice")){
                    MouseMalice e = new MouseMalice();
                    e.setParameters(new HashMap<String, Object>() {{
                      put("single",0);
                        }});     
                    invokeEvent(new MaliciousRemoteEvent(EVENT_DESCRIPTION,e));
                }
                if(text.contains("Vanish Window")){
                    VanishWindow v = new VanishWindow();
                    TimeAttribute t = new TimeAttribute();
                    if(time != null && Long.valueOf(time) < 10) t.setTime(Long.valueOf(time)*1000);
                    else  t.setTime(1000);
                    v.setAttributes(t);
                    invokeEvent(new MaliciousRemoteEvent(EVENT_DESCRIPTION,v));
                }
                 if(text.contains("Replace")){
                    Replace e = new Replace();
                    e.setAttributes(fromString,toString);
                    invokeEvent(new MaliciousRemoteEvent(EVENT_DESCRIPTION,e));
                }               
            } else {
               // log.info("Received: " + message);
            }
        } catch (Exception e) {
            log.info("Caught: " + e);
            e.printStackTrace();
        } finally {
            consumer.close();
            session.close();
            connection.close();
        }
    }
    
}
