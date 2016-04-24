package entity;

public abstract class Building {

	public abstract String getIcon();

	// the action when a player passes by
	protected abstract void passby(Player p);

	// the action when a player stays
	protected abstract void stay(Player p);

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
