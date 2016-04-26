package entity;

import java.util.ArrayList;
import java.util.HashMap;

import biz.IOHelper;

public class StockMarket {

	private class Stock {
		private String name;
		private double price;

		public Stock(String name, double price) {
			this.name = name;
			this.price = price;
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

	}

	private ArrayList<Stock> stocks = new ArrayList<Stock>();
	private HashMap<Player, HashMap<Stock, Integer>> acoounts = new HashMap<Player, HashMap<Stock, Integer>>();
	private HashMap<Stock, Double> rand = new HashMap<Stock, Double>();

	public StockMarket() {
		// add 10 stocks and randomly generate their prices
		stocks.add(new Stock("股票0", 10));
		stocks.add(new Stock("股票1", 10));
		stocks.add(new Stock("股票2", 10));
		stocks.add(new Stock("股票3", 10));
		stocks.add(new Stock("股票4", 10));
		stocks.add(new Stock("股票5", 10));
		stocks.add(new Stock("股票6", 10));
		stocks.add(new Stock("股票7", 10));
		stocks.add(new Stock("股票8", 10));
		stocks.add(new Stock("股票9", 10));
		for (int i = 0; i < 100; i++)
			refresh();
	}

	public Stock getStock(int n) {
		if ((n > 0) && (n <= stocks.size()))
			return stocks.get(n - 1);
		else
			return null;
	}

	public HashMap<Stock, Integer> getAccount(Player p) {
		if (!this.acoounts.containsKey(p))
			this.acoounts.put(p, new HashMap<Stock, Integer>());
		return acoounts.get(p);
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

	public void addStock(Player p, int i, int n) {
		HashMap<Stock, Integer> a = getAccount(p);
		if (a.containsKey(getStock(i)))
			a.put(getStock(i), getPlayerStock(p, i) + n);
		else
			a.put(getStock(i), n);
	}

	public void reduceStock(Player p, int i, int n) {
		HashMap<Stock, Integer> a = getAccount(p);
		a.put(getStock(i), getPlayerStock(p, i) - n);
	}

	public void printAccount(Player p) {
		IOHelper.showInfo("您的股票账户：");
		this.getAccount(p).forEach((s, i) -> {
			if (i > 0)
				IOHelper.showInfo(s.getName() + ": " + i + "股, 价值：" + s.getPrice() * i + "元。");
		});
	}

	public void refresh() {
		this.stocks.forEach(s -> {
			if (rand.get(s) == null)
				rand.put(s, ((double) ((int) (Math.random() * 2001)) - 1000) / 10000 + 1);
			s.setPrice(s.getPrice() * rand.get(s));
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
		// print stock information
		stocks.stream().forEach(s -> {
			IOHelper.showInfo(
					(stocks.indexOf(s) + 1) + ":\t" + s.getName() + "\t\t单价:\t" + String.format("%.2f", s.getPrice()));
		});
		if (IOHelper.InputYN("是否进行股票交易")) {
			// ops is a String array, which contains the transaction code
			String ops[];
			// (ops == null) means that the player wants to quit transaction
			while ((ops = IOHelper.InputStockOp()) != null) {
				// ops[1] is the ID of the stock
				int i = Integer.parseInt(ops[1]);
				// ops[2] is the amount
				int n = Integer.parseInt(ops[2]);
				if (getStock(i) == null) {
					IOHelper.alert("股票不存在！");
					return;
				} else {
					// calculate the value of the stocks
					int money = (int) (getStock(i).getPrice() * n);
					switch (ops[0]) {
					// buy stock
					case "b":
						if (p.getDeposit() + p.getCash() >= money) {
							IOHelper.alert("购入股票：" + getStock(i).getName() + " " + n + "股，花费" + money + "元。");
							if (p.getDeposit() >= money) {
								p.setDeposit(p.getDeposit() - money);
							} else {
								money -= p.getDeposit();
								p.setDeposit(0);
								p.setCash(p.getCash() - money);
							}
							addStock(p, i, n);
						} else {
							IOHelper.alert("金钱不足。");
						}
						break;
					// sell stock
					case "s":
						if (getPlayerStock(p, i) >= n) {
							reduceStock(p, i, n);
							p.setDeposit(p.getDeposit() + money);
							IOHelper.alert("卖出股票：" + getStock(i).getName() + " " + n + "股，获得" + money + "元。");
						} else {
							IOHelper.alert("股票不足。");
						}
						break;
					}
				}
			}
		}
	}

}
