package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Map implements Serializable {

	private transient Game game;

	private List<Cell> cells;

	public Map(Game game) {
		this.game = game;
		this.cells = new ArrayList<Cell>();
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