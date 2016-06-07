package views;

import java.awt.*;
import java.util.Collection;

import javax.swing.*;
import javax.swing.border.*;

import entity.Player;

@SuppressWarnings("serial")
public class PlayerInfoDialog extends JFrame {
	public PlayerInfoDialog(Collection<Player> players) {

		this.setBounds(100, 100, 225 * players.size(), 300);
		this.setLayout(new GridLayout(1, players.size(), 0, 0));

		players.forEach(p -> {
			this.add(new PlayerInfoPanel(p));
		});

		this.setResizable(false);
	}

	class PlayerInfoPanel extends JPanel {

		public PlayerInfoPanel(Player p) {
			init(p);
		}

		private void init(Player p) {

			this.setLayout(new BorderLayout(0, 0));
			this.setBorder(new TitledBorder("玩家信息"));

			JPanel playerInfoPanel_0 = new JPanel();
			this.add(playerInfoPanel_0);

			playerInfoPanel_0.setLayout(null);

			ImagePanel playerAvatarw = new ImagePanel();
			playerAvatarw.setImage(p.getAvatar());
			JPanel playerInfoPanel_0_l = new JPanel();
			playerInfoPanel_0_l.setBounds(10, 10, 145, 145);
			playerInfoPanel_0_l.add(playerAvatarw);
			playerInfoPanel_0.add(playerInfoPanel_0_l);
			playerInfoPanel_0_l.setLayout(new GridLayout(1, 0, 0, 0));

			JPanel playerInfoPanel_0_r = new JPanel(new GridLayout(2, 0, 0, 0));
			playerInfoPanel_0_r.setBounds(153, 0, 59, 166);
			JLabel lblPlayerNamew = new JLabel(p.getName());
			lblPlayerNamew.setHorizontalAlignment(SwingConstants.CENTER);
			JLabel labelw = new JLabel("玩家：");
			labelw.setHorizontalAlignment(SwingConstants.CENTER);
			playerInfoPanel_0_r.add(labelw);
			playerInfoPanel_0_r.add(lblPlayerNamew);
			playerInfoPanel_0.add(playerInfoPanel_0_r);

			JPanel playerInfoPanel_d = new JPanel(new GridLayout(5, 0, 0, 0));
			JPanel playerInfoPanel_1 = new JPanel(new GridLayout(0, 2, 0, 0));
			JLabel lblPlayerCashw = new JLabel(String.format("%d        ", p.getCash()));
			lblPlayerCashw.setHorizontalAlignment(SwingConstants.TRAILING);
			playerInfoPanel_1.add(new JLabel("        现金："));
			playerInfoPanel_1.add(lblPlayerCashw);

			JPanel playerInfoPanel_2 = new JPanel(new GridLayout(0, 2, 0, 0));
			JLabel lblPlayerDepositw = new JLabel(String.format("%d        ", p.getDeposit()));
			lblPlayerDepositw.setHorizontalAlignment(SwingConstants.TRAILING);
			playerInfoPanel_2.add(new JLabel("        存款："));
			playerInfoPanel_2.add(lblPlayerDepositw);

			JPanel playerInfoPanel_3 = new JPanel(new GridLayout(0, 2, 0, 0));
			JLabel lblPlayerPropertyw = new JLabel(String.format("%d        ", p.getEstates().size()));
			lblPlayerPropertyw.setHorizontalAlignment(SwingConstants.TRAILING);
			playerInfoPanel_3.add(new JLabel("        地产："));
			playerInfoPanel_3.add(lblPlayerPropertyw);

			JPanel playerInfoPanel_4 = new JPanel(new GridLayout(0, 2, 0, 0));
			JLabel lblPlayerPointw = new JLabel(String.format("%d        ", p.getTickets()));
			lblPlayerPointw.setHorizontalAlignment(SwingConstants.TRAILING);
			playerInfoPanel_4.add(new JLabel("        点券："));
			playerInfoPanel_4.add(lblPlayerPointw);

			JPanel playerInfoPanel_5 = new JPanel(new GridLayout(0, 2, 0, 0));
			JLabel lblPlayerMoneyw = new JLabel(String.format("%d        ", p.getAsset()));
			lblPlayerMoneyw.setHorizontalAlignment(SwingConstants.TRAILING);
			playerInfoPanel_5.add(new JLabel("        资产："));
			playerInfoPanel_5.add(lblPlayerMoneyw);

			playerInfoPanel_d.add(playerInfoPanel_1);
			playerInfoPanel_d.add(playerInfoPanel_2);
			playerInfoPanel_d.add(playerInfoPanel_3);
			playerInfoPanel_d.add(playerInfoPanel_4);
			playerInfoPanel_d.add(playerInfoPanel_5);
			this.add(playerInfoPanel_d, BorderLayout.SOUTH);
			// left
		}
	}
}
