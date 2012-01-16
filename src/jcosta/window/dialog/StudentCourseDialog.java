package jcosta.window.dialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import jcosta.database.DB;
import jcosta.window.AppWindow;

public class StudentCourseDialog extends AppDialog implements ActionListener
{
	private final Dimension SIZE = new Dimension(300, 250);
	private final String ADD = "Add";
	private JComboBox<String> _code;
	private JComboBox<Integer> _number;
	private JComboBox<String> _semester;
	private JComboBox<Integer> _year;
	private JSpinner _unit;
	private JComboBox<String> _grade;
	private JComboBox<String> _taken;

	private JButton _actionBtn;

	public StudentCourseDialog(AppWindow parent, String title)
	{
		// set the dialog
		super(parent, title);

		this.init();
		this.preFillInfo();
		this.showDialog();
	}

	public StudentCourseDialog(AppWindow parent, String title, String[] preData)
	{
		this(parent, title);
	}

	private void init()
	{
		SpinnerModel unitModel = new SpinnerNumberModel(3, 1, 4, 1);
		SpinnerModel numberModel = new SpinnerNumberModel(200, 100, 600, 1);

		String[] labelStr = { "Code:", "Number:", "Semester:", "Year:", "Unit", "Grade:", "Taken:" };
		String[] semesterStr = { "Fall", "Spring", "Summer" };
		String[] gradeSTr = { "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F" };
		String[] takenStr = { "Yes", "No" };
		JLabel[] label = new JLabel[labelStr.length];

		// set up the panel Constraint
		this.setPreferredSize(SIZE);
		this.fill(GridBagConstraints.HORIZONTAL);
		this.setPadding(1, 1, 1, 1);

		// initialize the labels;
		for(int i = 0; i < labelStr.length; i++)
		{
			label[i] = new JLabel(labelStr[i]);
			this.add(label[i], 0, i);
		}

		// setup code combobox
		_code = new JComboBox<String>();
		_code.setPreferredSize(new Dimension(200, 25));
		this.add(_code, 1, 0);
		
		_number = new JComboBox<Integer>();
		_number.setPreferredSize(new Dimension(200, 25));
		this.add(_number, 1, 1);
		
		_semester = new JComboBox<String>(semesterStr);
		_semester.setPreferredSize(new Dimension(200, 25));
		this.add(_semester, 1, 2);
		
		_year = new JComboBox<Integer>();
		_year.setPreferredSize(new Dimension(200, 25));
		this.add(_year, 1, 3);

		// setup unit
		_unit = new JSpinner(unitModel);
		_unit.setPreferredSize(new Dimension(200, 25));
		this.add(_unit, 1, 4);

		// setup grade
		_grade = new JComboBox<String>(gradeSTr);
		_grade.setPreferredSize(new Dimension(200, 25));
		this.add(_grade, 1, 5);

		// setup taken
		_taken = new JComboBox<String>(takenStr);
		_taken.setPreferredSize(new Dimension(200, 25));
		this.add(_taken, 1, 6);

		// setup button
		_actionBtn = new JButton("Add Course");
		_actionBtn.setPreferredSize(new Dimension(120, 25));
		_actionBtn.setActionCommand(ADD);
		_actionBtn.addActionListener(this);
		this.fill(GridBagConstraints.NONE);
		this.setAnchor(GridBagConstraints.LAST_LINE_END);
		this.add(_actionBtn, 1, 7);
	}

	/**
	 * convert letter grade to unit
	 * @param grade
	 * @return
	 */
	private double gradeToUnit(String grade)
	{
		double unit;
		if(grade.equals("A"))
			unit = 4.00;
		else if(grade.equals("A-"))
			unit = 3.70;
		else if(grade.equals("B+"))
			unit = 3.30;
		else if(grade.equals("B"))
			unit = 3.00;
		else if(grade.equals("B-"))
			unit = 2.70;
		else if(grade.equals("C+"))
			unit = 2.30;
		else if(grade.equals("C"))
			unit = 2.00;
		else if(grade.equals("C-"))
			unit = 1.70;
		else if(grade.equals("D+"))
			unit = 1.30;
		else if(grade.equals("D"))
			unit = 1.00;
		else if(grade.equals("D-"))
			unit = 0.70;
		else
			unit = 0.00;

		return unit;
	}
	
