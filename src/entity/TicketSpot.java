package entity;

public class TicketSpot extends Building {

	@Override
	public String getIcon() {
		return "券";
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

	@Override
	public Player getOwner() {
		return null;
	}

}
