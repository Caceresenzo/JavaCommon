package caceresenzo.libs.event;

public abstract class Event {
	
	private String name;
	private boolean cancellable, cancelled;
	
	public Event() {
		this(false);
	}
	
	public Event(boolean cancellable) {
		this.cancellable = cancellable;
	}
	
	/*
	 * Name
	 */
	public String getEventName() {
		if (name == null) {
			name = getClass().getSimpleName();
		}
		return name;
	}
	
	
	/*
	 * Cancel
	 */
	public boolean isCancellable() {
		return cancellable;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
}