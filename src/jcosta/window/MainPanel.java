package jcosta.window;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import jcosta.database.DB;
import jcosta.window.dialog.StudentDialog;

/**
 * @author Joaquim Costa This class sets up the main panel of the windows
 * 
 */
public class MainPanel extends JPanel implements ActionListener
{

	public final int DISPLAY_STUDENT = 0;
	public final int DISPLAY_COURSE = 1;
	public final int DISPLAY_CLASS = 2;

	private final String DELETE = "Delete";
	private final String UPDATE = "Update";

	static Dimension SIZE = new Dimension(700, 400);

	private AppWindow _parent;
	private JTable _table;
	private JScrollPane _tableScroll;
	private JPanel _tableAction;
	private JButton _updateButton;
	private JButton _deleteButton;
	private int _activeDisplay;
	private Vector<String> _header;
	private Vector<Vector> _data;

	public MainPanel(AppWindow parent)
	{
		_parent = parent;
		// setup panel
		this.setLayout(new BorderLayout());
		this.setPreferredSize(SIZE);

		_activeDisplay = DISPLAY_COURSE;

		this.init();
	}

	/**
	 * initializes instance variables and components
	 */
	private void init()
	{
		_header = new Vector<String>();
		_data = new Vector<Vector>();

		this.setTableInfo(_activeDisplay);

		_table = new JTable(_data, _header);
		_tableScroll = new JScrollPane(_table);
		_table.setFillsViewportHeight(true);

		// allow single selection only
		_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		_tableAction = new JPanel();
		this.add(_tableAction, BorderLayout.SOUTH);
		this.setupButton();

		this.add(_table.getTableHeader(), BorderLayout.PAGE_START);
		this.add(_tableScroll, BorderLayout.CENTER);
	}

	/**
	 * setup the button for the main panel
	 */
	private void setupButton()
	{
		_deleteButton = new JButton("Delete Selected Item");
		_deleteButton.setActionCommand(DELETE);
		_deleteButton.addActionListener(this);
		_tableAction.add(_deleteButton);

		_updateButton = new JButton("Update Selected Item");
		_updateButton.setActionCommand(UPDATE);
		_updateButton.addActionListener(this);
		_tableAction.add(_updateButton);
	}

	/**
	 * return an integer which represents the active display
	 * 
	 * @return
	 */
	public int getActiveDisplay()
	{
		return _activeDisplay;
	}

	/**
	 * selects the information to display in the table
	 * 
	 * @param info
	 */
	public void setTableInfo(int info)
	{
		switch(info)
		{

		case DISPLAY_STUDENT:
			this.viewStudent();
			break;
		case DISPLAY_COURSE:
			this.viewSchoolCourse();
			break;
		case DISPLAY_CLASS:
			this.viewStudentCourse();
			break;
		default: // do nothing

		}

		_activeDisplay = info;
	}

	/**
	 * removes all the synchronizes the table info
	 */
	public void sync()
	{
		this.removeAll();
		/*
		 * _table = new JTable(_data, _header); _tableScroll = new
		 * JScrollPane(_table); _table.setFillsViewportHeight(true);
		 * 
		 * this.add(_table.getTableHeader(), BorderLayout.PAGE_START);
		 * this.add(_tableScroll, BorderLayout.CENTER);
		 */

		this.init();

		this.validate();
		this.repaint();

	}

	/**
	 * resets the header and data of the table
	 */
	private void resetVector()
	{
		_data.removeAllElements();
		_header.removeAllElements();
	}

