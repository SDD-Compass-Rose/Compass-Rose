package org.compassrose.housingplanningtool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * Main class to create the display
 */
public class App implements Listener {
	
	/* ********************************* DISPLAY ********************************** */
	private Display display;
	private final Shell shell;
	private Composite mainComp;
	private StackLayout layout;
	private User user;
	
	/* ********************************* SIDEBAR ********************************** */
	private Button login_out;
	private Button switchView;

	private boolean loggedIn = false; 
	private Label name;
	private Label lottoNum;
	private Table roomList;
	private Table buildingList;
	
	private Button up_room;
	private Button down_room;
	private Button up_building;
	private Button down_building;
	
	/* ***************************** ROOMMATE WINDOW ****************************** */

	private Composite roommateView;
	private Table curRoommates;
	private TableColumn curRName;
	private TableColumn curRLotto;
	private Table pendingRoommates;
	private TableColumn pendRName;
	private List seekerList;
	
	private ArrayList<Student> currentRoommateList;
	
	private Text seekerInfo;
	private Button addToSeekerList;
	private Button removeFromSeekerList;
	private Button requestRoommate;
	private Button addAll;
	private Button denyAll;
	
	private Listener nameSorter;
	private Listener lottoSorter;
	
	/* ******************************** MAP VIEW ********************************* */
	
	private Composite mapView;
	private Structure currentStruct;
	
	private Label nameOfStructure;
	private Image StructureImage;	
	private Button go_up;
	private Combo childStructs;
	private Button go_down;
	private Button addToList;
	
	/* ******************************** ADMIN VIEW ******************************* */
	private Composite adminView;

	/**
	 * Constructor for the App class <p>
	 * 
	 * Creates the display
	 */
	public App() {
		display = new Display ();
		shell = new Shell (display);
		createShell(shell);
		addListeners();
		
		currentRoommateList = new ArrayList<Student>();
		
		shell.setText("RPI Housing Planning Tool");
		
		currentStruct = new CampusMap(2);
		// TODO initialize RPI Map
		displayMapView();
	}
	
	/**
	 * Adds listeners to the following widgets:
	 * <li> up-arrow in building list
	 * <li> down-arrow in building list
	 * <li> up-arrow in room list
	 * <li> down-arrow in room list
	 * <li> login/logout button
	 * <li> roommate view / building view
	 * <li> request roommate
	 * <li> current roommate name sorting
	 * <li> current roommate lotto sorting
	 * <li> pending roommate name sorting
	 * <li> navigate up in map view
	 * <li> add self to seeker list
	 */
	private void addListeners() {
		up_room.addListener(SWT.Selection, this);
		go_down.addListener(SWT.Selection, this);
		addToList.addListener(SWT.Selection, this);		
		
		down_room.addListener(SWT.Selection, this);
		up_building.addListener(SWT.Selection, this);
		down_building.addListener(SWT.Selection, this);
		login_out.addListener(SWT.Selection, new LoginListener(this));
		switchView.addListener(SWT.Selection, this);
		roomList.addListener(SWT.Selection, this);
		buildingList.addListener(SWT.Selection, this);
		
		seekerList.addListener(SWT.Selection, this);
		addToSeekerList.addListener(SWT.Selection, this);
		removeFromSeekerList.addListener(SWT.Selection, this);
		requestRoommate.addListener(SWT.Selection, this);
		addAll.addListener(SWT.Selection, this);
		denyAll.addListener(SWT.Selection, this);
		
		makeNameSorter();
		makeLottoSorter();
		curRName.addListener(SWT.Selection, nameSorter);
		curRLotto.addListener(SWT.Selection, lottoSorter);
//		pendRName.addListener(SWT.Selection, this);
		// XXX Do we want to be able to sort pending roommates?
		
		go_up.addListener(SWT.Selection, this);
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
		mainComp = new Composite(shell, SWT.FILL | SWT.BORDER);
		StackLayout layout2 = new StackLayout();
		mainComp.setLayout(layout2);
		
		GridData data = new GridData();
		data.horizontalSpan = 3;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		mainComp.setLayoutData(data);
		
		
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
		createMainWindow(mainComp);
	}

