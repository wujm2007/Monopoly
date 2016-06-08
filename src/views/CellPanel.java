package views;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

@SuppressWarnings("serial")
public abstract class CellPanel extends JPanel {
	public abstract Image getImage();

	public abstract int getIconWidth();

	public abstract int getIconHeight();

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (getImage() != null)
			g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), this);
	}

	public void refresh() {
		paintImmediately(0, 0, this.getWidth(), this.getHeight());
	}

	public void setLocation(int x, int y) {
		setBounds(35 * x - 30, 35 * y - 30, getIconWidth() * 30, getIconHeight() * 30);
	}
}