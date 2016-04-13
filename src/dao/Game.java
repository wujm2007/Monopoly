package dao;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import entity.*;

public class Game {

	private static final String STR_MAP = "0,0,0,0,0,3,0,0,0,0,0,0,0,2,0,0,0,0,0,4,7,7,7,7,7,6,6,6,7,7,7,7,7,0,0,0,0,0,1,0,0,0,0,0,5,0,0,0,0,0,0,3,0,0,0,0,0,0,2,0,0,0,0,0,4,7,6,6,6,5,5,1,5,5";
	private static final int PLAYER_NUM = 2;
	private Map map;
	private StockMarket stockMarket;

	public StockMarket getStockMarket() {
		return stockMarket;
	}

	public Map getMap() {
		return map;
	}

	public Game() {
		this.initMap();
		this.initPlayers();
		this.stockMarket = new StockMarket();
	}

	private Player[] players;

	public Player getPlayer(int n) {
		if ((0 <= n) && (PLAYER_NUM > n))
			return this.players[n];
		return null;
	}

	public Collection<Player> getPlayers() {
		Collection<Player> rtn = new ArrayList<Player>();
		for (Player p : players) {
			rtn.add(p);
		}
		return rtn;
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
			case "0":
				map.addCell(new Cell(map, count, new Estate(1)));
				break;
			case "1":
				map.addCell(new Cell(map, count, new Store()));
				break;
			case "2":
				map.addCell(new Cell(map, count, new Bank()));
				break;
			case "3":
				map.addCell(new Cell(map, count, new NewsSpot()));
				break;
			case "4":
				map.addCell(new Cell(map, count, new LotterySpot()));
				break;
			case "5":
				map.addCell(new Cell(map, count, new CardSpot()));
				break;
			case "6":
				map.addCell(new Cell(map, count, new EmptySpot()));
				break;
			case "7":
				map.addCell(new Cell(map, count, new TicketSpot()));
				break;
			}
			count++;
		}
	}

	private void initPlayers() {
		this.players = new Player[PLAYER_NUM];
		this.players[0] = new Player(this.map, "Player1", "□", "○");
		this.players[1] = new Player(this.map, "Player2", "■", "●");
	}

	// a primitive way to print the map
	public void printMap() {
		int length = map.getLength();
		for (int i = 0; i < 20; i++)
			System.out.print(map.getCell(i).getIcon());
		for (int i = 20; i < 20 + (length - 40) / 2; i++) {
			int j = length - 1 + 20 - i;
			System.out.printf("\n%s                                     %s", map.getCell(i).getIcon(),
					map.getCell(j).getIcon());
		}
		System.out.printf("\n");
		for (int i = length - 1 - (length - 40) / 2; i > length - 1 - (length - 40) / 2 - 20; i--) {
			System.out.print(map.getCell(i).getIcon());
		}
		System.out.printf("\n");
	}

	public void printPlayers() {
		for (Player p : players) {
			System.out.println(p.getName());
		}
	}

}
