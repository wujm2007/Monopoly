package entity;

import java.util.ArrayList;

import biz.IOHelper;

public class Store extends Building {

	@Override
	public String getIcon() {
		return "道";
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

		while (IOHelper.InputYN("是否需要购买卡片？")) {
			if (cards.size() == 0) {
				IOHelper.showInfo("卡片已售罄。");
				break;
			}
			cards.forEach(c -> {
				int index = cards.indexOf(c);
				IOHelper.showInfo("No." + (index + 1) + " " + c.getName() + ": \t" + c.getPrice());
			});
			IOHelper.showInfo("您拥有" + p.getTickets() + "点券。");
			int index;
			if (((index = IOHelper.InputInt("请输入您要购买的卡片编号: ") - 1) >= 0) && (index < cards.size())) {
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

	@Override
	public String getType() {
		return "道具店";
	}
}
