package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import entity.Player;

@SuppressWarnings("serial")
public class PlayerInfoPanel extends JPanel {

	private JLabel lblPlayerNamew, lblPlayerCashw, lblPlayerDepositw, lblPlayerPropertyw, lblPlayerPointw,
			lblPlayerMoneyw;
	private ImagePanel playerAvatarw;

	public PlayerInfoPanel() {
		init();
	}

	public PlayerInfoPanel(Player p) {
		init();
		setPlayer(p);
	}

	public void setPlayer(Player p) {
		if (p != null)
			updateInfo(p);
	}

	private void updateInfo(Player p) {
		playerAvatarw.setImage(p.getAvatar());
		lblPlayerNamew.setText(p.getName());
		lblPlayerCashw.setText(String.format("%d        ", p.getCash()));
		lblPlayerDepositw.setText(String.format("%d        ", p.getDeposit()));
		lblPlayerPropertyw.setText(String.format("%d        ", p.getEstates().size()));
		lblPlayerPointw.setText(String.format("%d        ", p.getTickets()));
		lblPlayerMoneyw.setText(String.format("%d        ", p.getAsset()));
	}

	private void init() {
		this.setLayout(new GridLayout(2, 0, 10, 10));
		this.setBorder(new TitledBorder("玩家信息"));

		JPanel playerInfoPanel_0 = new JPanel();
		this.add(playerInfoPanel_0);

		playerInfoPanel_0.setLayout(null);

		playerAvatarw = new ImagePanel();
		JPanel playerInfoPanel_0_l = new JPanel();
		playerInfoPanel_0_l.setBounds(10, 10, 145, 145);
		playerInfoPanel_0_l.add(playerAvatarw);
		playerInfoPanel_0.add(playerInfoPanel_0_l);
		playerInfoPanel_0_l.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel playerInfoPanel_0_r = new JPanel(new GridLayout(2, 0, 0, 0));
		playerInfoPanel_0_r.setBounds(153, 0, 59, 166);
		lblPlayerNamew = new JLabel();
		lblPlayerNamew.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel labelw = new JLabel("玩家：");
		labelw.setHorizontalAlignment(SwingConstants.CENTER);
		playerInfoPanel_0_r.add(labelw);
		playerInfoPanel_0_r.add(lblPlayerNamew);
		playerInfoPanel_0.add(playerInfoPanel_0_r);

		JPanel playerInfoPanel_d = new JPanel(new GridLayout(5, 0, 0, 0));
		JPanel playerInfoPanel_1 = new JPanel(new GridLayout(0, 2, 0, 0));
		lblPlayerCashw = new JLabel();
		lblPlayerCashw.setHorizontalAlignment(SwingConstants.TRAILING);
		playerInfoPanel_1.add(new JLabel("        现金："));
		playerInfoPanel_1.add(lblPlayerCashw);

		JPanel playerInfoPanel_2 = new JPanel(new GridLayout(0, 2, 0, 0));
		lblPlayerDepositw = new JLabel();
		lblPlayerDepositw.setHorizontalAlignment(SwingConstants.TRAILING);
		playerInfoPanel_2.add(new JLabel("        存款："));
		playerInfoPanel_2.add(lblPlayerDepositw);

		JPanel playerInfoPanel_3 = new JPanel(new GridLayout(0, 2, 0, 0));
		lblPlayerPropertyw = new JLabel();
		lblPlayerPropertyw.setHorizontalAlignment(SwingConstants.TRAILING);
		playerInfoPanel_3.add(new JLabel("        地产："));
		playerInfoPanel_3.add(lblPlayerPropertyw);

		JPanel playerInfoPanel_4 = new JPanel(new GridLayout(0, 2, 0, 0));
		lblPlayerPointw = new JLabel();
		lblPlayerPointw.setHorizontalAlignment(SwingConstants.TRAILING);
		playerInfoPanel_4.add(new JLabel("        点券："));
		playerInfoPanel_4.add(lblPlayerPointw);

		JPanel playerInfoPanel_5 = new JPanel(new GridLayout(0, 2, 0, 0));
		lblPlayerMoneyw = new JLabel();
		lblPlayerMoneyw.setHorizontalAlignment(SwingConstants.TRAILING);
		playerInfoPanel_5.add(new JLabel("        资产："));
		playerInfoPanel_5.add(lblPlayerMoneyw);

		playerInfoPanel_d.add(playerInfoPanel_1);
		playerInfoPanel_d.add(playerInfoPanel_2);
		playerInfoPanel_d.add(playerInfoPanel_3);
		playerInfoPanel_d.add(playerInfoPanel_4);
		playerInfoPanel_d.add(playerInfoPanel_5);
		this.add(playerInfoPanel_d, BorderLayout.SOUTH);
	}

}