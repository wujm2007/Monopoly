package view;

import javax.swing.*;

@SuppressWarnings("serial")
public abstract class CellPanel extends JPanel {
	public void refresh() {
		paintImmediately(0, 0, this.getWidth(), this.getHeight());
	}
}