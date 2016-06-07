package entity.buildings;

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
public class CardSpot extends Building {
	private static final String ICON = "卡";
	private static final String TYPE = "银行";

	@Override
	public String getIcon() {
		return ICON;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void passby(Player p) {
		return;
	}

	@Override
	public void stay(Player p) {
		int rand = (int) (Math.random() * 7);
		Card newCard = null;
		switch (rand) {
		case 0:
			newCard = ControlDice.getInstance();
			break;
		case 1:
			newCard = RoadBlock.getInstance();
			break;
		case 2:
			newCard = AverageCashCard.getInstance();
			break;
		case 3:
			newCard = RedCard.getInstance();
			break;
		case 4:
			newCard = BlackCard.getInstance();
			break;
		case 5:
			newCard = LotteryCard.getInstance();
			break;
		case 6:
			newCard = BuyEstateCard.getInstance();
			break;
		}
		p.getGame().io().alert("恭喜您获得 " + newCard.getName() + " 。");
		p.addCard(newCard);
		return;
	}

}
