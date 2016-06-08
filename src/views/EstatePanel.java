package views;

import java.awt.Image;

import javax.swing.ImageIcon;

import entity.buildings.Estate;

@SuppressWarnings("serial")
public class EstatePanel extends CellPanel {
	private Estate estate;
	private static final int HEIGHT = 1;
	private static final int WIDTH = 1;
	private transient ImageIcon imageIcon = new ImageIcon("./image/noowner0.png");
	private transient Image image = imageIcon.getImage();

	public void refresh() {
		if (this.estate.getOwner() != null) {
			image = this.estate.getOwner().getEstateImage(this.estate.getLevel());
		} else {
			image = this.imageIcon.getImage();
		}
		paintImmediately(0, 0, this.getWidth(), this.getHeight());
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public int getIconWidth() {
		return WIDTH;
	}

	@Override
	public int getIconHeight() {
		return HEIGHT;
	}
}