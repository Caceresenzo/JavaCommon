package caceresenzo.libs.common.test;

import caceresenzo.libs.event.Event;
import caceresenzo.libs.event.EventListener;
import caceresenzo.libs.event.EventManager;
import caceresenzo.libs.exception.InvalidEventException;
import caceresenzo.libs.logger.Logger;
import caceresenzo.libs.thread.ThreadUtils;

public class EventTest {
	
	public static EventTest instance = new EventTest();
	
	public class MyEvent extends Event {
		public MyEvent() {
			super(false);
			
			Logger.$("MyEvent initialized");
		}
	}
	
	public class MyListener implements EventListener {
		@Override
		public void onEvent(Event event) {
			Logger.$("MyEvent called");
			
		}
	}
	
	public static void main(String[] args) throws InvalidEventException {
		instance.doStuff();
	}
	
	public void doStuff() throws InvalidEventException {
		EventManager manager = EventManager.getInstance();
		
		final Event event = new MyEvent();
		
		manager.addListener(MyEvent.class, new MyListener());
		
		while (true) {
			ThreadUtils.sleep(1000L);

			
			manager.notify(event);
		}
	}
	
}