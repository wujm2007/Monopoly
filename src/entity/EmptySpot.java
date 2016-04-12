package entity;

public class EmptySpot extends Building {

	@Override
	public String getIcon() {
		return "空";
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
	public Player getOwner() {
		return null;
	}

}