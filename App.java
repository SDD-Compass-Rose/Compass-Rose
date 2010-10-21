package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Main class to create the program *
 */
public class App {
	
	/** Application display	 */
	private Display display;
	/** Shell the application is running within */
	private final Shell shell;
	
	/** The login and logout button on the main screen */
	private Button login_out;
	/** The button to navigate upwards in the map */
	private Button go_up;
	/** The button to bring up a roommate menu */
	private Button roommates;

	/**
	 * Constructor for the App class <p>
	 * 
	 * Creates the display
	 */
	public App() {
		display = new Display ();
		shell = new Shell (display);
		createShell(shell);
		
		shell.setText("RPI Housing Planning Tool");
		
	}
	
	/**
	 * Creates the layout of the shell of the main window
	 * 
	 * @param shell of the application
	 */
	private void createShell(Shell shell) {
		// Create shell layout
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		layout.makeColumnsEqualWidth = true;
		shell.setLayout(layout);
		
		// Add main composite window
		Composite main = new Composite(shell, SWT.FILL | SWT.BORDER);
		StackLayout layout2 = new StackLayout();
		main.setLayout(layout2);
		
		GridData data = new GridData();
		data.horizontalSpan = 3;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		main.setLayoutData(data);
		
		
		//  Add sidebar
		Composite sidebar = new Composite(shell, SWT.BORDER | SWT.FILL);
		GridLayout layout3 = new GridLayout();
		sidebar.setLayout(layout3);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		sidebar.setLayoutData(data);
	
		createSidebar(sidebar);
		createMainWindow(main);
	}

	/**
	 * Creates the main window components
	 * 
	 * @param main the composite containing the campus map
	 */
	private void createMainWindow(Composite main) {
		
		
	}

	/**
	 * Creates the sidebar components:
	 * <li> Login/logout button
	 * <li> Go up button
	 * <li> User information - name, lotto num
	 * <li> Building list
	 * <li> Room list
	 * <li> Access to roommate view
	 * 
	 * @param sidebar the composite containing the campus map
	 */
	private void createSidebar(Composite sidebar) {
		
		// set layout 
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 2;
		sidebar.setLayout(layout);
		
		go_up = new Button(sidebar, SWT.PUSH);
		go_up.setText("Go up");
		GridData data = new GridData();
		go_up.setLayoutData(data);
		
		login_out = new Button(sidebar, SWT.PUSH);
		login_out.setText("Login/Logout");
		login_out.setToolTipText("Tooltip!");
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		login_out.setLayoutData(data);
		
		login_out.addListener(SWT.PUSH, new LoginListener());
		
		Label l = new Label(sidebar, SWT.NONE);
		l.setText("Name:");
		data = new GridData();
		l.setLayoutData(data);
		
		Label name = new Label(sidebar, SWT.NONE);
		name.setText("Doctor Who");
		data = new GridData();
		name.setLayoutData(data);
		
		l = new Label(sidebar, SWT.NONE);
		l.setText("Lotto num:");
		data = new GridData();
		l.setLayoutData(data);
		
		Label lotto = new Label(sidebar, SWT.NONE);
		lotto.setText("42");
		data = new GridData();
		lotto.setLayoutData(data);
		
		Group roomBox = new Group(sidebar, SWT.NONE);
		roomBox.setText("Rooms List");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;		
		data.horizontalSpan = 2;
		roomBox.setLayoutData(data);
		
		// TODO add layout to roomBox
		
		Group buildingBox = new Group(sidebar, SWT.FILL);
		buildingBox.setText("Buildings List");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.horizontalSpan = 2;
		buildingBox.setLayoutData(data);
		
		roommates = new Button(sidebar, SWT.PUSH);
		roommates.setText("Roommate Menu");
		data = new GridData();
		data.horizontalAlignment = SWT.CENTER;
		data.horizontalSpan = 2;
		roommates.setLayoutData(data);
	}
	
	/**
	 * Starts the application
	 */
	public void run() {
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
		
	}

}
