package pt.edj.cp.timing.events;

/**
 *
 * @author rechtslang
 */
public interface IEventSender {
    public void register(IEventListener l);
    public void unregister(IEventListener l);
    public void broadcast(IEvent e);
}
