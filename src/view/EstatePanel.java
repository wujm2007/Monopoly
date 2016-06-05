package view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import entity.Estate;

@SuppressWarnings("serial")
public class EstatePanel extends CellPanel {
	private Estate estate;
	private static final int HEIGHT = 1;
	private static final int WIDTH = 1;
	private ImageIcon imageIcon = new ImageIcon("./image/noowner0.png");
	private Image image = imageIcon.getImage();

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}

	public void refresh() {
		image = this.estate.getOwner().getEstateImage(this.estate.getLevel());
		repaint();
	}

	public void setLocation(int x, int y) {
		setBounds(35 * x - 30, 35 * y - 30, WIDTH * 30, HEIGHT * 30);
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}
}