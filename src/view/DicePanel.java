package view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DicePanel extends JPanel {
	private ImageIcon imageIcon = new ImageIcon("./image/dice6.png");
	private Image image = imageIcon.getImage();

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}

	public void changeImage(int n) {
		imageIcon = new ImageIcon("./image/dice" + n + ".png");
		image = imageIcon.getImage();
		repaint();
	}

}