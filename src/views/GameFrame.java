package views;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import entity.Cell;
import entity.Game;
import entity.MapGUI;
import entity.Player;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class GameFrame {
	private Game game;
	private Player currentPlayer;
	private JFrame frame;
	private JLabel lblFooter;
	private DicePanel dicePanel;
	private JMenu mnItem;
	private JPanel mapPanel;
	private List<String> bgmList = Arrays.asList("asuka", "rei");
	private PlayerInfoPanel playerInfoPanel;
	private DatePanel datePanel;

	public DatePanel getDatePanel() {
		if (this.datePanel == null)
			this.datePanel = new DatePanel();
		return datePanel;
	}

	public PlayerInfoPanel getPlayerInfoPanel() {
		if (this.playerInfoPanel == null)
			this.playerInfoPanel = new PlayerInfoPanel();
		return playerInfoPanel;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		this.refresh();
	}

	public GameFrame(Game g) {
		this.game = g;
		initialize();
		this.setCurrentPlayer(game.getPlayers(false).iterator().next());
		this.frame.setVisible(true);
		this.frame.setTitle("Monopoly by WJM");
	}

	public void refresh() {
		getPlayerInfoPanel().setPlayer(currentPlayer);
		updateCardMenu();
		updateWaring();
		getDatePanel().updateInfo(game.getDate());
	}

	public void reinit() {
		refresh();
		((MapGUI) game.getMap()).reinit(mapPanel);
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

		initMapPanel();
		initInfoPanel();
		initMenu();
	}

	// 初始化右边栏
	private void initInfoPanel() {
		// 游戏信息面板开始
		JPanel infoPanel = new JPanel();
		infoPanel.setLocation(660, 0);
		infoPanel.setSize(224, 430);
		infoPanel.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(infoPanel);

		infoPanel.add(this.getDatePanel(), BorderLayout.NORTH);
		infoPanel.add(this.getPlayerInfoPanel(), BorderLayout.CENTER);

		lblFooter = new JLabel("提示");
		lblFooter.setBounds(10, 445, 500, 15);
		frame.getContentPane().add(lblFooter);
		// 玩家信息面板结束
	}

	// 初始化地图面板
	private void initMapPanel() {
		mapPanel = new JPanel();
		mapPanel.setBounds(0, 0, 650, 430);
		frame.getContentPane().add(mapPanel);
		mapPanel.setLayout(null);

		dicePanel = new DicePanel();
		dicePanel.setBounds(145, 145, 90, 90);
		setDiceListener();

		((MapGUI) game.getMap()).init(mapPanel, dicePanel);
	}

	// 初始化菜单
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();

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
		JMenuItem mntmCheckPlayerInfo = new JMenuItem("玩家信息");
		mntmCheckPlayerInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new PlayerInfoDialog(game.getPlayers(true))).setVisible(true);
			}
		});
		JMenuItem mntmCheckToll = new JMenuItem("指定地点信息");
		mntmCheckToll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int relativePos = game.io().inputInt("请输入相对位置:");
				Cell c = game.getMap().getCell(currentPlayer.getPosition());
				game.io().alert(c.getCellByRelativePos(relativePos).getBuilding().getDescription());
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
					AudioClip audioClip;
					File f = new File("./bgm/" + m + ".mid");
					try {
						audioClip = Applet.newAudioClip(f.toURI().toURL());
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

		// CHEAT菜单开始
		// JMenu mnCheat = new JMenu("CHEAT!");
		// JMenuItem injureMenuItem;
		// injureMenuItem = new JMenuItem("BREAK ME!");
		// injureMenuItem.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// currentPlayer.broke();
		// game.io().alert("玩家" + currentPlayer.getName() + "破产。");
		// }
		// });
		// mnCheat.add(injureMenuItem);
		// menuBar.add(mnCheat);
		// CHEAT菜单结束

		frame.setJMenuBar(menuBar);
	}

	public void changeImage(int step) {
		this.dicePanel.changeImage(step);
	}

	public void setDiceListener() {
		if (dicePanel.getMouseListeners().length == 0) {
			dicePanel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					dicePanel.removeMouseListener(this);
					if (currentPlayer.getInjuryCountdown() == 0) {
						dicePanel.rollDice(currentPlayer);
					} else {
						currentPlayer.injuryCountdown();
						currentPlayer.stop();
					}
				}
			});
		}
	}
}
