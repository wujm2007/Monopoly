package entity;

public class EmptySpot extends Building {
	private static final String icon = "空";
	private static final String type = "空地";

	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public void passby(Player p) {
		return;
	}

	@Override
	public void stay(Player p) {
		return;
	}

	@Override
	public String getType() {
		return type;
	}

}