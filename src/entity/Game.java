package entity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import view.GameFrame;

public class Game {

	private static final String STR_MAP_CMD = "5水星街,4,1,3,5金星街,2,5火星街,4,7,7,7,7,7,6,6,6,7,7,7,7,7,5木星街,1,5土星街,5,6天王星街,3,6海王星街,2,5蔡伦路,4,7,6,6,6,5,5,1,5,5";
	private static final String[] PLAYER_ICONS = { "■", "●", "★", "▲" };
	private static final String[] ESTATE_ICONS = { "□", "○", "☆", "△" };
	private Map map;
	private StockMarket stockMarket;
	private Date date;
	private int playerNum;
	private Collection<Player> players = new ArrayList<Player>();
	private IOHelper IO;
	private GameFrame gameFrame;

	public void setGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	public Date getDate() {
		return date;
	}

	public StockMarket getStockMarket() {
		return stockMarket;
	}

	public Map getMap() {
		return map;
	}

	public IOHelper io() {
		return IO;
	}

	public Game(int playerNum, boolean isGUI, IOHelper IO) {
		this.playerNum = playerNum;
		if (isGUI) {
			this.initMapGUI();
			this.initPlayersGUI();

		} else {
			this.initMap();
			this.initPlayers();
		}
		this.stockMarket = new StockMarket();
		this.date = new Date(this);
		this.IO = IO;
	}

	// broken == true means that broken players are included, otherwise not
	public List<Player> getPlayers(boolean broken) {
		return players.stream().filter(p -> (!((!broken) && (p.isBroke())))).collect(ArrayList::new, ArrayList::add,
				ArrayList::addAll);
	}

