package jcosta.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import jcosta.database.action.MenuAction;

/**
 * 
 * @author Joaquim Costa
 *
 * This class defines the main menu for the program
 */
public class Menu extends JMenuBar
{
	// define constants that map to an menu icons url
	static final String EXIT_ICON 				= "../images/exit_32x32.png";
	static final String SETTING_ICON			= "../images/setting_32x32.png";
	static final String ABOUT_ICON 				= "../images/about_32x32.png";
	static final String ADD_STUDENT_ICON		= "../images/student_add_32x32.png";
	static final String ADD_SCHOOL_COURSE_ICON	= "../images/school_course_add_32x32.png";
	static final String ADD_STUDENT_COURSE_ICON	= "../images/student_course_add_32x32.png";
	static final String ADD_TERM_ICON 			= "../images/term_add_32x32.png";
	static final String ADD_PROGRAM_ICON		= "../images/program_add_32x32.png";
	static final String VIEW_STUDENT_ICON	= "../images/view_user_32x32.png";
	static final String VIEW_STUDENT_COURSE_ICON 			= "../images/view_student_course_32x32.png";
	static final String VIEW_SHCOOL_ICON		= "../images/view_school_course_32x32.png";
	
		
	private AppWindow _parent;
	private JMenu _fileMenu;
	private JMenu _editMenu;
	private JMenu _helpMenu;
	private JMenu _viewMenu;
	
	private JMenuItem _settingItem;
	private JMenuItem _exitItem;
	private JMenuItem _aboutItem;
	

	private JMenuItem _addSchoolCourseItem;
	private JMenuItem _addStudentItem;
	private JMenuItem _addStudentCourseItem;
	private JMenuItem _addTermItem;
	private JMenuItem _addProgramItem;
	private JMenuItem _viewStudentTableItem;
	private JMenuItem _viewStudentCourseItem;
	private JMenuItem _viewSchoolCourseItem;
	
	
	
	public Menu( AppWindow window)
	{
		_parent = window;
		
		// setup the file menu
		_fileMenu = new JMenu("File");
		this.add(_fileMenu);
		
		_editMenu = new JMenu("Edit");
		this.add(_editMenu);
		
		// setup the help menu
		_viewMenu = new JMenu("View");
		this.add(_viewMenu);
		
		// setup the help menu
		_helpMenu = new JMenu("Help");
		this.add(_helpMenu);
		
		// setup the setting item
		_settingItem = createMenuItem("Setting", SETTING_ICON, "Setting", _fileMenu);
		
		// setup the exit item
		_exitItem = createMenuItem("Exit", EXIT_ICON, "Exit", _fileMenu);
		
		// setup the about item
		_aboutItem = createMenuItem("About", ABOUT_ICON, "About", _helpMenu);
		
		// setup the edit menu
		_addSchoolCourseItem = createMenuItem("Add School Course", ADD_SCHOOL_COURSE_ICON, MenuAction.ADD_SCHOOL_COURSE, _editMenu);
		_addStudentItem = createMenuItem("Add New Student", ADD_STUDENT_ICON, MenuAction.ADD_STUDENT_COURSE, _editMenu);
		_addStudentCourseItem = createMenuItem("Add Student Course", ADD_STUDENT_COURSE_ICON, MenuAction.ADD_SCHOOL_COURSE, _editMenu);
		_addTermItem = createMenuItem("Add Term", ADD_TERM_ICON, MenuAction.ADD_TERM, _editMenu);
		_addProgramItem = createMenuItem("Add Program", ADD_PROGRAM_ICON, MenuAction.ADD_PROGRAM, _editMenu);
		
		// setup the view menu
		_viewStudentTableItem = createMenuItem("View Student", VIEW_STUDENT_ICON, MenuAction.VIEW_STUDENT, _viewMenu);
		_viewStudentCourseItem = createMenuItem("View Student Course", VIEW_STUDENT_COURSE_ICON, MenuAction.VIEW_STUDENT_COURSE, _viewMenu);
		_viewSchoolCourseItem = createMenuItem("View School Course", VIEW_SHCOOL_ICON, MenuAction.VIEW_SCHOOL_COURSE, _viewMenu);
		
	}
	
	private JMenuItem createMenuItem( String text, String iconPath, String actionCommand, JMenu menu)
	{
		// get the icon resource and assign to ImageIcon
		URL url = Menu.class.getResource(iconPath);
		ImageIcon icon = ((url) != null) ? new ImageIcon(url) : null;
				
		JMenuItem menuItem = new JMenuItem(text, icon);
		menuItem.setActionCommand(actionCommand);
		menuItem.addActionListener(new MenuAction(_parent));
		menu.add(menuItem);
		
		return menuItem;
	}

}
