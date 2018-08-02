
package caceresenzo.libs.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import caceresenzo.libs.exception.InvalidEventException;

/**
 * Generic event manager class. Events are string based rather than being enumerations which whould have implied a central register of events. When events are strings, only the negotiating classes needs to know about the events existance. It is transparent also to the EventManager who only delegates the events.
 * <p>
 * A potential problem with string based events are that there might be name conflicts. In a large system, one might consider prefixing the event names to avoid conflicts. If this seems to become a problem, the system is probably bad designed anyway; The number of different event types should be kept low.
 * <p>
 * The listener class must implement the EventListener interface which implies the onEvent() method. The listener class register itself in the EventManager by:
 * 
 * <pre>
 * EventManager.getInstance().addListener(new Event(), new EventListener());
 * </pre>
 * 
 * The source class of the event will call:
 * 
 * <pre>
 * EventManager.getInstance().notify(new Event());
 * </pre>
 * 
 * and the EventManager will then call the onEvent() method of every listener.
 * <p>
 * The definition of <em>object</em> and <em>data</em> is purely up to the involved classes. Typically <em>object</em> will be the source of the event (the <em>created</em> object for a "Create" event, the <em>deleted</em> object for a "Delete" event and so forth.) The additinal <em>data</em> object is for convenience only and will often be null.
 * 
 */
public class EventManager {
	private static EventManager instance = null;
	
	private HashMap<Class<?>, List<EventListener>> listeners; // String -> Collection of WeakRef (EventListener)
	
	/**
	 * Return the sole instance of the class.
	 * 
	 * @return The EventManager singelton.
	 */
	public static EventManager getInstance() {
		if (instance == null) {
			instance = new EventManager();
		}
		return instance;
	}
	
	/**
	 * Create the event manager instance.
	 */
	private EventManager() {
		listeners = new HashMap<Class<?>, List<EventListener>>();
	}
	
	/**
	 * Add a listener.
	 * 
	 * @param event
	 *            The event the listener will listen to.
	 * @param listener
	 *            The event listener object itself.
	 * @throws InvalidEventException
	 */
	public void addListener(Class<?> event, EventListener listener) {
		List<EventListener> eventListeners = listeners.get(event);
		if (eventListeners == null) {
			eventListeners = new ArrayList<EventListener>();
			listeners.put(event, eventListeners);
		}
		
		for (Iterator<EventListener> iterator = eventListeners.iterator(); iterator.hasNext();) {
			EventListener targetListener = iterator.next();
			if (listener == targetListener) {
				return;
			}
		}
		
		eventListeners.add(listener);
	}
	
	/**
	 * Remove listener from specific event.
	 * 
	 * @param event
	 *            Event to remove listener from.
	 * @param listener
	 *            Listener to remove.
	 */
	public void removeListener(Class<?> event, EventListener listener) {
		if (event == null) {
			removeListener(listener);
			return;
		}
		
		List<EventListener> eventListeners = listeners.get(event);
		if (eventListeners == null) {
			return;
		}
		
		for (Iterator<EventListener> iterator = eventListeners.iterator(); iterator.hasNext();) {
			if (listener == iterator.next()) {
				iterator.remove();
				break;
			}
		}
		
		if (eventListeners.size() == 0) {
			listeners.remove(event);
		}
	}
	
	/**
	 * Remove listener from all events it is registered by. Convenient way of cleaning up an listener object being destroyed.
	 * 
	 * @param listener
	 *            Event listener to remove.
	 */
	public void removeListener(EventListener listener) {
		List<Class<?>> eventListeners = new ArrayList<Class<?>>(listeners.keySet());
		for (Iterator<Class<?>> iterator = eventListeners.iterator(); iterator.hasNext();) {
			Class<?> event = iterator.next();
			removeListener(event, listener);
		}
	}
	
	/**
	 * Call listeners. The definition of <em>object</em> and <em>data</em> is purely up to the communicating classes.
	 */
	public void notify(Event event) {
		List<EventListener> eventListeners = listeners.get(event.getClass());
		if (eventListeners == null) {
			return;
		}
		
		List<EventListener> copy = new ArrayList<EventListener>(eventListeners); // more secure
		for (Iterator<EventListener> iterator = copy.iterator(); iterator.hasNext();) {
			EventListener listener = (EventListener) iterator.next();
			if (listener == null) {
				iterator.remove();
			} else {
				listener.onEvent(event);
			}
		}
	}
	
}
