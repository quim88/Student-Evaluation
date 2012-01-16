package jcosta.window.dialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import jcosta.database.DB;
import jcosta.window.AppWindow;

/**
 * 
 * @author Joaquim Costa
 * Dialog for adding and updating students
 */
public class StudentDialog extends AppDialog implements ActionListener
{
	public static final String ADD = "Add";
	public static final String UPDATE = "Update";
	private final Dimension SIZE = new Dimension(300, 200);
	private int _userId;
	private JTextField _firstName;
	private JTextField _lastName;
	private JComboBox<String> _major;
	private JComboBox<String> _studentType;
	private JSpinner _gradYear;
	private JButton _actionBtn;
	private String _activeAction;

	/**
	 * This contructor will automatically display the dialog
	 * @param parent
	 * @param title
	 * @param action
	 * @param userId
	 */
	public StudentDialog(AppWindow parent, String title, String action, int userId)
	{
		// set the dialog
		super(parent, title);

		_userId = userId;
		_activeAction = action;

		this.init();
		this.showDialog();
	}

	/**
	 * initializes instance variables and setup components
	 */
	private void init()
	{
		SpinnerModel yearModel = new SpinnerNumberModel(2012, 2007, 2017, 1);

		String[] majorStr = { "Accounting", "Biology", "Chemistry", "Computer Science",
				"Elementary Education", "History", "Mathematics", "Nursing", "Political Science",
				"--Other--" };
		String[] studentTypeStr = { "Freshman", "Sophomore", "Junior", "Senior" };
		String[] labelStr = { "First Name:", "Last Name:", "Major:", "Student Type:", "Grad Year:" };
		JLabel[] label = new JLabel[labelStr.length];

		// set up the panel Constraint
		this.setPreferredSize(SIZE);
		this.fill(GridBagConstraints.HORIZONTAL);
		this.setPadding(1, 1, 1, 1);

		// initialize the labels;
		for(int i = 0; i < 5; i++)
		{
			label[i] = new JLabel(labelStr[i]);
			this.add(label[i], 0, i);
		}

		// setup firstname textfield
		_firstName = new JTextField();
		_firstName.setPreferredSize(new Dimension(200, 25));
		this.add(_firstName, 1, 0);

		// setup lastname textfield
		_lastName = new JTextField();
		_lastName.setPreferredSize(new Dimension(200, 25));
		this.add(_lastName, 1, 1);

		// setup major combobox
		_major = new JComboBox<String>(majorStr);
		_major.setPreferredSize(new Dimension(200, 25));
		this.add(_major, 1, 2);

		// setup student type combobox
		_studentType = new JComboBox<String>(studentTypeStr);
		_studentType.setPreferredSize(new Dimension(200, 25));
		this.add(_studentType, 1, 3);

		// setup gradyear
		_gradYear = new JSpinner(yearModel);
		_gradYear.setPreferredSize(new Dimension(200, 25));
		this.add(_gradYear, 1, 4);

		// setup button
		this.setupButton();

		this.fill(GridBagConstraints.NONE);
		this.setAnchor(GridBagConstraints.LAST_LINE_END);
		this.add(_actionBtn, 1, 5);

	}
	
	/**
	 * setup button according to the action to be performed
	 */
	private void setupButton()
	{
		if(_activeAction.equals(ADD))
		{
			_actionBtn = new JButton("Add");
			_actionBtn.setActionCommand(ADD);

		} else if(_activeAction.equals(UPDATE))
		{
			_actionBtn = new JButton("Update");
			_actionBtn.setActionCommand(UPDATE);
			this.preFillInfo();
			this.repaint();
		}

		_actionBtn.setPreferredSize(new Dimension(120, 25));
		_actionBtn.addActionListener(this);

	}



	/**
	 * add new student to the datatabase
	 */
	private void addStudent()
	{
		if(isValidInfo())
		{
			int gradYear = Integer.parseInt(_gradYear.getValue().toString());
			PreparedStatement stmt = null;
			String sql = "INSERT INTO Student(fname, lname, major, gradYear, studentType) VALUES (?,?,?,?,?)";
			try
			{
				stmt = DB.getConnection().prepareStatement(sql);
				stmt.setString(1, _firstName.getText());
				stmt.setString(2, _lastName.getText());
				stmt.setString(3, (String) _major.getSelectedItem());
				stmt.setInt(4, gradYear);
				stmt.setString(5, (String) _studentType.getSelectedItem());

				stmt.executeUpdate();

			} catch(SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.destroyDialog();
			this.getParentWindow().refresh();
		}
	}

	/**
	 * updates student info in the database
	 */
	private void updateStudent()
	{
		if(isValidInfo())
		{
			int gradYear = Integer.parseInt(_gradYear.getValue().toString());
			PreparedStatement stmt = null;
			String sql = "UPDATE Student SET fname = ?, lname = ?, major = ?, gradYear = ?, studentType = ? WHERE id = ?";
			try
			{
				stmt = DB.getConnection().prepareStatement(sql);
				stmt.setString(1, _firstName.getText());
				stmt.setString(2, _lastName.getText());
				stmt.setString(3, (String) _major.getSelectedItem());
				stmt.setInt(4, gradYear);
				stmt.setString(5, (String) _studentType.getSelectedItem());
				stmt.setInt(6, _userId);

				stmt.executeUpdate();

			} catch(SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.destroyDialog();
			this.getParentWindow().refresh();
		}
	}

	/**
	 * prefill the student information when updating
	 */
	private void preFillInfo()
	{
		PreparedStatement stmt = null;
		ResultSet rset = null;
		String sql = "SELECT fname, lname, major, gradYear, studentType FROM Student WHERE id = ?";
		try
		{
			stmt = DB.getConnection().prepareStatement(sql);
			stmt.setInt(1, _userId);
			rset = stmt.executeQuery();

			// prefill data
			while(rset.next())
			{
				
				 _firstName.setText(new String(rset.getString(1)));
				 _lastName.setText(new String(rset.getString(2)));
				 _major.setSelectedItem(new String(rset.getString(3)));
				 _gradYear.setValue(new Integer(rset.getInt(4)));
				 _studentType.setSelectedItem(new String(rset.getString(5)));
				
			}
		} catch(SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * validates the entered information
	 * @return
	 */
	private boolean isValidInfo()
	{
		boolean flag = true;
		if(_firstName.getText().equals("") || _lastName.getText().equals(""))
			flag = false;

		return flag;

	}
	
	/**
	 * perform actions in button click
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals(ADD))
		{
			this.addStudent();
		} else if(e.getActionCommand().equals(UPDATE))
		{
			this.updateStudent();

		}
	}

}