	/**
	 * gets the student course data from the database and inserts into the
	 * vectors
	 */
	private void viewStudentCourse()
	{

		// reset the vector in case there are some data already
		this.resetVector();

		_header.add("Program");
		_header.add("Number");
		_header.add("Semester");
		_header.add("Year");
		_header.add("Grade");
		_header.add("Taken");

		PreparedStatement stmt = null;
		ResultSet rset = null;
		String sql = "SELECT courseprgm, coursenum, semester, yr, grade, taken FROM StudentCourse c, Term t WHERE  studentid = ? AND c.termid = t.id";

		try
		{
			stmt = DB.getConnection().prepareStatement(sql);
			stmt.setInt(1, AppWindow.USER);
			rset = stmt.executeQuery();

			// add data to the table
			while(rset.next())
			{
				Vector<Object> rowData = new Vector<Object>();
				rowData.add(new String(rset.getString(1)));
				rowData.add(new Integer(rset.getInt(2)));
				rowData.add(new String(rset.getString(3)));
				rowData.add(new Integer(rset.getInt(4)));
				rowData.add(new Double(rset.getDouble(5)));
				rowData.add(new Boolean(rset.getBoolean(6)));

				_data.add(rowData);
			}

		} catch(SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * gets student data from the datbase and inserts into the vectors
	 */
	private void viewStudent()
	{

		// reset the vector in case there are some data already
		this.resetVector();

		_header.add("Id");
		_header.add("First Name");
		_header.add("Last Name");
		_header.add("Major");
		_header.add("Grad Year");
		_header.add("Student Type");

		PreparedStatement stmt = null;
		ResultSet rset = null;
		String sql = "SELECT id, fname, lname, major, gradYear, studentType FROM Student";

		try
		{
			stmt = DB.getConnection().prepareStatement(sql);
			rset = stmt.executeQuery();

			// add data to the table
			while(rset.next())
			{
				Vector<Object> rowData = new Vector<Object>();
				rowData.add(new Integer(rset.getInt(1)));
				rowData.add(new String(rset.getString(2)));
				rowData.add(new String(rset.getString(3)));
				rowData.add(new String(rset.getString(4)));
				rowData.add(new Integer(rset.getInt(5)));
				rowData.add(new String(rset.getString(6)));

				_data.add(rowData);
			}

		} catch(SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * gets the school course data from the database and inserts them into the
	 * vectors
	 */
	private void viewSchoolCourse()
	{
		this.resetVector();

		_header.add("Program");
		_header.add("Number");
		_header.add("Name");
		_header.add("Unit");

		PreparedStatement stmt = null;
		ResultSet rset = null;
		String sql = "SELECT prgm, num, name, unit FROM Course";

		try
		{
			stmt = DB.getConnection().prepareStatement(sql);
			rset = stmt.executeQuery();

			// add data to the table
			while(rset.next())
			{
				Vector<Object> rowData = new Vector<Object>();
				rowData.add(new String(rset.getString(1)));
				rowData.add(new Integer(rset.getInt(2)));
				rowData.add(new String(rset.getString(3)));
				rowData.add(new Integer(rset.getInt(4)));

				_data.add(rowData);
			}

		} catch(SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * delete selected item from the table
	 * 
	 * @param tableView
	 */
	private void delete(int tableView)
	{
		int row = _table.getSelectedRow();
		PreparedStatement stmt = null;
		String sql = "";
		try
		{
			switch(tableView)
			{

			case DISPLAY_STUDENT:
				int id = Integer.parseInt(_table.getValueAt(row, 0).toString());
				sql = "DELETE FROM Student WHERE id = ?";
				stmt = DB.getConnection().prepareStatement(sql);
				stmt.setInt(1, id);
				break;
			case DISPLAY_COURSE:
				String prgm = (String) _table.getValueAt(row, 0);
				int num = (Integer) _table.getValueAt(row, 1);
				sql = "DELETE FROM Course WHERE prgm = ? AND num = ?";
				stmt = DB.getConnection().prepareStatement(sql);
				stmt.setString(1, prgm);
				stmt.setInt(2, num);
				break;
			case DISPLAY_CLASS:
				prgm = (String) _table.getValueAt(row, 0);
				num = (Integer) _table.getValueAt(row, 1);
				int studentid = (Integer) _table.getValueAt(row, 2);
				int termid = (Integer) _table.getValueAt(row, 3);
				sql = "DELETE FROM StudentCourse WHERE courseprgm = ? AND coursenum = ? AND studentid = ? AND termid = ?";
				stmt = DB.getConnection().prepareStatement(sql);
				stmt.setString(1, prgm);
				stmt.setInt(2, num);
				stmt.setInt(2, studentid);
				stmt.setInt(2, termid);
				break;
			default: // do nothing

			}

			// perform action in the database
			if(stmt != null)
			{
				stmt.executeUpdate();
				this.sync();
			}

		} catch(SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * perform update depending on the table displayed
	 * 
	 * @param tableView
	 */
	private void update(int tableView)
	{
		int row = _table.getSelectedRow();
		switch(tableView)
		{
		case DISPLAY_STUDENT:
			int id = Integer.parseInt(_table.getValueAt(row, 0).toString());
			new StudentDialog(_parent, "Update Student", StudentDialog.UPDATE, id);
			break;
		case DISPLAY_COURSE:
			break;
		case DISPLAY_CLASS:
			break;
		default: // do nothing

		}
	}

	/**
	 * perform actions when the button is clicked
	 */
	public void actionPerformed(ActionEvent e)
	{
		// check if any row is selected before proceed
		if(_table.getSelectedRow() > -1)
		{
			if(e.getActionCommand().equals(DELETE))
			{
				System.out.println(DELETE);
				this.delete(_activeDisplay);
			} else if(e.getActionCommand().equals(UPDATE))
			{
				System.out.println(UPDATE);
				this.update(_activeDisplay);
			}
		}
	}
}
