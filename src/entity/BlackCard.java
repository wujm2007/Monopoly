package entity;

import biz.IOHelper;

public class BlackCard extends Card {

	@Override
	public int act(Player p) {
		int i = IOHelper.InputInt("请输入股票代码：");
		StockMarket sm = p.getGame().getStockMarket();
		while (sm.getStock(i) == null) {
			IOHelper.alert("Invalid position");
			i = IOHelper.InputInt("请输入股票代码：");
		}
		sm.limitDown(sm.getStock(i));
		return 0;
	}

	@Override
	public int getPrice() {
		return 50;
	}

	@Override
	public String getName() {
		return "黑卡";
	}

	@Override
	public String getDescription() {
		return "使第二天这支股票下跌10%";
	}
}