	/**
	 * Convert unit to letter grade
	 * @param unit
	 * @return
	 */
	private String unitToGrade(double unit)
	{
		String grade;
		if(unit == 4.00)
			grade = "A";
		else if(unit == 3.70)
			grade = "A-";
		else if(unit == 3.30)
			grade = "B+";
		else if(unit == 3.00)
			grade = "B" ;
		else if(unit == 2.7)
			grade = "B-";
		else if(unit == 2.30)
			grade = "C+" ;
		else if(unit == 2.00)
			grade = "C";
		else if(unit == 1.70)
			grade = "C-" ;
		else if(unit == 1.30)
			grade = "D+";
		else if(unit == 1.00)
			grade = "D";
		else if(unit == 0.70)
			grade = "D-";
		else
			grade = "F";

		return grade;
	}
	
	private int generateTermId()
	{
		String termIdStr =  _year.getSelectedItem().toString().substring(2);
		int termId;
		
		String semester = (String) _semester.getSelectedItem();
		
		if( semester.equals("Fall"))
		{
			termIdStr += "1";			
		}	
		else if( semester.equals("Spring"))
		{
			termIdStr += "2";
			
		}else if( semester.equals("Summer"))
		{
			termIdStr += "3";			
		}
		
		termId = Integer.parseInt(termIdStr);
		
		return termId;
	}

	
	/**
	 * Adds course to the database
	 */
	private void add()
	{
		
			int courseUnit = Integer.parseInt(_unit.getValue().toString());
			int courseNumber = Integer.parseInt(_number.getSelectedItem().toString());
			int termId = this.generateTermId();
			double gradeGpa = this.gradeToUnit(_grade.getSelectedItem().toString());
			String takenStr = _taken.getSelectedItem().toString();
			boolean taken = (takenStr.equals("Yes")) ? true : false;
					PreparedStatement stmt = null;
			String sql = "INSERT INTO StudentCourse(courseprgm, coursenum, studentid, termid, unit, grade, taken) VALUES (?,?,?,?,?,?,?)";
			try
			{				
				stmt = DB.getConnection().prepareStatement(sql);
				stmt.setString(1,  _code.getSelectedItem().toString());
				stmt.setInt(2, courseNumber);
				stmt.setInt(3, AppWindow.USER);
				stmt.setInt(4, termId);				
				stmt.setInt(5, courseUnit);
				stmt.setDouble(6, gradeGpa);
				stmt.setBoolean(7, taken);
				
				stmt.executeUpdate();

			} catch(SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.destroyDialog();
			this.getParentWindow().refresh();

		}
	
	/**
	 * prefill data from school course
	 */
	private void preFillInfo()
	{
		PreparedStatement stmt = null;
		ResultSet rset = null;
		String sql = "";
		try
		{
			// prefill code
			sql = "SELECT code FROM Program";
			stmt = DB.getConnection().prepareStatement(sql);
			rset = stmt.executeQuery();	
			
			while(rset.next())
				_code.addItem(new String(rset.getString(1)));
			
			// prefill year
			sql = "SELECT DISTINCT yr FROM Term";
			stmt = DB.getConnection().prepareStatement(sql);
			rset = stmt.executeQuery();
			
			while(rset.next())
				_year.addItem(new Integer(rset.getInt(1)));
			
			// prefill course number
			sql = "SELECT DISTINCT num FROM Course";
			stmt = DB.getConnection().prepareStatement(sql);
			rset = stmt.executeQuery();
			
			while(rset.next())				
				 _number.addItem(new Integer(rset.getInt(1)));
			
			
		} catch(SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals(ADD))
		{
			this.add();			
		}
		
	}

}
