package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

import entity.Player;

@SuppressWarnings("serial")
public class RoadPanel extends CellPanel {
	private static final int HEIGHT = 1;
	private static final int WIDTH = 1;
	private Image image;

	protected void paintComponent(Graphics g) {
		if (image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}

	public void setLocation(int x, int y, int type) {
		this.setBorder(new LineBorder(Color.BLACK));
		if (type == 0)
			setBounds(35 * x - 30, 35 * y - 30, WIDTH * 30, HEIGHT * 30);
		else if (type == 1)
			setBounds(35 * (x - 1) - 30, 35 * (y - 1) - 30, WIDTH * 90, HEIGHT * 90);
		else if (type == 2)
			setBounds(35 * (x - 1) - 30, 35 * y - 30, WIDTH * 90, HEIGHT * 60);
		else if (type == 3)
			setBounds(35 * (x - 1) - 30, 35 * (y - 1) - 30, WIDTH * 90, HEIGHT * 90);
		else if (type == 4)
			setBounds(35 * (x - 1) - 30, 35 * (y - 1) - 30, WIDTH * 90, HEIGHT * 90);
	}

	public void setPlayerAvatar(Player p) {
		image = p.getAvatar();
		repaint();
	}

	public void setRoadBlock() {
		image = (new ImageIcon("./image/barrage.png")).getImage();
		repaint();
	}

	public void clear() {
		image = null;
		repaint();
	}
}