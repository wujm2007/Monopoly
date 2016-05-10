package entity;

import java.util.ArrayList;

import biz_cmdLine.IOHelper;

public class Store extends Building {
	private static final String icon = "道";
	private static final String type = "道具店";

	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void passby(Player p) {
		return;
	}

	@Override
	public void stay(Player p) {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new ControlDice());
		cards.add(new RoadBlock());
		cards.add(new AverageCashCard());
		cards.add(new RedCard());
		cards.add(new BlackCard());
		cards.add(new LotteryCard());
		cards.add(new BuyEstateCard());

		if (cards.size() == 0) {
			IOHelper.alert("卡片已售罄。");
			return;
		}

		while (IOHelper.InputYN("是否需要购买卡片？")) {
			cards.forEach(c -> {
				int index = cards.indexOf(c);
				IOHelper.showInfo("No." + (index + 1) + " " + c.getName() + ": \t" + c.getPrice());
			});

			IOHelper.showInfo("您拥有" + p.getTickets() + "点券。");

			int index = IOHelper.InputInt("请输入您要购买的卡片编号: ") - 1;

			if ((index >= 0) && (index < cards.size())) {
				if (p.getTickets() >= cards.get(index).getPrice()) {
					p.setTickets(p.getTickets() - cards.get(index).getPrice());
					p.addCard(cards.get(index));
					cards.remove(index);
				} else {
					IOHelper.alert("点券不足。");
				}
			} else {
				IOHelper.alert("编号错误。");
				continue;
			}
		}
	}
}
