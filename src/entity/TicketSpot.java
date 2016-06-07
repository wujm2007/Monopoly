package entity;

@SuppressWarnings("serial")
public class TicketSpot extends Building {
	private static final String ICON = "券";
	private static final String TYPE = "点券点";

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
		int rand = (((int) (Math.random() * 10)) + 1) * 10;
		p.setTickets(p.getTickets() + rand);
		p.getGame().io().alert("您获得" + rand + "点券.");
	}

}
