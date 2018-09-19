package model;

import view.Viewer;

/**
 * Interface that allows objects to be registered as viewers, 
 * and notifies them when a change occurs.
 *
 */
public interface Viewable {
	/**
	 * The given view is added to the list of registered views.
	 * 
	 * @param view
	 * @throws DPMSystemException If view is already registered.
	 */
	public void addView(Viewer view) throws DPMSystemException;
	
	/**
	 * The given view is removed from the list of registered views.
	 * 
	 * @param view
	 * @throws DPMSystemException If view is not in list of registered views.
	 */
	public void removeView(Viewer view) throws DPMSystemException;
	
	/**
	 * Registered views are notified that a change has been made.
	 * 
	 */
	public void notifyViews();
}
