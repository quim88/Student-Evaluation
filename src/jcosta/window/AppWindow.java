package jcosta.window;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author Joaquim Costa
 *	This class is the main window of the program 
 */
public class AppWindow extends JFrame
{
	public static int USER = -1;
	private final Dimension WINDOW_SIZE = new Dimension(600,700);	
	private JPanel _panel;
	private Menu _menu; 
	private Toolbar _toolbar;
	private MainPanel _mainPanel;
	private BorderLayout _layout;
	
	public AppWindow()
	{
		// setup content panel
		_layout = new BorderLayout();		
		_panel = new JPanel(_layout);
		
		// setup main method
		_mainPanel = new MainPanel(this);
		_panel.add(_mainPanel, BorderLayout.CENTER);	
		
		// display main menu
		_menu = new Menu(this);		
		this.setJMenuBar(_menu);
		
		// display toolbar
		_toolbar = new Toolbar(this);
		_panel.add(_toolbar, BorderLayout.PAGE_START);
		
		// setup window properties
		this.setTitle("Student Evaluation");
		this.setContentPane(_panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
	}
	
	/**
	 * this method return the instance of the MainPanel
	 * @return
	 */
	public MainPanel getMainPanel()
	{
		return _mainPanel;
	}
	
	/**
	 * this method syncs the mainpanel
	 */
	public void refresh()
	{
		_mainPanel.sync();
	}

	/**
	 * this is the main point of the program 
	 * @param args
	 */
	public static void main( String[] args )
	{
		new AppWindow();
	}
	
}
