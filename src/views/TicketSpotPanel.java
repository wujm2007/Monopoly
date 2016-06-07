package views;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class TicketSpotPanel extends CellPanel {
	private static final int HEIGHT = 1;
	private static final int WIDTH = 1;
	private transient ImageIcon imageIcon = new ImageIcon("./image/ticket.png");
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