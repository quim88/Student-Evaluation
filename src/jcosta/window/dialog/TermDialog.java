package jcosta.window.dialog;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
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

public class TermDialog extends AppDialog implements ActionListener
{
	private final Dimension SIZE = new Dimension(300,100);
	private final String ADD = "Add";
	
	private JComboBox<String> _semester;
	private JSpinner _year;
	private JButton _actionBtn;

	public TermDialog(AppWindow parent, String title)
	{
		// set the dialog
		super(parent, title);
	
		this.init();
		this.showDialog();
	}
	
	public TermDialog(AppWindow parent, String title, String[] preData )
	{
		this(parent, title);
	}
	

	private void init()
	{
		SpinnerModel yearModel = new SpinnerNumberModel(2012, 2007, 2017, 1);
		
		String[] semesterStr = { "Fall", "Spring", "Summer"};
		String[] labelStr = { "Semester:", "Year:", "Major:"};
		JLabel[] label = new JLabel[labelStr.length];
		
		// set up the panel Constraint
		this.setPreferredSize(SIZE);
		this.fill(GridBagConstraints.HORIZONTAL);
		this.setPadding(1, 1, 1, 1);
		
		// initialize the labels;
		for(int i = 0; i < 2; i++)
		{
			label[i] = new JLabel(labelStr[i]);
			this.add(label[i], 0, i);
		}

		// setup major combobox
		_semester = new JComboBox<String>(semesterStr);
		_semester.setPreferredSize(new Dimension(200, 25));
		this.add(_semester, 1, 0);

		// setup grad year
		_year = new JSpinner(yearModel);
		_year.setPreferredSize(new Dimension(200, 25));
		this.add(_year, 1, 1);
		
		// setup button
		_actionBtn = new JButton("Add Term");
		_actionBtn.setPreferredSize(new Dimension(120, 25));
		_actionBtn.setActionCommand(ADD);
		_actionBtn.addActionListener(this);
		this.fill(GridBagConstraints.NONE);
		this.setAnchor(GridBagConstraints.LAST_LINE_END);
		this.add(_actionBtn, 1, 2);

	}
	
	
	
	private void add()
	{
			int year = Integer.parseInt(_year.getValue().toString());
			PreparedStatement stmt = null;
			String sql = "INSERT INTO Term( id, semester, yr) VALUES (?,?,?)";
			try
			{
				stmt = DB.getConnection().prepareStatement(sql);
				stmt.setInt(1, generateTermId());
				stmt.setString(2, (String) _semester.getSelectedItem());
				stmt.setInt(3, year);
				
				stmt.executeUpdate();

			} catch(SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.destroyDialog();
		
	}
	
	private int generateTermId()
	{
		String termIdStr =  _year.getValue().toString().substring(2);
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

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals(ADD))
		{
			this.add();
		}
			
	}

}
