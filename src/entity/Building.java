package entity;

public abstract class Building {
	public abstract String getIcon();

	public abstract void passby(Player p);

	public abstract void stay(Player p);

	public abstract Player getOwner();
}
