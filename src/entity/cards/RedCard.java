package entity.cards;

import entity.IOHelper;
import entity.Player;
import entity.StockMarket;

@SuppressWarnings("serial")
public class RedCard extends Card {
	
	private static RedCard instance;

	private RedCard() {
	};

	public static RedCard getInstance() {
		if (instance == null) {
			return (instance = new RedCard());
		}
		return instance;
	}
	
	private static final String NAME = "红卡";
	private static final String DESCRIPTION = "使第二天这支股票上涨10%";

	@Override
	public int act(Player p) {
		IOHelper IO = p.getGame().io();
		int i = IO.InputInt("请输入股票代码：");
		StockMarket sm = p.getGame().getStockMarket();
		while (sm.getStock(i) == null) {
			IO.alert("Invalid position");
			i = IO.InputInt("请输入股票代码：");
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
