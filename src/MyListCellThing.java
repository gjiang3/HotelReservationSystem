/**
 * Hotel Reservation System
 * CS157A Group Project
 * @author Arjun Nayak, Guohua Jiang, Peter Stadler
 * @version 1.00
 */

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

public class MyListCellThing extends JLabel implements ListCellRenderer {
	
    public MyListCellThing() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        // Assumes the stuff in the list has a pretty toString
    	String s = value.toString();
    	
        setText(s);
        setBackground(Color.WHITE);
        
        if(s.contains("Manager"))
        	setForeground(Color.BLUE);
        else if(s.contains("Customer"))
        	setForeground(Color.green);
        else if(s.contains("Room Attendant"))
        	setForeground(Color.ORANGE);
        else if(s.contains("Single Room") || s.contains("Fasle"))
        	setForeground(Color.red);
        else if(s.contains("Doube Room") || s.contains("True"))
        	setForeground(Color.green);
        else if(s.contains("Twin Room"))
        	setForeground(Color.MAGENTA);
        else 
        	setForeground(Color.BLUE);
        
        if(isSelected)
        	setBackground(Color.gray);
        return this;
    }
}