package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biz_cmdLine.IOHelper;

public class StockMarket {

	public class Stock {
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
	private Map<Stock, Double> rand = new HashMap<Stock, Double>();

	public StockMarket() {
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
		Map<Stock, Integer> a = getAccount(p);
		if (a.containsKey(getStock(i)))
			a.put(getStock(i), getPlayerStock(p, i) + n);
		else
			a.put(getStock(i), n);
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
		IOHelper.showStocks(stocks);
		IOHelper.printStockAccount(getAccount(p));
		if (IOHelper.InputYN("是否进行股票交易")) {
			StockTradeOperation op;
			while ((op = IOHelper.InputStockOp()).getOpType() != StockTradeOpType.QUIT) {
				int i = op.getIndex();
				int n = op.getNum();
				if (getStock(i) == null) {
					IOHelper.alert("股票不存在！");
					continue;
				} else {
					// calculate the value of the stocks
					int money = (int) (getStock(i).getPrice() * n);
					switch (op.getOpType()) {
					case BUY:
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
					case SELL:
						if (getPlayerStock(p, i) >= n) {
							reduceStock(p, i, n);
							p.setDeposit(p.getDeposit() + money);
							IOHelper.alert("卖出股票：" + getStock(i).getName() + " " + n + "股，获得" + money + "元。");
						} else {
							IOHelper.alert("股票不足。");
						}
						break;
					default:
						break;
					}
				}
			}
		}
	}

}
