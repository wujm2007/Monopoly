package entity;

import java.util.ArrayList;
import java.util.Collection;

import dao.Game;

public class Map {

	private Game game;
	private ArrayList<Cell> cells = new ArrayList<Cell>();

	public Map(Game game) {
		this.game = game;
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

	public Collection<Cell> getCells() {
		return cells;
	}

	public Cell getCell(int x) {
		if ((x < 0) || (x >= cells.size()))
			return null;
		else
			return cells.get(x);
	}

}