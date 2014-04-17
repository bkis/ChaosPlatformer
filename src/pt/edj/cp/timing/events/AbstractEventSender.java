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
        listeners.add(l);
    }

    synchronized public void unregister(IEventListener l) {
        listeners.remove(l);
    }

    synchronized public void broadcast(IEvent e) {
        for (IEventListener l : listeners)
            l.receiveEvent(e);
    }
    
}
