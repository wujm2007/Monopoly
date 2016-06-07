package entity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import entity.buildings.Bank;
import entity.buildings.CardSpot;
import entity.buildings.EmptySpot;
import entity.buildings.Estate;
import entity.buildings.LotterySpot;
import entity.buildings.NewsSpot;
import entity.buildings.Store;
import entity.buildings.TicketSpot;
import views.GameFrame;

@SuppressWarnings("serial")
public class Game implements Serializable {

	private static final String STR_MAP_CMD = "5水星街,4,1,3,5金星街,2,5火星街,4,7,7,7,7,7,6,6,6,7,7,7,7,7,5木星街,1,5土星街,5,6天王星街,3,6海王星街,2,5蔡伦路,4,7,6,6,6,5,5,1,5,5";
	private static final String[] PLAYER_ICONS = { "■", "●", "★", "▲" };
	private static final String[] ESTATE_ICONS = { "□", "○", "☆", "△" };
	private Map map;
	private StockMarket stockMarket;
	private Date date;
	private int playerNum;
	private List<Player> players = new ArrayList<Player>();
	private IOHelper IO;
	private transient GameFrame gameFrame;

	public void setGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	public GameFrame getGameFrame() {
		return this.gameFrame;
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
		this.stockMarket = new StockMarket(this);
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
				for (int i = 0; i < num; i++)
					map.addCell(new Cell(map, count++, new Estate(c.substring(1), i + 1, 1.0)));
				break;
			}
		}
	}

	private void initMapGUI() {
		this.map = new MapGUI(this);
	}

	private void initPlayers() {
		for (int i = 0; i < playerNum; i++)
			this.players.add(new Player(this.map, "Player" + (i + 1), PLAYER_ICONS[i], ESTATE_ICONS[i]));
	}

	private void initPlayersGUI() {
		for (int i = 0; i < playerNum; i++)
			this.players.add(new Player(this.map, "Player" + (i + 1), "./image/ava" + (i + 1) + ".jpg",
					"./image/owner" + (char) ('A' + i)));
	}

	public void addDay() {
		getDate().addDay();
	}

	public void updateInfo() {
		if (this.gameFrame != null)
			this.gameFrame.refresh();
	}

	public void changeDiceImg(int step) {
		if (this.gameFrame != null)
			gameFrame.changeImage(step);
	}

	public void save() {
		JFileChooser jf = new JFileChooser();
		jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);
		jf.showDialog(null, null);
		File fi = jf.getSelectedFile();
		String filePath = fi.getAbsolutePath() + "/save.dat";
		System.out.println("save: " + filePath);
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filePath));
			os.writeObject(this.getPlayers(true));
			os.writeObject(this.getStockMarket());
			os.writeObject(this.getMap());
			os.writeObject(this.getDate());
			os.writeObject(this.gameFrame.getCurrentPlayer());
			os.close();
			this.io().alert("保存成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			this.io().alert("保存失败");
		}
	}

	@SuppressWarnings("unchecked")
	public void load() {
		JFileChooser fd = new JFileChooser();
		fd.showOpenDialog(null);
		File f = fd.getSelectedFile();
		if (f != null) {
			try {
				ObjectInputStream is = new ObjectInputStream(new FileInputStream(f));
				this.players = (List<Player>) is.readObject();
				this.stockMarket = (StockMarket) is.readObject();
				this.stockMarket.setGame(this);
				this.map = (Map) is.readObject();
				this.map.setGame(this);
				this.date = (Date) is.readObject();
				this.date.setGame(this);
				this.gameFrame.setCurrentPlayer((Player) is.readObject());
				this.gameFrame.init();
				is.close();
				this.io().alert("读取成功");
			} catch (Exception e) {
				e.printStackTrace();
				this.io().alert("读取失败");
			}
		}
	}
}
