package entity;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.Timer;

import entity.buildings.Estate;
import entity.buildings.Hospital;
import entity.cards.Card;
import entity.cards.ControlDice;
import entity.cards.RoadBlock;
import entity.cards.TaxCard;
import entity.cards.TurnaroundCard;

@SuppressWarnings("serial")
public class Player implements Serializable {
	private static final int ORIGINAL_CASH = 5000, ORIGINAL_TICKET = 100, ORIGINAL_DEPOSIT = 10000;

	private transient Timer timer;

	private class BankAccount implements Serializable {
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
	private int cash, tickets, steps = 0;
	private BankAccount account;
	private Map map;
	private boolean isBroke, isClockwise;
	private int injuryCountdown = 0;

	public boolean isClockwise() {
		return isClockwise;
	}

	public void setClockwise(boolean isClockwise) {
		this.isClockwise = isClockwise;
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
		this.isClockwise = true;
		this.cards = new ArrayList<Card>();
		this.addCard(RoadBlock.getInstance());
		this.addCard(ControlDice.getInstance());
		this.addCard(TurnaroundCard.getInstance());
		this.addCard(TaxCard.getInstance());
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void goWithTimer() {
		if (steps == 0) {
			steps = (int) (Math.random() * 6) + 1;
		}
		this.getGame().changeDiceImg(steps);
		timer = new Timer(500, new ActionListener() {
			int stepsLeft = steps;

			@Override
			public void actionPerformed(ActionEvent e) {
				nextStep(--stepsLeft, isClockwise);
			}
		});
		timer.start();
	}

	public void go() {
		if (steps == 0) {
			steps = (int) (Math.random() * 6) + 1;
		}
		this.getGame().changeDiceImg(steps);
		for (int i = steps; i != 0;) {
			nextStep(--i, isClockwise);
		}
	}

	public void nextStep(int stepsLeft, boolean isClockWise) {
		if (steps == 0) {
			stop();
			return;
		}
		Cell cell = map.getCell(getPosition());
		if (cell.isBlocked()) {
			cell.stay(this);
			stop();
			return;
		}
		cell.removePlayer(this);
		cell.getNextCell(isClockwise).addPlayer(this);
		if (stepsLeft != 0)
			cell.getNextCell(isClockwise).passby(this);
		else {
			cell.getNextCell(isClockwise).stay(this);
			stop();
		}
	}

	public void stop() {
		steps = 0;
		if (timer != null)
			timer.stop();
		if (this.getGame().getGameFrame() != null) {
			this.getGame().getGameFrame().setCurrentPlayer(this.getNextPlayer());
			if (this.getGame().getPlayers(false).indexOf(this) == this.getGame().getPlayers(false).size() - 1)
				this.getGame().getDate().addDay();
			this.getGame().getGameFrame().setDiceListener();
		}
	}

	public int getPosition() {
		return map.getCells().stream().filter(c -> (c.hasPlayer(this))).findFirst().orElse(null).getPosition();
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}

	public int getDeposit() {
		return this.account.getDeposit();
	}

	public void setDeposit(int m) {
		this.account.setDeposit(m);
		this.getGame().updateInfo();
	}

	public void addDeposit(int money) {
		if (money > 0)
			setDeposit(getDeposit() + money);
	}

	public boolean costDeposit(int money) {
		if ((money > 0) && (this.account.deposit >= money)) {
			setDeposit(getDeposit() - money);
			return true;
		} else {
			return false;
		}
	}

	public HashMap<Card, Integer> getCards() {
		HashMap<Card, Integer> result = new HashMap<Card, Integer>();
		this.cards.forEach(c -> {
			if (result.containsKey(c))
				result.put(c, result.get(c) + 1);
			else
				result.put(c, 1);
		});
		return result;
	}

	public void addCard(Card c) {
		this.cards.add(c);
		this.getGame().updateInfo();
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
		this.getGame().updateInfo();
	}

	public void addCash(int money) {
		if (money > 0)
			setCash(getCash() + money);
	}

	public boolean costCash(int money) {
		if ((money > 0) && (this.cash >= money)) {
			setCash(getCash() - money);
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
		this.getGame().updateInfo();
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
		this.getGame().io().alert(this.getName() + "破产。");
		if (this.getPeers(false).size() == 1) {
			this.getGame().io().alert("游戏结束，玩家" + this.getPeers(false).get(0) + "胜利！");
			System.exit(0);
		}
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
		this.isClockwise = !isClockwise;
	}

	public Game getGame() {
		return getMap().getGame();
	}

	public List<Player> getPeers(boolean broken) {
		return getMap().getGame().getPlayers(broken);
	}

	public Image getAvatar() {
		Image image = (new ImageIcon(icon)).getImage();
		return image;
	}

	public Image getEstateImage(int level) {
		Image image = (new ImageIcon(estateIcon + level + ".png")).getImage();
		return image;
	}

	public Player getNextPlayer() {
		List<Player> peers = this.getPeers(false);
		return peers.get((peers.indexOf(this) + 1) % peers.size());
	}

	public void removeCard(Card card) {
		cards.remove(card);
	}

	public void injure() {
		injuryCountdown = 2;
		getMap().getCell(getPosition()).removePlayer(this);
		getMap().getCell(Hospital.getPostition()).addPlayer(this);
		setClockwise(!isClockwise);
		stop();
	}

	public int getInjuryCountdown() {
		return injuryCountdown;
	}

	public void injuryCountdown() {
		this.getGame().io().alert(this.getName() + "还有" + (injuryCountdown--) + "天出院");
	}
}