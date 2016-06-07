package entity.buildings;

import java.io.Serializable;

import entity.Player;

@SuppressWarnings("serial")
public abstract class Building implements Serializable {

	public abstract String getIcon();

	public abstract void passby(Player p);

	public abstract void stay(Player p);

	public Player getOwner() {
		return null;
	}

	public String getOriginalIcon() {
		return getIcon();
	}

	public abstract String getType();

	public String getDescription() {
		return "类型：" + getType();
	}

}