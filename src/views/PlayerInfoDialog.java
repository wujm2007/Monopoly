package views;

import java.awt.*;
import java.util.Collection;

import javax.swing.*;

import entity.Player;

@SuppressWarnings("serial")
public class PlayerInfoDialog extends JFrame {
	public PlayerInfoDialog(Collection<Player> players) {

		this.setBounds(100, 100, 225 * players.size(), 350);
		this.setLayout(new GridLayout(1, players.size(), 0, 0));

		players.forEach(p -> {
			this.add(new PlayerInfoPanel(p));
		});

		this.setResizable(false);
	}

}
