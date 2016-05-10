package entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import biz_cmdLine.IOHelper;

public class Player {
	private static final int ORIGINAL_CASH = 5000, ORIGINAL_TICKET = 1000, ORIGINAL_DEPOSIT = 10000;

	private class BankAccount {
		private int deposit;

		public BankAccount() {
			this.deposit = ORIGINAL_DEPOSIT;
		}

		private int getDeposit() {
			return deposit;
		}

		private void setDeposit(int deposit) {
			this.deposit = deposit;
		}

	}

	private String name, icon, estateIcon;
	private List<Card> cards;
	private int cash, tickets;
	private BankAccount account;
	private Map map;
	private boolean isBroke;
	private int steps = 0;
	private boolean clockwise;

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
		this.addCard(new RoadBlock());
		this.addCard(new RoadBlock());
		this.addCard(new ControlDice());
		this.addCard(new ControlDice());
		this.addCard(new ControlDice());
		this.addCard(new ControlDice());
		this.addCard(new ControlDice());
		this.addCard(new AverageCashCard());
		this.addCard(new BlackCard());
		this.addCard(new RedCard());
		this.addCard(new BuyEstateCard());
		this.addCard(new LotteryCard());
	}

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
			else {
				cell.getNextCell().stay(this);
			}
		}
		steps = 0;
	}

	public int getPosition() {
		return map.getCells().stream().filter(c -> (c.hasPlayer(this))).findFirst().orElse(null).getPosition();
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

	public void addDeposit(int money) {
		if (money > 0)
			this.account.deposit += money;
	}

	public boolean costDeposit(int money) {
		if ((money > 0) && (this.account.deposit >= money)) {
			this.account.deposit -= money;
			return true;
		} else {
			return false;
		}
	}

	public List<Card> getCards() {
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

	public void addCash(int money) {
		if (money > 0)
			this.cash += money;
	}

	public boolean costCash(int money) {
		if ((money > 0) && (this.cash >= money)) {
			this.cash -= money;
			return true;
		} else {
			return false;
		}
	}

	public int getTickets() {
		return tickets;
	}

	public void setTickets(int tickets) {
		this.tickets = tickets;
	}

	public int cost(int cost) {
		int totalCost = cost;
		if (!costCash(cost)) {
			cost -= this.getCash();
			this.costCash(this.getCash());
			if (!this.costDeposit(cost)) {
				cost -= getDeposit();
				this.costDeposit(this.getDeposit());
				while ((cost > 0) && (!getEstates().isEmpty())) {
					Estate e = getEstates().iterator().next();
					cost -= e.getPrice();
					e.setOwner(null);
				}
				// cost may be negative if the price of the house sold is
				// more than the cost
				if (cost > 0) {
					this.broke();
					return totalCost - cost;
				} else {
					this.addCash(0 - cost);
				}
			}
		}
		return totalCost;
	}

	public void broke() {
		this.getEstates().stream().forEach(e -> {
			e.setOwner(null);
		});
		this.getGame().getStockMarket().writeoff(this);
		this.isBroke = true;
		IOHelper.alert(this.getName() + "破产。");
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
		return getMap().getCells().stream()
				.filter(cell -> (cell.getBuilding() instanceof Estate) && (cell.getBuilding().getOwner() == this))
				.map(cell -> (Estate) cell.getBuilding()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}

	public int getAsset() {
		int estatesValue = 0;
		for (Estate e : getEstates()) {
			estatesValue += e.getPrice();
		}
		return this.cash + getDeposit() + estatesValue;
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

	public Collection<Player> getPeers(boolean broken) {
		return getMap().getGame().getPlayers(broken);
	}

	public boolean isClockWise() {
		return this.clockwise;
	}
}