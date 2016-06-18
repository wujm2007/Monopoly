package entity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entity.buildings.Bank;
import entity.buildings.CardSpot;
import entity.buildings.EmptySpot;
import entity.buildings.Estate;
import entity.buildings.Hospital;
import entity.buildings.LotterySpot;
import entity.buildings.NewsSpot;
import entity.buildings.Store;
import entity.buildings.TicketSpot;

@SuppressWarnings("serial")
public class Map implements Serializable {

	private transient Game game;

	private List<Cell> cells;

	public Map(Game game, String strMap) {
		this.game = game;
		this.cells = new ArrayList<Cell>();
		this.initMap(strMap);
	}

	// initializing the map with a given string
	public void initMap(String strMap) {
		BufferedReader r = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(strMap.getBytes())));
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
				addCell(new Cell(this, count++, new Store()));
				break;
			case "2":
				addCell(new Cell(this, count++, new Bank()));
				break;
			case "3":
				addCell(new Cell(this, count++, new NewsSpot()));
				break;
			case "4":
				addCell(new Cell(this, count++, new LotterySpot()));
				break;
			case "5":
				addCell(new Cell(this, count++, new CardSpot()));
				break;
			case "6":
				addCell(new Cell(this, count++, new EmptySpot()));
				break;
			case "7":
				addCell(new Cell(this, count++, new TicketSpot()));
				break;
			case "8":
				Hospital.setPostition(count);
				addCell(new Cell(this, count++, Hospital.getInstance()));
				break;
			default:
				int num = 0;
				try {
					num = Integer.parseInt(c.substring(0, 1));
				} catch (Exception e) {
					break;
				}
				for (int i = 0; i < num; i++)
					addCell(new Cell(this, count++, new Estate(c.substring(1), i + 1, 1.0)));
				break;
			}
		}
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getLength() {
		return cells.size();
	}

	public void addCell(Cell c) {
		cells.add(c);
	}

	public List<Cell> getCells() {
		return cells;
	}

	public Cell getCell(int x) {
		if ((x < 0) || (x >= cells.size()))
			return null;
		else
			return cells.get(x);
	}

}