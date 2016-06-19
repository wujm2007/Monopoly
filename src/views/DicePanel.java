package views;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import entity.Player;

@SuppressWarnings("serial")
public class DicePanel extends JPanel {
	private transient ImageIcon imageIcon = new ImageIcon("./image/dice6.png");
	private transient Image image = imageIcon.getImage();

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}

	public void changeImage(int n) {
		imageIcon = new ImageIcon("./image/dice" + n + ".png");
		image = imageIcon.getImage();
		paintImmediately(0, 0, getWidth(), getHeight());
	}

	public void rollDice(Player currentPlayer) {
		Timer DiceRollingTimer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = (int) (Math.random() * 6) + 1;
				changeImage(n);
			}
		});
		DiceRollingTimer.start();

		Timer DiceStoppingTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DiceRollingTimer.stop();
				currentPlayer.goWithTimer();
			}
		});
		DiceStoppingTimer.start();
		DiceStoppingTimer.setRepeats(false);
	}
}