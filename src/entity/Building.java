package entity;

public abstract class Building {
	public abstract String getIcon();

	protected abstract void passby(Player p);

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
