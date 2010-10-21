package gui;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Deals with the login and logout functions
 */
public class LoginListener implements Listener {

	@Override
	public void handleEvent(Event event) {
		// TODO call CAS
		
		MessageDialog.openInformation(event.display.getActiveShell(),"Login/Logout","This will open CAS and login or logout");

	}

}
