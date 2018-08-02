package caceresenzo.libs.event;

public interface EventListener {
	
	/**
	 * Called in response to a notify() call on the EventManager if the present listener is registered to listen for the given event.
	 */
	public void onEvent(Event event);
	
}