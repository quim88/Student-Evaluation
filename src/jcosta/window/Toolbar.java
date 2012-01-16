package jcosta.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JToolBar;

import jcosta.database.DB;
import jcosta.database.action.MenuAction;
import jcosta.window.dialog.ProgramDialog;
import jcosta.window.dialog.SchoolCourseDialog;
import jcosta.window.dialog.StudentCourseDialog;
import jcosta.window.dialog.StudentDialog;
import jcosta.window.dialog.AppDialog;
import jcosta.window.dialog.TermDialog;


/**
 * @author Joaquim Costa
 * This class defines the main toolbar of the windows
 *
 */
public class Toolbar extends JPanel
{
	// define constants that map to an menu icons url
	static final String ADD_STUDENT_ICON		= "../images/student_add_48x48.png";
	static final String ADD_SCHOOL_COURSE_ICON	= "../images/school_course_add_48x48.png";
	static final String ADD_STUDENT_COURSE		= "../images/student_course_add_48x48.png";
	static final String ADD_TERM_ICON 			= "../images/term_add_48x48.png";
	static final String ADD_PROGRAM_ICON		= "../images/program_add_48x48.png";
	

	private JToolBar _toolbar;
	private AppWindow _parent;
	private JButton _addStudentBtn;
	private JButton _addStudentCourseBtn;
	private JButton _addSchoolCourseBtn;
	private JButton _addProgramBtn;
	private JButton _addTermBtn;
	private JComboBox<String> _studentList;
	
	
	public Toolbar(AppWindow parentFrame)
	{
		_parent = parentFrame;
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(400, 65));
		
		_toolbar = new JToolBar("Basic Task");
		_toolbar.addSeparator();
		
		
		// setup toolbar buttons
		_addSchoolCourseBtn = createIconButton("Add School Course", ADD_SCHOOL_COURSE_ICON, "Add School Courses", MenuAction.ADD_SCHOOL_COURSE);
		_addStudentBtn = createIconButton("Add Student", ADD_STUDENT_ICON, "Add New Student", MenuAction.ADD_STUDENT);
		_addStudentCourseBtn = createIconButton("Add Course",ADD_STUDENT_COURSE, "Add Student Course", MenuAction.ADD_STUDENT_COURSE);
		_addProgramBtn = createIconButton("Add Program", ADD_PROGRAM_ICON, "Add Program of Studies", MenuAction.ADD_PROGRAM);
		_addTermBtn = createIconButton("Add Term", ADD_TERM_ICON, "Add semester Term", MenuAction.ADD_TERM);		
		
		// setup the student list
		_studentList = new JComboBox<String>();
		_studentList.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		_studentList.addActionListener(new MenuAction(_parent));
		_studentList.setActionCommand(MenuAction.CHANGED_STUDENT);
		
		this.syncStudentList();
		
		_toolbar.add(_studentList);
		
		this.add(_toolbar, BorderLayout.PAGE_START);
		
	}
	
	/**
	 * creates an icon button
	 * @param text
	 * @param iconPath
	 * @param toolTip
	 * @param actionCommand
	 * @return
	 */
	private  JButton createIconButton( String text, String iconPath, String toolTip, String actionCommand )
	{
		// get the icon resource and assign to ImageIcon
		URL url = Toolbar.class.getResource(iconPath);
		ImageIcon icon = ((url) != null) ? new ImageIcon(url) : null;
		
		JButton button = new JButton(icon);
		button.setToolTipText(toolTip);
		button.setActionCommand(actionCommand);
		button.addActionListener(new MenuAction(_parent));
		_toolbar.add(button);
		
		return button;	
	}
	
	/**
	 * gets the students in the database and setup in the combobox
	 */
	private void syncStudentList()
	{
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT id, fname, lname FROM Student";
		try
		{
			stmt = DB.getConnection().prepareStatement(sql);
			rset = stmt.executeQuery();
			
			while(rset.next())
			{
				_studentList.addItem(rset.getString(3) + ", " + rset.getString(2) + ", " + rset.getString(1));
			}			

		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}


}