	// initializing the map with a given string
	private void initMap() {
		this.map = new Map(this);// a map of which the length is 74
		BufferedReader r = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(STR_MAP_CMD.getBytes())));
		String line = null;
		try {
			line = r.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] cells = line.split(",");
		int count = 0;
		for (String c : cells) {
			switch (c) {
			case "1":
				map.addCell(new Cell(map, count++, new Store()));
				break;
			case "2":
				map.addCell(new Cell(map, count++, new Bank()));
				break;
			case "3":
				map.addCell(new Cell(map, count++, new NewsSpot()));
				break;
			case "4":
				map.addCell(new Cell(map, count++, new LotterySpot()));
				break;
			case "5":
				map.addCell(new Cell(map, count++, new CardSpot()));
				break;
			case "6":
				map.addCell(new Cell(map, count++, new EmptySpot()));
				break;
			case "7":
				map.addCell(new Cell(map, count++, new TicketSpot()));
				break;
			default:
				int num = 0;
				try {
					num = Integer.parseInt(c.substring(0, 1));
				} catch (Exception e) {
					break;
				}
				for (int i = 0; i < num; i++) {
					map.addCell(new Cell(map, count++, new Estate(c.substring(1), i + 1, 1.0)));
				}
				break;
			}
		}
	}

	private void initMapGUI() {
		this.map = new MapGUI(this);
	}

	private void initPlayers() {
		for (int i = 0; i < playerNum; i++) {
			this.players.add(new Player(this.map, "Player" + (i + 1), PLAYER_ICONS[i], ESTATE_ICONS[i]));
		}
	}

	private void initPlayersGUI() {
		for (int i = 0; i < playerNum; i++) {
			this.players.add(new Player(this.map, "Player" + (i + 1), "./image/ava" + (i + 1) + ".jpg",
					"./image/owner" + (char) ('A' + i)));
		}
	}

	public void addDay() {
		getDate().addDay();
		if (this.gameFrame != null)
			this.gameFrame.refresh();
	}

	public void updateInfo() {
		if (this.gameFrame != null)
			this.gameFrame.refresh();
	}

	public void changeDiceImg(int step) {
		if (this.gameFrame != null)
			gameFrame.changeImage(step);
	}

	/*
	 * public void save() { JFileChooser jf = new JFileChooser();
	 * jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG |
	 * JFileChooser.DIRECTORIES_ONLY); jf.showDialog(null, null); File fi =
	 * jf.getSelectedFile(); String f = fi.getAbsolutePath() + "\\save.dat";
	 * System.out.println("save: " + f); try { FileOutputStream fs = new
	 * FileOutputStream(f); ObjectOutputStream os = new ObjectOutputStream(fs);
	 * os.writeObject(Player.getPlayer(0)); os.writeObject(Player.getPlayer(1));
	 * for (int i = 0; i < MAP_LENGTH; i++) { if (map[i] instanceof Estate)
	 * os.writeInt(((Estate) (map[i])).getLevel()); else os.writeInt(0); } for
	 * (int i = 0; i < MAP_LENGTH; i++) { if (map[i] instanceof Estate) { if
	 * (((Estate) (map[i])).getOwner() == Player.getPlayer(0)) os.writeInt(0);
	 * else if (((Estate) (map[i])).getOwner() == Player.getPlayer(1))
	 * os.writeInt(1); else os.writeInt(-1); } else os.writeInt(-1); }
	 * os.writeInt(TurnaroundCard.getNum(Player.getPlayer(0)));
	 * os.writeInt(RemoteCard.getNum(Player.getPlayer(0)));
	 * os.writeInt(BuyhouseCard.getNum(Player.getPlayer(0)));
	 * os.writeInt(InspecttaxCard.getNum(Player.getPlayer(0)));
	 * os.writeInt(TurnaroundCard.getNum(Player.getPlayer(1)));
	 * os.writeInt(RemoteCard.getNum(Player.getPlayer(1)));
	 * os.writeInt(BuyhouseCard.getNum(Player.getPlayer(1)));
	 * os.writeInt(InspecttaxCard.getNum(Player.getPlayer(1)));
	 * os.writeInt(Date.getYear()); os.writeInt(Date.getMonth());
	 * os.writeInt(Date.getDay()); os.writeInt(Date.getDayOfWeek2());
	 * os.writeInt(playerNum); os.close(); } catch (Exception ex) {
	 * ex.printStackTrace(); } }
	 * 
	 * public void load() { JFileChooser fd = new JFileChooser();
	 * fd.showOpenDialog(null); File f = fd.getSelectedFile(); if (f != null) {
	 * try { FileInputStream istream = new FileInputStream(f); ObjectInputStream
	 * pr = new ObjectInputStream(istream);
	 * 
	 * MainGUI.getMap()[Player.getPlayer(0).getPosition()].repaint(); if
	 * (MainGUI.getMap()[Player.getPlayer(0).getPosition()] instanceof Estate)
	 * MainGUI.getRoad()[Player.getPlayer(0).getPosition()].move(0);
	 * MainGUI.getMap()[Player.getPlayer(1).getPosition()].repaint(); if
	 * (MainGUI.getMap()[Player.getPlayer(1).getPosition()] instanceof Estate)
	 * MainGUI.getRoad()[Player.getPlayer(1).getPosition()].move(1);
	 * 
	 * Player.getPlayer(0).loadPlayer((Player) pr.readObject());
	 * Player.getPlayer(1).loadPlayer((Player) pr.readObject()); for (int i = 0;
	 * i < MAP_LENGTH; i++) { int n = pr.readInt(); if (map[i] instanceof
	 * Estate) { ((Estate) (map[i])).setLevel(n); } } for (int i = 0; i <
	 * MAP_LENGTH; i++) { int n = pr.readInt(); if (map[i] instanceof Estate) {
	 * if (n == 0) ((Estate) (map[i])).setOwner(Player.getPlayer(0)); if (n ==
	 * 1) ((Estate) (map[i])).setOwner(Player.getPlayer(1)); } }
	 * 
	 * TurnaroundCard.setNum(Player.getPlayer(0), pr.readInt());
	 * RemoteCard.setNum(Player.getPlayer(0), pr.readInt());
	 * BuyhouseCard.setNum(Player.getPlayer(0), pr.readInt());
	 * InspecttaxCard.setNum(Player.getPlayer(0), pr.readInt());
	 * TurnaroundCard.setNum(Player.getPlayer(1), pr.readInt());
	 * RemoteCard.setNum(Player.getPlayer(1), pr.readInt());
	 * BuyhouseCard.setNum(Player.getPlayer(1), pr.readInt());
	 * InspecttaxCard.setNum(Player.getPlayer(1), pr.readInt());
	 * Date.setDate(pr.readInt(), pr.readInt(), pr.readInt(), pr.readInt());
	 * playerNum = pr.readInt();
	 * 
	 * MainGUI.getRoad()[Player.getPlayer(0).getPosition()].stay(0);
	 * MainGUI.getRoad()[Player.getPlayer(1).getPosition()].stay(1);
	 * 
	 * lblDate.setText(String.format("%d/%d/%d", Date.getYear(),
	 * Date.getMonth(), Date.getDay())); lblDay.setText(Date.getDayOfWeek());
	 * playerAvatar.setImage(Player.getPlayer(playerNum).getImage());
	 * lblPlayerName.setText(String.format("%s",
	 * Player.getPlayer(getPlayerNum()).getName()));
	 * lblPlayerCash.setText(String.format("%d        ",
	 * Player.getPlayer(getPlayerNum()).getCash()));
	 * lblPlayerDeposit.setText(String.format("%d        ",
	 * Player.getPlayer(getPlayerNum()).getDeposit()));
	 * lblPlayerPoint.setText(String.format("%d        ",
	 * Player.getPlayer(getPlayerNum()).getPoint()));
	 * lblPlayerMoney.setText(String.format("%d        ",
	 * Player.getPlayer(getPlayerNum()).getMoney()));
	 * lblPlayerProperty.setText(String.format("%d        ",
	 * Player.getPlayer(getPlayerNum()).getProperty()));
	 * MainGUI.mntmTurnaroundCard .setText("转向卡 x " +
	 * TurnaroundCard.getNum(Player.getPlayer(MainGUI.getPlayerNum())));
	 * MainGUI.mntmRemoteCard.setText("遥控骰子 x " +
	 * RemoteCard.getNum(Player.getPlayer(MainGUI.getPlayerNum())));
	 * MainGUI.mntmBuyhouseCard .setText("购地卡 x " +
	 * BuyhouseCard.getNum(Player.getPlayer(MainGUI.getPlayerNum())));
	 * MainGUI.mntmInspecttaxCard .setText("查税卡 x " +
	 * InspecttaxCard.getNum(Player.getPlayer(MainGUI.getPlayerNum()))); int
	 * position = Player.getPlayer(MainGUI.getPlayerNum()).getPosition(); int
	 * nearestToll =
	 * Player.getPlayer(MainGUI.getPlayerNum()).getNearestTollPositon(position)
	 * - position; if
	 * (Player.getPlayer(MainGUI.getPlayerNum()).getNearestTollPositon(position)
	 * != -1) MainGUI.lblFooter.setText("您前方" + (nearestToll + MAP_LENGTH) %
	 * MAP_LENGTH + "步处过路费" + ((Estate) (MainGUI.getMap()[Player
	 * .getPlayer(MainGUI.getPlayerNum()).getNearestTollPositon(position)])).
	 * getPrice() / 2 + "元"); else MainGUI.lblFooter.setText("暂无提示");
	 * 
	 * istream.close(); } catch (Exception e) { } } }
	 */
}
