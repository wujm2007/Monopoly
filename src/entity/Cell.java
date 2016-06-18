package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entity.buildings.Building;
import views.RoadPanel;

@SuppressWarnings("serial")
public class Cell implements Serializable {
	private Map map;
	private int position;
	private Building building;
	private List<Player> players = new ArrayList<Player>();
	private boolean blocked;
	private RoadPanel roadPanel;

	public RoadPanel getRoadPanel() {
		return roadPanel;
	}

	public void setRoadPanel(RoadPanel roadPanel) {
		this.roadPanel = roadPanel;
		this.roadPanel.refresh();
	}

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
		if (map instanceof MapGUI) {
			((MapGUI) map).addPlayer(player);
		}
	}

	public Player getLastPlayer() {
		if (players.size() == 0)
			return null;
		return this.players.get(players.size() - 1);
	}

	public Player getAnotherPlayer(Player player) {
		Player result = null;
		for (Player p : players) {
			if (p != player)
				result = p;
		}
		return result;
	}

	public boolean hasPlayer(Player p) {
		return this.players.contains(p);
	}

	public void removePlayer(Player p) {
		if (map instanceof MapGUI) {
			((MapGUI) map).removePlayer(p, getPosition());
		}
		this.players.remove(p);
	}

	// if player is on this cell: if (original == true) return the original
	// icon, otherwise return the player's icon
	public String getIcon(boolean original) {
		if (original)
			return this.getBuilding().getOriginalIcon();
		if (this.players.isEmpty())
			return this.getBuilding().getIcon();
		else
			return this.getLastPlayer().getIcon();
	}

	public Cell getNextCell(boolean isClockwise) {
		int nPosition = isClockwise ? (this.getPosition() + 1) % map.getLength()
				: (this.getPosition() + (map.getLength() - 1)) % map.getLength();
		return map.getCell(nPosition);
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked() {
		this.blocked = true;
		if (map instanceof MapGUI) {
			((MapGUI) map).addRoadBlock(getPosition());
		}
	}

	public void removeBlock() {
		this.blocked = false;
		if (map instanceof MapGUI) {
			((MapGUI) map).removeRoadBlock(getPosition());
		}
	}

	public Cell getCellByRelativePos(int n) {
		boolean positive = n > 0;
		Cell rtn = this;
		for (int i = 0; i < Math.abs(n); i++)
			rtn = rtn.getNextCell(positive);
		return rtn;
	}

	public void stay(Player player) {
		getBuilding().stay(player);
		removeBlock();
	}

	public void passby(Player player) {
		getBuilding().passby(player);
	}

}
