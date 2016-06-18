package entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import views.GameFrame;

@SuppressWarnings("serial")
public class Game implements Serializable {

	private static final String STR_MAP_CMD = "5水星街,4,1,3,5金星街,2,5火星街,4,7,7,7,7,7,6,6,6,7,7,7,7,7,5木星街,1,5土星街,5,6天王星街,3,6海王星街,2,5蔡伦路,4,7,6,6,6,5,5,1,5,5";

	// The map of GUI version is still hard-coded.
	private static final String STR_MAP_GUI = "5水星街,1,5金星街,3,5火星街,2,5木星街,8,5土星街,5,7,7,7,7,7,7";

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
			this.map = new MapGUI(this, STR_MAP_GUI);
			this.initPlayersGUI();
		} else {
			this.map = new Map(this, STR_MAP_CMD);
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
		jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
		jf.showDialog(null, "保存");
		jf.setDialogTitle("保存进度");
		File fi = jf.getSelectedFile();
		try {
			String filePath = fi.getAbsolutePath();
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
				this.gameFrame.reinit();
				is.close();
				this.io().alert("读取成功");
			} catch (Exception e) {
				e.printStackTrace();
				this.io().alert("读取失败");
			}
		}
	}
}
