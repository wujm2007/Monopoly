package entity;

import java.util.ArrayList;
import java.util.Collection;

import dao.IOHelper;

public class Cell {
	private Map map;
	private int position;
	private Building building;
	private Collection<Player> players = new ArrayList<Player>();
	private boolean blocked;

	public Cell(Map m, int p, Building b) {
		this.map = m;
		this.position = p;
		this.building = b;
	}

	public int getPosition() {
		return position;
	}

	public Building getBuilding() {
		return building;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public Collection<Player> getPlayers() {
		return players;
	}

	public boolean hasPlayer(Player p) {
		return getPlayers().contains(p);
	}

	public void removePlayer(Player p) {
		this.players.remove(p);
	}

	// if player is the this cell: if (original == true) return the original
	// icon, otherwise return the player's icon
	public String getIcon(boolean original) {
		if (original)
			return this.getBuilding().getOriginalIcon();

		if (this.getPlayers().isEmpty())
			return this.getBuilding().getIcon();
		else
			return this.getPlayers().iterator().next().getIcon();
	}

	public Cell getNextCell() {
		int nPosition = (this.getPosition() + 1) % map.getLength();
		return map.getCell(nPosition);
	}

	public Cell getPreviousCell() {
		int pPosition = (this.getPosition() + (map.getLength() - 1)) % map.getLength();
		return map.getCell(pPosition);
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked() {
		this.blocked = true;
	}

	public Cell getCellByRelativePos(int n) {
		boolean positive = n > 0;
		Cell rtn = this;
		for (int i = 0; i < Math.abs(n); i++) {
			if (positive)
				rtn = rtn.getNextCell();
			else
				rtn = rtn.getPreviousCell();
		}
		return rtn;
	}

	public void stay(Player player) {
		getBuilding().stay(player);
		this.blocked = false;
	}

	public void passby(Player player) {
		getBuilding().passby(player);
	}

	public void printInfo() {
		IOHelper.showInfo(getBuilding().getDescription());
	}

}
