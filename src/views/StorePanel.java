package views;

import java.awt.Image;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class StorePanel extends CellPanel {
	private static final int HEIGHT = 3;
	private static final int WIDTH = 3;
	private transient ImageIcon imageIcon = new ImageIcon("./image/shop.jpg");
	private transient Image image = imageIcon.getImage();

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