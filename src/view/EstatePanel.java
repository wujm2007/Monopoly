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
	private transient ImageIcon imageIcon = new ImageIcon("./image/noowner0.png");
	private transient Image image = imageIcon.getImage();

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}

	public void refresh() {
		if (this.estate.getOwner() != null) {
			image = this.estate.getOwner().getEstateImage(this.estate.getLevel());
		} else {
			image = this.imageIcon.getImage();
		}
		paintImmediately(0, 0, this.getWidth(), this.getHeight());
	}

	public void setLocation(int x, int y) {
		setBounds(35 * x - 30, 35 * y - 30, WIDTH * 30, HEIGHT * 30);
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}
}