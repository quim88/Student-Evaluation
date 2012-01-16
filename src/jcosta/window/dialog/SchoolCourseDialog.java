package jcosta.window.dialog;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 *
 * Dialog for the school course
 */
public class SchoolCourseDialog extends AppDialog implements ActionListener
{
	private final String ADD = "Add";
	private JTextField _name;
	private JComboBox<String> _code;
	private JSpinner _number;
	private JSpinner _unit;
	
	private JButton _actionBtn;

	/**
	 * Constructor takes the Parent window and title as argument
	 * @param parent
	 * @param title
	 */
	public SchoolCourseDialog(AppWindow parent, String title)
	{
		// set the dialog
		super(parent, title);
	
		this.init();
		this.showDialog();
	}
	
	/**
	 * initializes instance variables and setup componets
	 */
	private void init()
	{
		SpinnerModel unitModel = new SpinnerNumberModel(3, 1, 4, 1);
		SpinnerModel numberModel = new SpinnerNumberModel(200, 100, 600, 1);
		
		String[] labelStr = { "Name:", "Code:", "Number:", "Unit:" };
		JLabel[] label = new JLabel[labelStr.length];
		
		// set up the panel Constraint
		this.setPreferredSize(new Dimension(300,200));
		this.fill(GridBagConstraints.HORIZONTAL);
		this.setPadding(1, 1, 1, 1);
		
		// initialize the labels;
		for(int i = 0; i < 4; i++)
		{
			label[i] = new JLabel(labelStr[i]);
			this.add(label[i], 0, i);
		}

		// setup course name
		_name = new JTextField();
		_name.setPreferredSize(new Dimension(200, 25));
		this.add(_name, 1, 0);

		// setup code combobox
		_code = new JComboBox<String>();
		_code.setPreferredSize(new Dimension(200, 25));
		this.add(_code, 1, 1);
		this.syncProgramCode();

		// setup course number
		_number = new JSpinner(numberModel);
		_number.setPreferredSize(new Dimension(200, 25));
		this.add(_number, 1, 2);
		
		// setup unit
		_unit = new JSpinner(unitModel);
		_unit.setPreferredSize(new Dimension(200, 25));
		this.add(_unit, 1, 3);
	
		// setup button
		_actionBtn = new JButton("Add Course");
		_actionBtn.setPreferredSize(new Dimension(120, 25));
		_actionBtn.setActionCommand(ADD);
		_actionBtn.addActionListener(this);
		this.fill(GridBagConstraints.NONE);
		this.setAnchor(GridBagConstraints.LAST_LINE_END);
		this.add(_actionBtn, 1, 5);

	}
	
	/**
	 * gets the program code from the database and to the code combobox
	 */
	private void syncProgramCode()
	{
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT code FROM Program";
		try
		{
			stmt = DB.getConnection().prepareStatement(sql);
			rset = stmt.executeQuery();
			
			while(rset.next())
			{
				_code.addItem(rset.getString(1));
			}
			
			

		} catch(SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Adds course to the database
	 */
	private void add()
	{
		if(isValidInfo())
		{
			int courseNumber = Integer.parseInt(_number.getValue().toString());
			int courseUnit = Integer.parseInt(_unit.getValue().toString());
			PreparedStatement stmt = null;
			String sql = "INSERT INTO Course(prgm, num, name, unit) VALUES (?,?,?,?)";
			try
			{
				stmt = DB.getConnection().prepareStatement(sql);
				stmt.setString(1, (String) _code.getSelectedItem());
				stmt.setInt(2, courseNumber);
				stmt.setString(3, _name.getText());
				stmt.setInt(4, courseUnit);
				

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
	 * validates if the entered info is correct
	 * @return
	 */
	private boolean isValidInfo()
	{
		boolean flag = true;
		if(_name.getText().equals(""))
			flag = false;

		return flag;

	}

	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals(ADD))
		{
			this.add();
		}
		
	}

}
