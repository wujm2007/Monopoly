package entity;

import biz_cmdLine.IOHelper;

public class BlackCard extends Card {
	private static final String NAME = "黑卡";
	private static final String DESCRIPTION = "使第二天这支股票下跌10%";

	@Override
	public int act(Player p) {
		int i = IOHelper.InputInt("请输入股票代码：");
		StockMarket sm = p.getGame().getStockMarket();
		while (sm.getStock(i) == null) {
			IOHelper.alert("股票代码错误！");
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
		return NAME;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}
}
