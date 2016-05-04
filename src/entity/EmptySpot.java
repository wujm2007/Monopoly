package entity;

public class EmptySpot extends Building {
	private static final String ICON = "空";
	private static final String TYPE = "空地";

	@Override
	public String getIcon() {
		return ICON;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void passby(Player p) {
		return;
	}

	@Override
	public void stay(Player p) {
		return;
	}

}