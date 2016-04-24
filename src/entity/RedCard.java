package entity;

import dao.IOHelper;
import dao.StockMarket;

public class RedCard extends Card {

	@Override
	public int act(Player p) {
		int i = IOHelper.InputInt("请输入股票代码：");
		StockMarket sm = p.getGame().getStockMarket();
		while (sm.getStock(i) == null) {
			IOHelper.alert("Invalid position");
			i = IOHelper.InputInt("请输入股票代码：");
		}
		sm.limitUp(sm.getStock(i));
		return 0;
	}

	@Override
	public int getPrice() {
		return 80;
	}

	@Override
	public String getName() {
		return "红卡";
	}

	@Override
	public String getDescription() {
		return "使第二天这支股票上涨 10%";
	}
}
