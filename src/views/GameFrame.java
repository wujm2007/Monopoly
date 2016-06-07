package views;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import entity.Cell;
import entity.Game;
import entity.MapGUI;
import entity.Player;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

public class GameFrame {
	private Game game;
	private Player currentPlayer;
	private JFrame frame;
	private JLabel lblDate, lblDay, lblPlayerName, lblPlayerCash, lblPlayerDeposit, lblPlayerPoint, lblPlayerMoney,
			lblPlayerProperty, lblFooter;
	private ImagePanel playerAvatar;
	private DicePanel dicePanel;
	private JMenu mnItem;
	private JPanel mapPanel;
	private List<String> bgmList = Arrays.asList("asuka", "rei");

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		this.refresh();
	}

	public GameFrame(Game g) {
		this.game = g;
		this.playerAvatar = new ImagePanel();
		initialize();
		this.setCurrentPlayer(game.getPlayers(false).iterator().next());
	}

	public void refresh() {
		updatePlayerInfo();
		updateCardMenu();
		updateWaring();
		updateDateInfo();
	}

	public void init() {
		refresh();
		((MapGUI) game.getMap()).reinit(mapPanel);
	}

	private void updateDateInfo() {
		lblDate.setText(String.format("%d/%d/%d", game.getDate().getYear(), game.getDate().getMonth(),
				game.getDate().getDay()));
		lblDay.setText(game.getDate().getDayOfWeek());
	}

	private void updateCardMenu() {
		mnItem.removeAll();
		this.getCurrentPlayer().getCards().forEach((card, num) -> {
			JMenuItem cardMenuItem;
			cardMenuItem = new JMenuItem(card.getName() + " x " + num);
			cardMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (card.act(currentPlayer) == 0)
						currentPlayer.removeCard(card);
					else
						game.io().alert("使用失败。");
					refresh();
				}
			});
			mnItem.add(cardMenuItem);
		});
	}

	private void updatePlayerInfo() {
		playerAvatar.setImage(currentPlayer.getAvatar());
		lblPlayerName.setText(currentPlayer.getName());
		lblPlayerCash.setText(String.format("%d        ", currentPlayer.getCash()));
		lblPlayerDeposit.setText(String.format("%d        ", currentPlayer.getDeposit()));
		lblPlayerPoint.setText(String.format("%d        ", currentPlayer.getTickets()));
		lblPlayerMoney.setText(String.format("%d        ", currentPlayer.getCash() + currentPlayer.getDeposit()));
		lblPlayerProperty.setText(String.format("%d        ", currentPlayer.getAsset()));
	}

	private void updateWaring() {
		Cell c = game.getMap().getCell(currentPlayer.getPosition());
		boolean noBlock = true;
		for (int i = 0; i < 11; i++) {
			if (c.getCellByRelativePos(i).isBlocked()) {
				this.lblFooter.setText("前方" + i + "步处有路障。");
				noBlock = false;
			}
		}
		if (noBlock)
			this.lblFooter.setText("前方10步内无路障。");
	}

	private void initialize() {
		game.getDate().addDay();
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 520);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// 地图面板开始
		mapPanel = new JPanel();
		mapPanel.setBounds(0, 0, 650, 430);
		frame.getContentPane().add(mapPanel);
		mapPanel.setLayout(null);

		dicePanel = new DicePanel();
		dicePanel.setBounds(145, 145, 90, 90);
		setDiceListner();

		((MapGUI) game.getMap()).init(mapPanel, dicePanel);

		// 地图面板结束

		// 右边栏开始

		// 游戏信息面板开始
		JPanel panel_e = new JPanel();
		panel_e.setLocation(660, 0);
		frame.getContentPane().add(panel_e);
		panel_e.setSize(224, 430);

		JPanel panel_e_0 = new JPanel(new GridLayout(2, 0, 0, 0));
		panel_e_0.setBorder(new TitledBorder("游戏信息"));
		String strTime = String.format("%d/%d/%d", game.getDate().getYear(), game.getDate().getMonth(),
				game.getDate().getDay());
		lblDate = new JLabel(strTime);
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		String strDay = String.format("%s", game.getDate().getDayOfWeek());
		lblDay = new JLabel(strDay);
		lblDay.setHorizontalAlignment(SwingConstants.CENTER);
		panel_e_0.add(lblDate);
		panel_e_0.add(lblDay);
		// 游戏信息面板结束

		// 玩家信息面板开始
		JPanel panel_e_1 = new JPanel(new GridLayout(2, 0, 10, 10));
		panel_e_1.setBorder(new TitledBorder("玩家信息"));

		JPanel panel_e_1_0 = new JPanel();
		panel_e_1.add(panel_e_1_0);

		panel_e_1_0.setLayout(null);

		JPanel panel_e_1_0_l = new JPanel();
		panel_e_1_0_l.setBounds(10, 10, 145, 145);
		panel_e_1_0_l.add(playerAvatar);
		panel_e_1_0.add(panel_e_1_0_l);
		panel_e_1_0_l.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel_e_1_0_r = new JPanel(new GridLayout(2, 0, 0, 0));
		panel_e_1_0_r.setBounds(153, 0, 59, 166);
		lblPlayerName = new JLabel();
		lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel label = new JLabel("玩家：");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_e_1_0_r.add(label);
		panel_e_1_0_r.add(lblPlayerName);
		panel_e_1_0.add(panel_e_1_0_r);

		JPanel panel_e_1_d = new JPanel(new GridLayout(5, 0, 0, 0));
		JPanel panel_e_1_1 = new JPanel(new GridLayout(0, 2, 0, 0));
		lblPlayerCash = new JLabel();
		lblPlayerCash.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_e_1_1.add(new JLabel("        现金："));
		panel_e_1_1.add(lblPlayerCash);

		JPanel panel_e_1_2 = new JPanel(new GridLayout(0, 2, 0, 0));
		lblPlayerDeposit = new JLabel();
		lblPlayerDeposit.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_e_1_2.add(new JLabel("        存款："));
		panel_e_1_2.add(lblPlayerDeposit);

		JPanel panel_e_1_3 = new JPanel(new GridLayout(0, 2, 0, 0));
		lblPlayerProperty = new JLabel();
		lblPlayerProperty.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_e_1_3.add(new JLabel("        地产："));
		panel_e_1_3.add(lblPlayerProperty);

		JPanel panel_e_1_4 = new JPanel(new GridLayout(0, 2, 0, 0));
		lblPlayerPoint = new JLabel();
		lblPlayerPoint.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_e_1_4.add(new JLabel("        点券："));
		panel_e_1_4.add(lblPlayerPoint);

		JPanel panel_e_1_5 = new JPanel(new GridLayout(0, 2, 0, 0));
		lblPlayerMoney = new JLabel();
		lblPlayerMoney.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_e_1_5.add(new JLabel("        资产："));
		panel_e_1_5.add(lblPlayerMoney);
		panel_e.setLayout(new BorderLayout(0, 0));

		panel_e_1_d.add(panel_e_1_1);
		panel_e_1_d.add(panel_e_1_2);
		panel_e_1_d.add(panel_e_1_3);
		panel_e_1_d.add(panel_e_1_4);
		panel_e_1_d.add(panel_e_1_5);
		panel_e_1.add(panel_e_1_d);

		panel_e.add(panel_e_0, BorderLayout.NORTH);
		panel_e.add(panel_e_1);

		lblFooter = new JLabel("提示");
		lblFooter.setBounds(10, 445, 500, 15);
		frame.getContentPane().add(lblFooter);
		// 玩家信息面板结束
		// 右边栏结束

		// 菜单开始
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		// 文件子菜单开始
		JMenu mnFile = new JMenu("文件");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("退出游戏");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		JMenuItem mntmSave = new JMenuItem("保存游戏");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.save();
			}
		});
		JMenuItem mntmLoad = new JMenuItem("读取游戏");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.load();
			}
		});
		mnFile.add(mntmSave);
		mnFile.add(mntmLoad);
		mnFile.add(mntmExit);
		// 文件子菜单结束

		// 查看子菜单开始
		JMenu mnCheck = new JMenu("查看");
		menuBar.add(mnCheck);
		JMenuItem mntmCheckPlayerInfo = new JMenuItem("查看玩家信息");
		mntmCheckPlayerInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new PlayerInfoDialog(game.getPlayers(true))).setVisible(true);
			}
		});
		JMenuItem mntmCheckToll = new JMenuItem("指定地点过路费");
		mntmCheckToll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int relativePos = game.io().InputInt("请输入相对位置:");
				Cell c = game.getMap().getCell(currentPlayer.getPosition());
				game.io().showInfo(c.getCellByRelativePos(relativePos).getBuilding().getDescription());
			}
		});
		mnCheck.add(mntmCheckPlayerInfo);
		mnCheck.add(mntmCheckToll);
		// 查看子菜单结束

		// 道具子菜单开始
		mnItem = new JMenu("道具");
		menuBar.add(mnItem);
		// 道具子菜单结束

		// 股票子菜单开始
		JMenu mnStock = new JMenu("股票");
		JMenuItem mntmEnterStockMarket = new JMenuItem("进入股票交易");
		mntmEnterStockMarket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.getStockMarket().stockOperation(currentPlayer);
			}
		});
		mnStock.add(mntmEnterStockMarket);
		menuBar.add(mnStock);
		// 股票子菜单结束

		// 音乐子菜单开始
		JMenu mnBGM = new JMenu("背景音乐");
		mnItem.removeAll();
		this.bgmList.forEach(m -> {
			JMenuItem bgmMenuItem;
			bgmMenuItem = new JMenuItem(m);
			bgmMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						AudioClip audioClip;
						audioClip = Applet.newAudioClip(new URL("file:///./music/" + m + ".mid"));
						audioClip.play();
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
				}
			});
			mnBGM.add(bgmMenuItem);
		});
		menuBar.add(mnBGM);
		// 音乐子菜单结束

		// 菜单结束
	}

	public void setFrameVisible(boolean b) {
		this.frame.setVisible(b);
	}

	public void changeImage(int step) {
		this.dicePanel.changeImage(step);
	}

	private void goPlayer() {
		currentPlayer.goWithTimer();
	}

	private void rollDice() {
		Timer DiceRollingTimer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = (int) (Math.random() * 6) + 1;
				dicePanel.changeImage(n);
			}
		});
		DiceRollingTimer.start();

		Timer DiceStoppingTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DiceRollingTimer.stop();
				goPlayer();
			}
		});
		DiceStoppingTimer.start();
		DiceStoppingTimer.setRepeats(false);
	}

	public void setDiceListner() {
		dicePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				rollDice();
				dicePanel.removeMouseListener(this);
			}
		});
	}
}
