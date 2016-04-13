package entity;

import java.util.ArrayList;
import java.util.Collection;

import dao.Game;

public class Player {
	private static final int ORIGINAL_CASH = 5000, ORIGINAL_TICKET = 100, ORIGINAL_DEPOSIT = 10000;

	private String name, icon, estateIcon;
	private ArrayList<Card> cards;
	private int cash, tickets;
	private BankAccount account;
	private Map map;
	private boolean isBroke;
	private int steps = 0;
	private boolean clockwise;

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void go() {
		if (steps == 0) {
			steps = (int) (Math.random() * 6) + 1;
		}
		for (int i = 0; i < steps; i++) {
			Cell cell = map.getCell(getPosition());
			if (cell.isBlocked()) {
				cell.stay(this);
				steps = 0;
				return;
			}
			cell.removePlayer(this);
			cell.getNextCell().addPlayer(this);
			if (i != steps - 1)
				cell.getNextCell().passby(this);
			else
				cell.getNextCell().stay(this);
		}
		steps = 0;
	}

	public int getPosition() {
		return map.getCells().stream().filter(c -> (c.hasPlayer(this))).findFirst().orElse(null).getPosition();
	}

	private class BankAccount {
		private int deposit;

		public BankAccount() {
			this.deposit = ORIGINAL_DEPOSIT;
		}

		public int getDeposit() {
			return deposit;
		}

		public void setDeposit(int deposit) {
			this.deposit = deposit;
		}

	}

	public Player(Map map, String name, String icon, String estateIcon) {
		this.map = map;
		this.name = name;
		this.icon = icon;
		this.estateIcon = estateIcon;
		this.cash = ORIGINAL_CASH;
		this.tickets = ORIGINAL_TICKET;
		this.account = new BankAccount();
		this.isBroke = false;
		this.map.getCell(0).addPlayer(this);
		this.clockwise = true;
		this.cards = new ArrayList<Card>();
		this.addCard(new RoadBlock());
		this.addCard(new ControlDice());
	}

	public String getName() {
		return name;
	}

	public int getDeposit() {
		return this.account.getDeposit();
	}

	public void setDeposit(int m) {
		this.account.setDeposit(m);
	}

	public ArrayList<Card> getCards() {
		return this.cards;
	}

	public void addCard(Card c) {
		getCards().add(c);
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getTickets() {
		return tickets;
	}

	public void setTickets(int tickets) {
		this.tickets = tickets;
	}

	public void cost(int cost) {
		if (getCash() >= cost) {
			setCash(getCash() - cost);
		} else {
			cost -= getCash();
			if (getDeposit() >= cost) {
				setDeposit(getDeposit() - cost);
			} else {
				cost -= getDeposit();
				while ((cost > 0) && (!getEstates().isEmpty())) {
					Estate e = getEstates().iterator().next();
					cost -= e.getPrice();
					e.setOwner(null);
				}
				if (cost > 0) {
					this.broke();
				} else {
					setCash(getCash() - cost);
				}
			}
		}
	}

	public void broke() {
		this.isBroke = true;
	}

	public boolean isBroke() {
		return isBroke;
	}

	public String getIcon() {
		return icon;
	}

	public String getEstateIcon() {
		return estateIcon;
	}

	public Collection<Estate> getEstates() {
		Collection<Estate> c = new ArrayList<Estate>();
		getMap().getCells().stream().filter(cell -> (cell.getBuilding().getOwner() == this))
				.map(cell -> cell.getBuilding()).forEachOrdered(e -> {
					c.add((Estate) e);
				});
		return c;
	}

	public int getAsset() {
		int estatesValue = 0;
		for (Estate e : getEstates()) {
			estatesValue += e.getPrice();
		}
		return getCash() + getDeposit() + estatesValue;
	}

	public Map getMap() {
		return map;
	}

	public void turnBack() {
		this.clockwise = !clockwise;
	}

	public Game getGame() {
		return getMap().getGame();
	}

	public boolean isClockWise() {
		return this.clockwise;
	}
}