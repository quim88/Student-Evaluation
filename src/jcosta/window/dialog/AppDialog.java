package jcosta.window.dialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jcosta.window.AppWindow;

public class AppDialog extends JPanel
{
	private JDialog _dialog;
	private AppWindow _parent;
	private GridBagConstraints _gbc;
	
	private int _x, _y;
	
	
	public AppDialog( AppWindow parent, String title)
	{
		_parent = parent;
		_gbc = new GridBagConstraints();

		this.setLayout(new GridBagLayout());
		
		// setup dialog location
		if(_parent != null)
		{
			Point point = parent.getLocation();
			
			_x = (point.x +parent.getWidth())/2;
			_y = (point.y+parent.getHeight())/2;
		}
		
		// setup dialog
		_dialog = new JDialog( parent, title, true);
		_dialog.setContentPane(this);
		_dialog.setResizable(false);
		_dialog.setLocation(_x, _y);
		
		
	}
	
	/**
	 * Add a component to the panel
	 * @param obj
	 * @param x
	 * @param y
	 */
	public void add( Component obj, int x, int y)
	{
		_gbc.gridx = x;
		_gbc.gridy = y;
		this.add(obj, _gbc);		
	}	
	
	/**
	 * show dialog
	 */
	public void showDialog()
	{
		_dialog.pack();
		_dialog.setVisible(true);
	}
	
	/**
	 * Destroy dialog
	 */
	public void destroyDialog()
	{
		_dialog.setVisible(false);
		_dialog.dispose();		
	}
	
	/**
	 * return the parent of the dialog
	 * @return
	 */
	public AppWindow getParentWindow()
	{
		return _parent;
	}
	

	public void fill(int fill)
	{
		_gbc.fill = fill;
	}
	
	public void setPadding(int top, int left, int bottom, int right)
	{
		_gbc.insets = new Insets(top, left, bottom, right);		
	}
	
	public void setAnchor( int position)
	{
		_gbc.anchor = position;		
	}
	
	public void gridWidth(int gw)
	{
		_gbc.gridwidth = gw;
	}
	
	public void gridHeight(int gw)
	{
		_gbc.gridheight = gw;
	}
	
	public void iPadX(int paddingx)
	{
		_gbc.ipadx = paddingx;
	}
	
	public void iPadY(int paddingy)
	{
		_gbc.ipady = paddingy;
	}

	

}
