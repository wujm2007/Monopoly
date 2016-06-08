package entity.buildings;

import java.util.ArrayList;
import java.util.List;

import entity.IOHelper;
import entity.Player;
import entity.cards.AverageCashCard;
import entity.cards.BlackCard;
import entity.cards.BuyEstateCard;
import entity.cards.Card;
import entity.cards.ControlDice;
import entity.cards.LotteryCard;
import entity.cards.RedCard;
import entity.cards.RoadBlock;

@SuppressWarnings("serial")
public class Store extends Building {
	private static final String icon = "道";
	private static final String type = "道具店";
	private List<Card> cards;

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
		IOHelper IO = p.getGame().io();
		cards = new ArrayList<Card>();
		cards.add(ControlDice.getInstance());
		cards.add(RoadBlock.getInstance());
		cards.add(AverageCashCard.getInstance());
		cards.add(RedCard.getInstance());
		cards.add(BlackCard.getInstance());
		cards.add(LotteryCard.getInstance());
		cards.add(BuyEstateCard.getInstance());

		if (cards.size() == 0) {
			IO.alert("卡片已售罄。");
			return;
		}

		while (IO.inputYN("是否需要购买卡片？")) {
			String info = "";
			for (Card c : cards) {
				int index = cards.indexOf(c);
				info += "No." + (index + 1) + " " + c.getName() + ": \t" + c.getPrice() + "\n";
			}
			IO.alert(info + "您拥有" + p.getTickets() + "点券。");
			int index = IO.inputInt("请输入您要购买的卡片编号: ") - 1;
			if (index != -1)
				trade(index, p);
		}
	}

	private void trade(int index, Player p) {
		IOHelper IO = p.getGame().io();
		if ((index >= 0) && (index < cards.size())) {
			if (p.getTickets() >= cards.get(index).getPrice()) {
				p.setTickets(p.getTickets() - cards.get(index).getPrice());
				p.addCard(cards.get(index));
				cards.remove(index);
			} else {
				IO.alert("点券不足。");
			}
		} else {
			IO.alert("编号错误。");
		}
	}
}
