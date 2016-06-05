package entity;

import java.util.ArrayList;
import java.util.List;

public class Map {

	private Game game;
	private List<Cell> cells;

	public Map(Game game) {
		this.game = game;
		this.cells = new ArrayList<Cell>();
	}

	public Game getGame() {
		return game;
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