package entity;

import dao.IOHelper;

public class CardSpot extends Building {

	@Override
	public String getIcon() {
		return "卡";
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
			newCard = new ControlDice();
			break;
		case 1:
			newCard = new RoadBlock();
			break;
		case 2:
			newCard = new AverageCashCard();
			break;
		case 3:
			newCard = new RedCard();
			break;
		case 4:
			newCard = new BlackCard();
			break;
		case 5:
			newCard = new LotteryCard();
			break;
		case 6:
			newCard = new BuyEstateCard();
			break;
		}
		IOHelper.alert("恭喜您获得 " + newCard.getName() + " 。");
		p.getCards().add(newCard);
		return;
	}

	@Override
	public String getType() {
		return "道具点";
	}

}