	/**
	 * Creates the main window components
	 * 
	 * @param main the composite containing the campus map
	 */
	private void createMainWindow(Composite main) {
		layout = new StackLayout();
		main.setLayout(layout);
		
		roommateView = new Composite(main, SWT.NONE);
		createRoommateView();
		
		mapView = new Composite(main, SWT.NONE);
		createMapView();
		
		adminView = new Composite(main, SWT.NONE);
		createAdminView();
	}

	/**
	 * Creates the layout for the roommate menu
	 */
	private void createRoommateView() {
		GridLayout rLayout = new GridLayout();
		rLayout.numColumns = 3;
		rLayout.makeColumnsEqualWidth = true;
		roommateView.setLayout(rLayout);
		
		// Current Roommate Functions - name/lotto table
		Group g = new Group(roommateView, SWT.NONE);
		g.setText("Current Roommates");
		GridData data = new GridData();
		data.horizontalSpan = 2;
		data.grabExcessVerticalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		g.setLayoutData(data);
		g.setLayout(new FillLayout());
		
		// Create Current Roommate List
		curRoommates = new Table(g, SWT.VIRTUAL | SWT.BORDER | SWT.SINGLE);
		curRoommates.setHeaderVisible(true);
		curRoommates.setLinesVisible(true);
		
			// create columns
			curRName = new TableColumn(curRoommates, SWT.NONE);
			curRName.setText("Name");
			curRName.pack();
			
			curRLotto = new TableColumn(curRoommates, SWT.NONE);
			curRLotto.setText("Lotto");
			curRLotto.pack();
			
		curRoommates.setSortColumn(curRName);
		curRoommates.setSortDirection(SWT.UP);
		
		// Seeker List Functions - seeker list, add self to seeker list, request roommate, seeker info box
		g = new Group(roommateView, SWT.NONE);
		g.setText("Seeker List");
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.verticalSpan = 2;
		g.setLayoutData(data);
		
		GridLayout l = new GridLayout();
		l.numColumns = 2;
		l.makeColumnsEqualWidth = true;
		g.setLayout(l);
		
		// Add Self to Seeker List button
		addToSeekerList = new Button(g, SWT.PUSH);
		addToSeekerList.setText("Add Self to List");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		addToSeekerList.setLayoutData(data);
		
		// Remove Self from Seeker List button
		removeFromSeekerList = new Button(g, SWT.PUSH);
		removeFromSeekerList.setText("Remove Self from List");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		removeFromSeekerList.setLayoutData(data);
		
		// Seeker List
		seekerList = new List(g, SWT.BORDER | SWT.SINGLE);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessVerticalSpace = true;
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = 2;
		seekerList.setLayoutData(data);
		
		// Request Seeker Button
		requestRoommate = new Button(g, SWT.PUSH);
		requestRoommate.setText("Request as Roommate");
		requestRoommate.setEnabled(false);
		data = new GridData();
		data.horizontalAlignment = GridData.CENTER;
		data.horizontalSpan = 2;
		requestRoommate.setLayoutData(data);
		
		// Seeker Info Box
		seekerInfo = new Text(g, SWT.MULTI | SWT.V_SCROLL);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.horizontalSpan = 2;
		seekerInfo.setLayoutData(data);
				
		// Pending Roommates View
		g = new Group(roommateView, SWT.NONE);
		g.setText("Pending Roommates");
		data = new GridData();
		data.grabExcessVerticalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.horizontalSpan = 2;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		g.setLayoutData(data);
		GridLayout glayout = new GridLayout();
		glayout.makeColumnsEqualWidth = true;
		glayout.numColumns = 2;
		g.setLayout(glayout);
		
		// Create Pending Roommate List
		pendingRoommates = new Table(g, SWT.VIRTUAL | SWT.BORDER);
		pendingRoommates.setHeaderVisible(true);
		pendingRoommates.setLinesVisible(true);
		
		pendRName = new TableColumn(pendingRoommates, SWT.NONE);
		pendRName.setText("Name");
		pendRName.pack();
		
		TableColumn tc = new TableColumn(pendingRoommates, SWT.NONE);
		tc.setText("Accept");
		tc.pack();
		tc = new TableColumn(pendingRoommates, SWT.NONE);
		tc.setText("Deny");
		tc.pack();
		
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.horizontalSpan = 2;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		pendingRoommates.setLayoutData(data);
		
		// Add All button
		addAll = new Button(g, SWT.PUSH);
		addAll.setText("Accept All");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		addAll.setLayoutData(data);
		
		// Deny All button
		denyAll = new Button(g, SWT.PUSH);
		denyAll.setText("Deny All");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		denyAll.setLayoutData(data);
	}

