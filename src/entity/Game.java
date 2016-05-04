package entity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

public class Game {

	private static final String STR_MAP = "4,4,4,4,4,4,4,5水星街,3,7金星街,2,5火星街,4,7,7,7,7,7,6,6,6,7,7,7,7,7,5木星街,1,5土星街,5,6天王星街,3,6海王星街,2,5蔡伦路,4,7,6,6,6,5,5,1,5,5";
	private static final String[] PLAYER_ICONS = { "■", "●", "★", "▲" };
	private static final String[] ESTATE_ICONS = { "□", "○", "☆", "△" };
	private Map map;
	private StockMarket stockMarket;
	private Date date;
	private int playerNum;
	private Collection<Player> players = new ArrayList<Player>();

	public Date getDate() {
		return date;
	}

	public StockMarket getStockMarket() {
		return stockMarket;
	}

	public Map getMap() {
		return map;
	}

	public Game(int playerNum) {
		this.playerNum = playerNum;
		this.initMap();
		this.initPlayers();
		this.stockMarket = new StockMarket();
		this.date = new Date(this);
	}

	// broken == true means that broken players are included, otherwise not
	public Collection<Player> getPlayers(boolean broken) {
		return players.stream().filter(p -> (!((!broken) && (p.isBroke())))).collect(ArrayList::new, ArrayList::add,
				ArrayList::addAll);
	}

	// initializing the map with a given string
	private void initMap() {
		this.map = new Map(this);// a map of which the length is 74
		BufferedReader r = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(STR_MAP.getBytes())));
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

	private void initPlayers() {
		for (int i = 0; i < playerNum; i++) {
			this.players.add(new Player(this.map, "Player" + (i + 1), PLAYER_ICONS[i], ESTATE_ICONS[i]));
		}
	}

	public void addDay() {
		getDate().addDay();
	}

}
