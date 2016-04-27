package entity;

public class TicketSpot extends Building {
	private static final String icon = "券";
	private static final String type = "点券点";

	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void passby(Player p) {
		return;
	}

	@Override
	public void stay(Player p) {
		int rand = (((int) (Math.random() * 10)) + 1) * 10;
		p.setTickets(p.getTickets() + rand);
	}

}
