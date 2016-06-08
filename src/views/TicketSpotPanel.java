package views;

import java.awt.Image;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class TicketSpotPanel extends CellPanel {
	private static final int HEIGHT = 1;
	private static final int WIDTH = 1;
	private transient ImageIcon imageIcon = new ImageIcon("./image/ticket.png");
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