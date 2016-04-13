package entity;

public abstract class Building {
	public abstract String getIcon();

	protected abstract void passby(Player p);

	protected abstract void stay(Player p);

	public abstract Player getOwner();
}
