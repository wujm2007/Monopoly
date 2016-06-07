package views;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class StorePanel extends CellPanel {
	private static final int HEIGHT = 3;
	private static final int WIDTH = 3;
	private transient ImageIcon imageIcon = new ImageIcon("./image/shop.jpg");
	private transient Image image = imageIcon.getImage();

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}

	public void setLocation(int x, int y) {
		setBounds(35 * x - 30, 35 * y - 30, WIDTH * 30, HEIGHT * 30);
	}
}