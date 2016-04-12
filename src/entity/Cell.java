package entity;

import java.util.ArrayList;
import java.util.Collection;

public class Cell {
	private int position;
	private Building building;
	private Collection<Player> players = new ArrayList<Player>();

	public Cell(int p, Building b) {
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

	public void removePlayer(Player p) {
		this.players.remove(p);
	}

	public String getIcon() {
		if (this.getPlayers().isEmpty())
			return this.getBuilding().getIcon();
		else
			return this.getPlayers().iterator().next().getIcon();
	}

}
