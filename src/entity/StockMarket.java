package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@SuppressWarnings("serial")
public class StockMarket implements Serializable {

	public static class Stock implements Serializable {
		private static int instanceCount = 1;
		private String name;
		private double price;
		private int index;
		private double fluctuation;

		public int getIndex() {
			return index;
		}

		public Stock(String name, double price) {
			this.name = name;
			this.price = price;
			this.index = instanceCount++;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public String getName() {
			return name;
		}

		public double getFluctuation() {
			return fluctuation;
		}

		public void setFluctuation(double fluctuation) {
			this.fluctuation = fluctuation;
		}

	}

	public enum StockTradeOpType {
		BUY, SELL, QUIT
	}

	public static class StockTradeOperation {
		private StockTradeOpType op;
		private int index, num;

		public StockTradeOperation(StockTradeOpType op, int index, int num) {
			this.op = op;
			this.index = index;
			this.num = num;
		}

		public StockTradeOpType getOpType() {
			return op;
		}

		public int getIndex() {
			return index;
		}

		public int getNum() {
			return num;
		}

	}

	private List<Stock> stocks = new ArrayList<Stock>();

	private Map<Player, HashMap<Stock, Integer>> acoounts = new HashMap<Player, HashMap<Stock, Integer>>();
	private Map<Player, HashMap<Stock, Double>> costs = new HashMap<Player, HashMap<Stock, Double>>();
	private Map<Stock, Double> rand = new HashMap<Stock, Double>();

	private Game game;

	public StockMarket(Game g) {
		this.game = g;
		// add 10 stocks and randomly generate their prices
		stocks.add(new Stock("股票1", 10));
		stocks.add(new Stock("股票2", 10));
		stocks.add(new Stock("股票3", 10));
		stocks.add(new Stock("股票4", 10));
		stocks.add(new Stock("股票5", 10));
		stocks.add(new Stock("股票6", 10));
		stocks.add(new Stock("股票7", 10));
		stocks.add(new Stock("股票8", 10));
		stocks.add(new Stock("股票9", 10));
		stocks.add(new Stock("股票0", 10));
		for (int i = 0; i < 100; i++)
			refresh();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public Vector<String> generateStackTableRow(Stock s) {
		Vector<String> result = new Vector<String>();
		result.addElement(s.getName());
		result.addElement(String.format("%.2f", s.getPrice()));
		result.addElement(String.format("%.2f", 100 * (s.getFluctuation() - 1)) + "%");
		game.getPlayers(true).forEach(p -> {
			if ((acoounts.get(p) != null) && (acoounts.get(p).get(s) != null)) {
				result.add(new Integer(acoounts.get(p).get(s)).toString());
				result.add(String.format("%.2f", costs.get(p).get(s)));
			} else {
				result.add("0");
				result.add("0");
			}
		});
		return result;
	}

	public Stock getStock(int n) {
		if ((n > 0) && (n <= stocks.size()))
			return stocks.get(n - 1);
		else
			return null;
	}

	public Map<Stock, Integer> getAccount(Player p) {
		if (!this.acoounts.containsKey(p))
			this.acoounts.put(p, new HashMap<Stock, Integer>());
		return acoounts.get(p);
	}

	public Map<Stock, Double> getCosts(Player p) {
		if (!this.costs.containsKey(p))
			this.costs.put(p, new HashMap<Stock, Double>());
		return costs.get(p);
	}

	// write off the stock account of a certain player (after he/she is broke)
	public void writeoff(Player p) {
		acoounts.remove(p);
	}

	public Integer getPlayerStock(Player p, int i) {
		if (!getAccount(p).containsKey(getStock(i)))
			getAccount(p).put(getStock(i), 0);
		return getAccount(p).get(getStock(i));
	}

	public Double getPlayerCost(Player p, int i) {
		if (!getCosts(p).containsKey(getStock(i)))
			getCosts(p).put(getStock(i), 0D);
		return getCosts(p).get(getStock(i));
	}

	public void addStock(Player p, int i, int n) {
		Map<Stock, Integer> a = getAccount(p);
		Map<Stock, Double> c = getCosts(p);
		Stock s = getStock(i);
		if (a.containsKey(s)) {
			a.put(s, getPlayerStock(p, i) + n);
			c.put(s, (getPlayerStock(p, i) * this.getPlayerCost(p, i) + n * s.getPrice()) / (getPlayerStock(p, i) + n));
		} else {
			a.put(getStock(i), n);
			c.put(s, s.getPrice());
		}
	}

	public void reduceStock(Player p, int i, int n) {
		Map<Stock, Integer> a = getAccount(p);
		a.put(getStock(i), getPlayerStock(p, i) - n);
	}

	public void refresh() {
		this.stocks.forEach(s -> {
			if (rand.get(s) == null)
				rand.put(s, ((double) ((int) (Math.random() * 2001)) - 1000) / 10000 + 1);
			s.setPrice(s.getPrice() * rand.get(s));
			s.setFluctuation(rand.get(s));
			rand.remove(s);
		});
	}

	public void limitUp(Stock s) {
		rand.put(s, 1.1);
	}

	public void limitDown(Stock s) {
		rand.put(s, 0.9);
	}

	public void stockOperation(Player p) {
		p.getGame().io().stockOperation(p);
	}

	@SuppressWarnings("incomplete-switch")
	public void trade(StockTradeOperation op, Player p) {
		IOHelper IO = p.getGame().io();
		StockMarket stockMarket = p.getGame().getStockMarket();
		int i = op.getIndex();
		int n = op.getNum();
		if (op.getOpType() == StockTradeOpType.QUIT)
			return;
		int money = (int) (stockMarket.getStock(i).getPrice() * n);
		switch (op.getOpType()) {
		case BUY:
			if (p.getDeposit() + p.getCash() >= money) {
				IO.alert("购入股票：" + stockMarket.getStock(i).getName() + " " + n + "股，花费" + money + "元。");
				if (!p.costDeposit(money)) {
					money -= p.getDeposit();
					p.costDeposit(p.getDeposit());
					p.costCash(money);
				}
				stockMarket.addStock(p, i, n);
			} else {
				IO.alert("金钱不足。");
			}
			return;
		case SELL:
			if (stockMarket.getPlayerStock(p, i) >= n) {
				stockMarket.reduceStock(p, i, n);
				p.addDeposit(money);
				IO.alert("卖出股票：" + stockMarket.getStock(i).getName() + " " + n + "股，获得" + money + "元。");
			} else {
				IO.alert("股票不足。");
			}
			return;
		}
	}

}
