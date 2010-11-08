package org.compassrose.housingplanningtool;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Deals with the login and logout functions
 */
public class LoginListener implements Listener {
	App app;
    	
	public LoginListener(App app) {
		this.app = app;
	}

	@Override
	public void handleEvent(Event event) {
		// TODO call CAS
		if (app.isLoggedIn()) {
			MessageDialog.openInformation(event.display.getActiveShell(),"Logout","This will open CAS and logout");
			app.setLoggedIn(false);
		} else {
			if (MessageDialog.openQuestion(event.display.getActiveShell(),"Login","Yes = Student, No = Admin")) {
				//XXX get user info somehow... find if student or admin
				String rcsId = "olyhaa";
				User user = new Student(rcsId);
				app.setUser(user);
				app.setLoggedIn(true);
			} else {
				String rcsId = "olyhaa";
				User user = new Administrator(rcsId);
				app.setUser(user);
				app.setLoggedIn(true);
			}
		}

	}

}
