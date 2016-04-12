package entity;

public class EmptySpot extends Building {

	@Override
	public String getIcon() {
		return "空";
	}

	@Override
	public void passby(Player p) {
	}

	@Override
	public void stay(Player p) {
	}

	@Override
	public Player getOwner() {
		return null;
	}

}