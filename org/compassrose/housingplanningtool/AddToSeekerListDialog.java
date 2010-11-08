package org.compassrose.housingplanningtool;

import org.eclipse.swt.SWT;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog for entering in user info to be used in the seeker list
 */
public class AddToSeekerListDialog extends Dialog {
	private Student user;
	private Text nameBox;
	private Text contactBox;
	private Text infoBox;

	public AddToSeekerListDialog(Shell parent, Student u) {
		super(parent);
		
		user = u;
		setShellStyle(SWT.RESIZE | SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL);
	}
	
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		
		// adds the title to the dialog box
		shell.setText("Add Self to Seeker List");
	}
	
	protected Control createDialogArea(Composite parent) {
		// Create page layout
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 4;
		composite.setLayout(layout);
		
		// Add widgets to page
		Label l = new Label(composite, SWT.NONE);
		l.setText("Name:");
		GridData data = new GridData();
		l.setLayoutData(data);
		
		nameBox = new Text(composite, SWT.BORDER);
		nameBox.setEditable(false);
		data = new GridData();
		data.horizontalSpan = 3;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		nameBox.setLayoutData(data);
		
		l = new Label(composite, SWT.NONE);
		l.setText("Contact:");
		data = new GridData();
		l.setLayoutData(data);
		
		contactBox = new Text(composite, SWT.BORDER);
		data = new GridData();
		data.horizontalSpan = 3;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		contactBox.setLayoutData(data);
		
		l = new Label(composite, SWT.NONE);
		l.setText("Other Info:");
		data = new GridData();
		data.horizontalSpan = 4;
		l.setLayoutData(data);
		
		infoBox = new Text(composite, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		data = new GridData();
		data.horizontalSpan = 4;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.minimumHeight = 100;
		infoBox.setLayoutData(data);
		
		addData();
		
		return composite;
	}

	/**
	 * Adds rcsid and contact info to text boxes
	 */
	private void addData() {
		if (user != null) {
			nameBox.setText(user.getRCSID());
			contactBox.setText(user.getRCSID() + "@rpi.edu");
		} else {
			// XXX remove once user is working
			nameBox.setText("olyhaa");
			contactBox.setText("olyhaa@rpi.edu");
		}
	}

	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
	}
	
	protected void okPressed() {
		// TODO add user to seeker list in database
		super.okPressed();
	}
	
	protected void cancelPressed() {

		super.cancelPressed();
	}

}
