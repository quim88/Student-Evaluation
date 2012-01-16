package jcosta.database.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import jcosta.window.AppWindow;
import jcosta.window.MainPanel;
import jcosta.window.dialog.ProgramDialog;
import jcosta.window.dialog.SchoolCourseDialog;
import jcosta.window.dialog.StudentCourseDialog;
import jcosta.window.dialog.StudentDialog;
import jcosta.window.dialog.TermDialog;

/**
 * 
 * @author Joaquim Costa
 * 
 * This class performs action for the main menu
 *
 */
public class MenuAction implements ActionListener
{
	public static String ADD_SCHOOL_COURSE	= "AddSchoolCourses";
	public static String ADD_STUDENT	= "AddStudent";
	public static String ADD_STUDENT_COURSE	= "AddStudentCourse";
	public static String ADD_PROGRAM	= "AddProgram";
	public static String ADD_TERM	= "AddTerm";
	public static String VIEW_STUDENT	= "ViewStudent";
	public static String VIEW_STUDENT_COURSE	= "ViewStudentCourse";
	public static String VIEW_SCHOOL_COURSE	= "ViewSchoolCourse";
	public static String CHANGED_STUDENT	= "ChangedStudent";
	public static String SETTING	= "Setting";	
	public static String ABOUT	= "About";	
	public static String EXIT	= "Exit";	
	
	private AppWindow _parent;
	private MainPanel _mainPanel;
	
	public MenuAction(AppWindow parent)
	{
		_parent = parent;
		_mainPanel = _parent.getMainPanel();		
	}
	
	/**
	 * refresh the table with the info in the database
	 */
	private void refreshTable()
	{
		_mainPanel.setTableInfo(_mainPanel.getActiveDisplay());
		_mainPanel.sync();		
	}
	
	/**
	 * set the selected student
	 * @param e
	 */
	private void setUserId( ActionEvent e)
	{
		//AppWindow.USER = 
		JComboBox<String> st = (JComboBox) e.getSource();
		String user = st.getSelectedItem().toString();
		user = user.substring(user.lastIndexOf(',')+2);
		
		AppWindow.USER = Integer.valueOf(user);
	}
	
	
	/**
	 * Perform action from the main application and dialogs
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals(ADD_SCHOOL_COURSE))
		{
			new SchoolCourseDialog(_parent, "Add School Course");			
			// sync the table if the current display is course
			if( _mainPanel.getActiveDisplay() == _mainPanel.DISPLAY_COURSE) 
				this.refreshTable();
		}
		else if(e.getActionCommand().equals(ADD_STUDENT))
		{
			new StudentDialog(_parent, "Add Student", StudentDialog.ADD, -1);			
			// sync the table if the current display is student
			if( _mainPanel.getActiveDisplay() == _mainPanel.DISPLAY_STUDENT) 
				this.refreshTable();
			
		}
		else if(e.getActionCommand().equals(ADD_STUDENT_COURSE))
		{
			new StudentCourseDialog(_parent, "Add Class");			
			// sync the table if the the current display is class
			if( _mainPanel.getActiveDisplay() == _mainPanel.DISPLAY_CLASS) 
				this.refreshTable();
			
		}
		else if(e.getActionCommand().equals(ADD_PROGRAM))
		{
			new ProgramDialog(_parent, "Add Program");			
		}
		else if(e.getActionCommand().equals(ADD_TERM))
		{
			new TermDialog(_parent, "Add Term");
		}
		else if(e.getActionCommand().equals(VIEW_STUDENT))
		{
			_mainPanel.setTableInfo(_mainPanel.DISPLAY_STUDENT);
			_mainPanel.sync();			
		}
		else if(e.getActionCommand().equals(VIEW_STUDENT_COURSE))
		{
			_mainPanel.setTableInfo(_mainPanel.DISPLAY_CLASS);
			_mainPanel.sync();			
		}
		else if(e.getActionCommand().equals(VIEW_SCHOOL_COURSE))
		{
			_mainPanel.setTableInfo(_mainPanel.DISPLAY_COURSE);
			_mainPanel.sync();			
		}
		else if(e.getActionCommand().equals(CHANGED_STUDENT))
		{
			this.setUserId(e);
			
		}
		else if(e.getActionCommand().equals(EXIT))
		{
			_parent.dispose();
			System.exit(0);			
		}
		else if(e.getActionCommand().equals(ABOUT))
		{
			
		}
		else if(e.getActionCommand().equals(SETTING))
		{
			
		}
	
	}

}
