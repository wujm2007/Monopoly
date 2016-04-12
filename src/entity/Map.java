package entity;

import java.util.ArrayList;
import java.util.Collection;

public class Map {
	private Collection<Cell> cells = new ArrayList<Cell>();

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
		return cells.stream().filter(cell -> (cell.getPosition() == x)).findFirst().orElse(null);
	}

}