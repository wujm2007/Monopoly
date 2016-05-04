package entity;

import biz_cmdLine.IOHelper;

public class RedCard extends Card {
	private static final String NAME = "红卡";
	private static final String DESCRIPTION = "使第二天这支股票上涨10%";

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
		return NAME;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}
}
