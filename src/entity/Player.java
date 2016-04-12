package entity;

import java.util.ArrayList;
import java.util.Collection;

public class Player {
	private static final int ORIGINAL_CASH = 5000;
	private static final int ORIGINAL_TICKET = 100;
	private static final int ORIGINAL_DEPOSIT = 5000;

	private String name, icon, estateIcon;
	private Collection<Card> cards = new ArrayList<Card>();
	private int cash, tickets;
	private BankAccount account;
	private Map map;
	private boolean isBroke;

	public class BankAccount {
		private Player owner;
		private int deposit;

		public BankAccount(Player owner) {
			this.owner = owner;
			this.deposit = ORIGINAL_DEPOSIT;
		}

		public Player getOwner() {
			return owner;
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
		this.account = new BankAccount(this);
		this.isBroke = false;
		this.map.getCell(0).addPlayer(this);
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

	public Collection<Card> getCards() {
		return cards;
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

	private void broke() {
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
			System.out.println(e.getPrice());
			estatesValue += e.getPrice();
		}
		return getCash() + getDeposit() + estatesValue;
	}

	public Map getMap() {
		return map;
	}

}