	/**
	 * Creates the layout for the map view
	 */
	private void createMapView() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		mapView.setLayout(layout);
		
		// Name of current structure
		nameOfStructure = new Label(mapView, SWT.NONE);
		GridData data = new GridData();
		nameOfStructure.setLayoutData(data);
		
		StructureImage = new Image(display,"./src/org/compassrose/images/NorthHall4.gif");
		// TODO add image
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		
		Composite side = new Composite(mapView, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		side.setLayout(layout);
		data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.grabExcessVerticalSpace = true;
		side.setLayoutData(data);
		
		// Navigation Button
		go_up = new Button(side, SWT.PUSH);
		go_up.setText("Go up");
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = 2;
		go_up.setLayoutData(data);
		
		// Possible child structures
		childStructs = new Combo(side, SWT.DROP_DOWN | SWT.READ_ONLY);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		childStructs.setLayoutData(data);
		
		// Navigate to child structure
		go_down = new Button(side, SWT.PUSH);
		go_down.setText("Go!");
		data = new GridData();
		go_down.setLayoutData(data);
		
		// Add current structure to list
		addToList = new Button(side, SWT.PUSH);
		addToList.setText("Add to List");
		data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = GridData.FILL;
		addToList.setLayoutData(data);		
	}
	
	/**
	 * Creates the layout for the admin view
	 */
	private void createAdminView() {
		
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
		
		// Set layout 
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 2;
		sidebar.setLayout(layout);
		
		// LOGIN-LOGOUT Button
		login_out = new Button(sidebar, SWT.PUSH);
		login_out.setText("Login");
		login_out.setToolTipText("Tooltip!");
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = 2;
		login_out.setLayoutData(data);
		
		// User Information
		Label l = new Label(sidebar, SWT.NONE);
		l.setText("Name:");
		data = new GridData();
		l.setLayoutData(data);
		
		name = new Label(sidebar, SWT.NONE);
		name.setText("");
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		name.setLayoutData(data);
		
		l = new Label(sidebar, SWT.NONE);
		l.setText("Lotto num:");
		data = new GridData();
		l.setLayoutData(data);
		
		lottoNum = new Label(sidebar, SWT.NONE);
		lottoNum.setText("");
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		lottoNum.setLayoutData(data);
		
		// Horizontal separator
		l = new Label(sidebar, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		l.setLayoutData(data);
		
		// Up, Down, Title Composite
		Composite c = new Composite(sidebar, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		c.setLayout(layout);
		data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		c.setLayoutData(data);		
		
		up_room = new Button(c, SWT.PUSH);
		up_room.setImage(new Image(display, "./src/org/compassrose/images/up_arrow.gif"));
	//	up_room.setEnabled(false);
		data = new GridData();
		up_room.setLayoutData(data);
		
		down_room = new Button(c, SWT.PUSH);
		down_room.setImage(new Image(display, "./src/org/compassrose/images/down_arrow.gif"));
	//	down_room.setEnabled(false);
		data = new GridData();
		down_room.setLayoutData(data);
		
		l = new Label(c, SWT.NONE);
		l.setText("Rooms List");
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		data.grabExcessHorizontalSpace = true;
		l.setLayoutData(data);
		
		// Room Info
		roomList = new Table(sidebar, SWT.BORDER | SWT.SINGLE);
		TableColumn tc;

			// Add columns
			tc = new TableColumn(roomList, SWT.NONE);
			tc.setText("Rank");
			tc.setAlignment(SWT.CENTER);
			tc.setWidth(90); // TODO add width to Rank column in room list
			
			tc = new TableColumn(roomList, SWT.NONE);
			tc.setText("Name");
			tc.setWidth(185); // TODO add width to Name column in room list
			
		roomList.setLinesVisible(true);
		roomList.setHeaderVisible(true);
			
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;		
		data.horizontalSpan = 2;
		roomList.setLayoutData(data);
		
		// Horizontal separator
		l = new Label(sidebar, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		l.setLayoutData(data);
		
		// Up, Down, Title Composite
		c = new Composite(sidebar, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		c.setLayout(layout);
		data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		c.setLayoutData(data);		
		
		up_building = new Button(c, SWT.PUSH);
		up_building.setImage(new Image(display, "./src/org/compassrose/images/up_arrow.gif"));
		up_building.setEnabled(false);
		data = new GridData();
		up_building.setLayoutData(data);
		
		down_building = new Button(c, SWT.PUSH);
		down_building.setImage(new Image(display, "./src/org/compassrose/images/down_arrow.gif"));
		down_building.setEnabled(false);
		data = new GridData();
		down_building.setLayoutData(data);
		
		l = new Label(c, SWT.NONE);
		l.setText("Buildings List");
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		data.grabExcessHorizontalSpace = true;
		l.setLayoutData(data);
		
		// Building Info
		buildingList = new Table(sidebar, SWT.BORDER | SWT.SINGLE);
		
			// Add columns
			tc = new TableColumn(buildingList, SWT.NONE);
			tc.setText("Rank");
			tc.setAlignment(SWT.CENTER);
			tc.setWidth(90); // TODO add width to Rank column in building list
			
			tc = new TableColumn(buildingList, SWT.NONE);
			tc.setText("Name");
			tc.setWidth(185); // TODO add width to Name column in building list
			
		buildingList.setLinesVisible(true);
		buildingList.setHeaderVisible(true);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.horizontalSpan = 2;
		buildingList.setLayoutData(data);
		
		// Horizontal separator
		l = new Label(sidebar, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		l.setLayoutData(data);
		
		// Roommates Button
		switchView = new Button(sidebar, SWT.PUSH);
		switchView.setEnabled(false);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		switchView.setLayoutData(data);
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

	@Override
	public void handleEvent(Event event) {
		if (event.widget == switchView) {
			if (switchView.getText().compareTo("Roommates Menu") == 0)
				displayRoommateView();
			else if (switchView.getText().compareTo("Add Users and Approve Ratings") == 0)
				displayAdminView();
			else
				displayMapView();
		} else if (event.widget == go_up) {
			MessageDialog.openInformation(event.display.getActiveShell(),"Go up","This will go up to the parent");
			displayParent();
		} else if (event.widget == up_room) {
			moveRoom(SWT.UP);
		} else if (event.widget == down_room) {
			moveRoom(SWT.DOWN);
		} else if (event.widget == up_building) {
			moveBuilding(SWT.UP);
		} else if (event.widget == down_building) {
			moveBuilding(SWT.DOWN);
		} else if (event.widget == addToSeekerList) {
			AddToSeekerListDialog dialog = new AddToSeekerListDialog(new Shell(), (Student)this.user);
			if (dialog.open() == Window.OK ){
				// TODO update seeker list in window
				addToSeekerList.setEnabled(false);
				removeFromSeekerList.setEnabled(true);
			}
		} else if (event.widget == removeFromSeekerList) {
			if (MessageDialog.openConfirm(event.display.getActiveShell(),"Remove From Seeker List","Are you sure you would like to remove yourself from the seeker list?")) {
				addToSeekerList.setEnabled(true);
				removeFromSeekerList.setEnabled(false);
				// TODO update seeker list in window
			}
		} else if (event.widget == requestRoommate) {
			String name = seekerList.getSelection()[0];
			if (MessageDialog.openConfirm(event.display.getActiveShell(),"Request Roommate","Are you sure you want to request " + name + " as a roommate?")) {
				// TODO send request for roommate
			}
		} else if (event.widget == addAll) {
			if (MessageDialog.openConfirm(event.display.getActiveShell(),"Add All", "Are you sure you want to accept all pending roommate requests?")) {
				
				// TODO add all
			}
		} else if (event.widget == denyAll) {
			if (MessageDialog.openConfirm(event.display.getActiveShell(),"Deny All","Are you sure you want to deny all pending roommate requests?")) {
				// TODO deny all
			}
		} else if (event.widget == roomList) {
			if (roomList.getSelectionIndex() > 0) {
				up_room.setEnabled(true);
			} else {
				up_room.setEnabled(false);
			} if (roomList.getSelectionIndex() < roomList.getItemCount() -1) {
				down_room.setEnabled(false);
			} else {
				down_room.setEnabled(true);
			}
		} else if (event.widget == buildingList) {
			if (buildingList.getSelectionIndex() > 0) {
				up_building.setEnabled(true);
			} else {
				up_building.setEnabled(false);
			} if (buildingList.getSelectionIndex() < buildingList.getItemCount() -1) {
				down_building.setEnabled(false);
			} else {
				down_building.setEnabled(true);
			}
		} else if (event.widget == seekerList) {
			if (seekerList.getSelectionCount() > 0) {
				displaySeekerInfo();
				requestRoommate.setEnabled(true);
			} else {
				requestRoommate.setEnabled(false);
				seekerInfo.clearSelection();
			}
		} else if (event.widget == go_down) {
			// TODO event handling on choosing a child structure
		} else if (event.widget == addToList) {
			// TODO add building or room to list
		}
	}
	
	/**
	 * Based on the current selection of the seeker list, this will display the seeker's name, contact info, 
	 * and any other info that was entered in the text box below the seeker list.
	 */
	private void displaySeekerInfo() {
		// TODO display seeker info based on the student selected in the seeker list
	}
	

	/**
	 * Moves the currently selected building in the building list either up or down depending on the
	 * value of direction.
	 * 
	 * @param direction <code>SWT.UP</code> or <code>SWT.DOWN</code>
	 */
	private void moveBuilding(int direction) {
		int index = buildingList.getSelectionIndex();
		if (direction == SWT.UP) {
			// make sure it is not the top item
			if (index <= 0)
				return;
			
			String name = buildingList.getItem(index).getText(1);
			buildingList.getItem(index).setText(1, buildingList.getItem(index-1).getText(1));
			buildingList.getItem(index-1).setText(1, name);			
		} else {
			// make sure it is not the bottom item
			if (index > buildingList.getItemCount()-1)
				return;
			
			String name = buildingList.getItem(index).getText(1);
			buildingList.getItem(index).setText(1, buildingList.getItem(index+1).getText(1));
			buildingList.getItem(index+1).setText(1, name);
		}
	}
	/**
	 * Moves the currently selected room in the room list either up or down depending on the
	 * value of direction.
	 * 
	 * @param direction <code>SWT.UP</code> or <code>SWT.DOWN</code>
	 */
	private void moveRoom(int direction) {
		int index = roomList.getSelectionIndex();
		if (direction == SWT.UP) {
			// make sure it is not the top item
			if (index <= 0)
				return;
			
			String name = roomList.getItem(index).getText(1);
			roomList.getItem(index).setText(1, roomList.getItem(index-1).getText(1));
			roomList.getItem(index-1).setText(1, name);			
		} else {
			// make sure it is not the bottom item
			if (index > roomList.getItemCount()-1)
				return;
			
			String name = roomList.getItem(index).getText(1);
			roomList.getItem(index).setText(1, roomList.getItem(index+1).getText(1));
			roomList.getItem(index+1).setText(1, name);
		}	
	}

	/**
	 * Sets the user of this instance of the application
	 * 
	 * @param u The user that has signed in
	 */
	public void setUser(User u) {
		this.user = u;
		name.setText(user.getRCSID());
		if (user instanceof Student) {			
			lottoNum.setText(((Student)user).getLottoNumber() +"");
			switchView.setText("Roommates Menu");
			
			updateStudentDisplay();
			// TODO see if user is on seeker list
			removeFromSeekerList.setEnabled(false);
		} else { // user is an Administrator
			switchView.setText("Add Users and Approve Ratings");
		}
	}
	
	/**
	 * Updates all the widgets for students including building lists, room lists, roommates lists,
	 * pending roommates, seeker list, etc.
	 */
	private void updateStudentDisplay() {
		// TODO update EVERYTHING!  building lists, room lists, roommate lists, pending roommates, if on seekerlist, etc
		// use addToSeekerList(User u)
		// addToPendingRoommates(User u)
		// addToCurrentRoommates(User u)
		// addToBuildingList(Building b)
		// addToRoomList(Room r)
	}
	
	/**
	 * Adds a student to the seeker list
	 * 
	 * @param s the <code>Student</code> to be added
	 */
	private void addToSeekerList(Student s) {
		seekerList.add(s.getRCSID());
		// TODO how do we keep track of seeker's lists info
	}
	
	/**
	 * Adds a Student to the pending roommates list
	 * 
	 * @param s the <code>Student</code> to be added
	 */
	private void addToPendingRoommates(Student s) {
		TableItem item = new TableItem(pendingRoommates, SWT.NONE);
		item.setText(s.getRCSID());
		
		// TODO add accept/deny buttons to table
		
	}
	
	/**
	 * Adds a Student to the current roommates list
	 * 
	 * @param s the <code>Student</code> to be added
	 */
	private void addToCurrentRoommates(Student s) {
		TableItem item = new TableItem(curRoommates, SWT.NONE);
		String[] text = new String[2];
		text[0] = s.getRCSID();
		text[1] = s.getLottoNumber()+"";
		item.setText(text);
	}
	
	/**
	 * Adds a building to the building list.  By default, it is placed at the bottom-most rank
	 * 
	 * @param b the <code>Building</code> to be added
	 */
	private void addToBuildingList(Building b) {
		TableItem item = new TableItem(buildingList, SWT.NONE);
		String[] text = new String[2];
		text[0] = buildingList.getItemCount()+"";
		text[1] = b.getName();
		item.setText(text);		
	}
	
	/**
	 * Adds a room to the room list.  By default, it is placed at the bottom-most rank.
	 * 
	 * @param r the <code>Room</code< to be added
	 */
	private void addToRoomList(Room r) {
		TableItem item = new TableItem(roomList, SWT.NONE);
		String[] text = new String[2];
		text[0] = roomList.getItemCount()+"";
		text[1] = r.getName();
		item.setText(text);	
	}
		

	/**
	 * Displays the map of the parent of the current structure.  If the current structure does not have a parent,
	 * this method will do nothing.
	 */
	public void displayParent() {
		if (currentStruct.getParent() == null)
			return;
		
		// TODO display parent structure
	}

	
	/**
	 * Displays the roommate view. This includes:
	 * <li> Current Roommates
	 * <li> Pending roommate list
	 * <li> Seeker list
	 * 
	 * <p>
	 * It will be displayed in the main window area
	 */
	public void displayRoommateView() {
		layout.topControl = roommateView;	
		switchView.setText("Map View");
		mainComp.layout();
		go_up.setEnabled(false);
	}
	
	
	/**
	 * Displays the map in the main area
	 */
	public void displayMapView() {
		layout.topControl = mapView;
		if (user instanceof Administrator)
			switchView.setText("Add Users and Approve Ratings");
		else
			switchView.setText("Roommates Menu");
		if (currentStruct.getName() != null)
			nameOfStructure.setText(currentStruct.getName());
		else
			nameOfStructure.setText("");
		mainComp.layout();
		updateGoUpButton();
	}
	
	/**
	 * Displays the admin view.  This includes:
	 * <li> Approve rating options
	 * <li> Add new administrators
	 * <li> Add new Students
	 * <li> Remove users
	 */
	public void displayAdminView() {
		layout.topControl = adminView;
		switchView.setText("Map View");
		mainComp.layout();
	}

	/**
	 * Enables the "Go Up" button if the current structure has a parent.  Disabled otherwise.
	 */
	private void updateGoUpButton() {
		if (currentStruct.getParent() == null)
			go_up.setEnabled(false);
		else
			go_up.setEnabled(true);
	}

	/**
	 * Lets the gui know that the user has been logged in or has logged out
	 * @param b <code>true</code> if the user is now logged in, <code>false</code> otherwise
	 * @param u the <code>User</code> that is logged in.  This can be <code>null</code> if the user is logging out
	 */
	public void setLoggedIn(boolean b) {
		if (b) {
			login_out.setText("Logout");
			loggedIn = true;
		} else {
			login_out.setText("Login");
			loggedIn = false;
			saveToDatabase();
			user = null;
			name.setText("");
			lottoNum.setText("");
			displayMapView();
		}
		switchView.setEnabled(b);
	}
	
	/**
	 * Saves all user information to the database for future use
	 */
	private void saveToDatabase() {
		// TODO save user infor to database
	}

	
	/**
	 * @return <code>true</code> if the user is now logged in, <code>false</code> otherwise
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	/**
	 * Creates the sorter for the name column of the current roommate table
	 */
	private void makeNameSorter() {
		nameSorter = new Listener() {
			public void handleEvent(Event event) {
				// get sort column and direction
				TableColumn sortColumn = curRoommates.getSortColumn();
				TableColumn thisColumn = (TableColumn)event.widget;
				int dir = curRoommates.getSortDirection();
				if (sortColumn == thisColumn) 
					dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
				else {
					curRoommates.setSortColumn(thisColumn);
					dir = SWT.UP;
				}
				// update data in table
				curRoommates.setSortDirection(dir);
				updateCurRoommates();
			}
		};
	}
	
	/**
	 * Creates the sorter for the lotto column of the current roommate table
	 */
	private void makeLottoSorter() {
		lottoSorter = new Listener() {
			public void handleEvent(Event event) {
				// get sort column and direction
				TableColumn sortColumn = curRoommates.getSortColumn();
				TableColumn thisColumn = (TableColumn)event.widget;
				int dir = curRoommates.getSortDirection();
				if (sortColumn == thisColumn) 
					dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
				else {
					curRoommates.setSortColumn(thisColumn);
					dir = SWT.UP;
				}
				// update data in table
				curRoommates.setSortDirection(dir);
				updateCurRoommates();
			}
		};
	}
	
	/**
	 * Updates the current roommate table to account for sorting that occurred
	 */
	private void updateCurRoommates() {
		// sort the list based on the sorting column
		if (curRoommates.getSortColumn().getText().compareTo("Name") == 0) 
			sortNames(curRoommates.getSortDirection());
		else 
			sortLottos(curRoommates.getSortDirection());
		
		// update the table based on the sorted list
		curRoommates.clearAll();
		String[] str = new String[2];
			
		for (int i = 0; i < currentRoommateList.size(); i++) {
			str[0] = currentRoommateList.get(i).getRCSID();
			str[1] = currentRoommateList.get(i).getLottoNumber() + "";
			curRoommates.getItem(i).setText(str);
		}
	}
	

	/**
	 * Compares the two names based on lexographical ordering
	 * 
	 * @param name1 - first name
	 * @param name2 - second name
	 * @param dir - <code>SWT.UP</code> or <code>SWT.DOWN</code>
	 * @return the value 0 if name2 is equal to name1; a value less than 
	 * 0 if name1 is lexicographically less than name2; and a value 
	 * greater than 0 if name1 is lexicographically greater than name2.
	 */
	private int compareNames(String name1, String name2, int dir) {
		if (name1.compareToIgnoreCase(name2) == 0)
			return (dir == SWT.UP) ? name1.compareTo(name2) : -1 * name1.compareTo(name2);
		else
			return (dir == SWT.UP) ? name1.compareToIgnoreCase(name2) : -1 * name1.compareToIgnoreCase(name2);
	}

	/**
	 * Compares two roommates based on their respective lotto numbers
	 * 
	 * @param lotto1 - first lotto number
	 * @param lotto2 - second lotto number
	 * @param dir <code>SWT.UP</code> or <code>SWT.DOWN</code>
	 * @return the value 0 if lotto1 and lotto2 have the same value; a 
	 * value less than 0 if lotto1 has a lower number than lotto2; and 
	 * a value greater than 0 if lotto2 has a higher value than lotto2.
	 */
	private int compareLotto(int lotto1, int lotto2, int dir) {
		int res = lotto1 - lotto2;

		return (dir == SWT.UP) ? res : -1 * res;
	}

	/**
	 * Sorts the current roommates with respect to the names column
	 * 
	 * @param direction <code>SWT.UP</code> or <code>SWT.DOWN</code>
	 */
	protected void sortNames(final int direction) {
		if (currentRoommateList.size() <= 0)
			return;
		Collections.sort(currentRoommateList, new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				return compareNames(s1.getRCSID(), s2.getRCSID(), direction);
			}
		});
	}
	
	/**
	 * Sorts the current roommates with respect to the lotto number column
	 * 
	 * @param direction <code>SWT.UP</code> or <code>SWT.DOWN</code>
	 */
	protected void sortLottos(final int direction) {
		if (currentRoommateList.size() <= 0)
			return;
		Collections.sort(currentRoommateList, new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				return compareLotto(s1.getLottoNumber(), s2.getLottoNumber(), direction);
			}
		});
	}
}