/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.edj.cp.timing.events;

import java.util.HashSet;

/**
 *
 * @author rechtslang
 */
public class AbstractEventSender implements IEventSender {
    
    protected HashSet<IEventListener> listeners;
    
    protected AbstractEventSender() {
        listeners = new HashSet<IEventListener>();
    }

    synchronized public void register(IEventListener l) {
        listeners.add(l);;
    }

    synchronized public void unregister(IEventListener l) {
        listeners.remove(l);
    }

    synchronized public void broadcast(Event e) {
        for (IEventListener l : listeners)
            l.receiveEvent(e);
    }
    
}
