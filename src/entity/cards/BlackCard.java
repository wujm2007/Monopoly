package entity.cards;

import entity.IOHelper;
import entity.Player;
import entity.StockMarket;

@SuppressWarnings("serial")
public class BlackCard extends Card {

	private static BlackCard instance;

	private static final String NAME = "黑卡";
	private static final String DESCRIPTION = "使第二天这支股票下跌10%";

	private BlackCard() {
	};

	public static BlackCard getInstance() {
		if (instance == null) {
			return (instance = new BlackCard());
		}
		return instance;
	}

	@Override
	public int act(Player p) {
		IOHelper IO = p.getGame().io();
		int i = IO.InputInt("请输入股票代码：");
		StockMarket sm = p.getGame().getStockMarket();
		while (sm.getStock(i) == null) {
			IO.alert("股票代码错误！");
			i = IO.InputInt("请输入股票代码：");
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
