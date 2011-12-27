package jcosta.window.dialog;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import jcosta.database.DB;
import jcosta.window.AppWindow;

public class ProgramDialog extends AppDialog implements ActionListener
{
	private final Dimension SIZE = new Dimension(300,100);
	private final String ADD = "Add";
	private JTextField _code;
	private JTextField _name;
	private JButton _actionBtn;

	public ProgramDialog(AppWindow parent, String title)
	{
		// set the dialog
		super(parent, title);
	
		this.init();
		this.showDialog();
	}
	
	private void init()
	{
		String[] labelStr = { "Code:", "Program Name:"};
		JLabel[] label = new JLabel[labelStr.length];
		

		this.setPreferredSize(SIZE);
		
		// set up the panel Constraint
		this.fill(GridBagConstraints.HORIZONTAL);
		this.setPadding(1, 1, 1, 1);
		
		// initialize the labels;
		for(int i = 0; i < 2; i++)
		{
			label[i] = new JLabel(labelStr[i]);
			this.add(label[i], 0, i);
		}

		// setup code textfield
		_code = new JTextField();
		_code.setPreferredSize(new Dimension(200, 25));
		this.add(_code, 1, 0);

		// setup name textfield
		_name = new JTextField();
		_name.setPreferredSize(new Dimension(200, 25));
		this.add(_name, 1, 1);
		
		// setup button
		_actionBtn = new JButton("Add Program");
		_actionBtn.setPreferredSize(new Dimension(120, 25));
		_actionBtn.setActionCommand(ADD);
		_actionBtn.addActionListener(this);
		this.fill(GridBagConstraints.NONE);
		this.setAnchor(GridBagConstraints.LAST_LINE_END);
		this.add(_actionBtn, 1, 5);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals(ADD))
		{
			this.add();
		}
		
	}
	
	private void add()
	{
		if(isValidInfo())
		{
			PreparedStatement stmt = null;
			String sql = "INSERT INTO Program(code, name) VALUES (?,?)";
			try
			{
				stmt = DB.getConnection().prepareStatement(sql);
				stmt.setString(1, _code.getText().toUpperCase());
				stmt.setString(2, _name.getText());
				
				stmt.executeUpdate();

			} catch(SQLException e)
			{
				e.printStackTrace();
			}

			this.destroyDialog();
		}
	}
	
	private boolean isValidInfo()
	{
		boolean flag = true;
		if((_code.getText().length() != 4) || _name.getText().equals(""))
			flag = false;

		return flag;

	}
}
