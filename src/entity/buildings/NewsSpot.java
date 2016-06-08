package entity.buildings;

import java.util.Collection;

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
public class NewsSpot extends Building {
	private static final String ICON = "新";
	private static final String TYPE = "新闻点";

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
		newsEvent(p);
	}

	private void newsEvent(Player p) {
		IOHelper IO = p.getGame().io();

		Collection<Player> players = p.getPeers(false);

		int rand = (int) (Math.random() * 5);
		int randMoney = (int) (Math.random() * 49 + 1) * 100;
		switch (rand) {
		case 0:
			Player landlord = p;
			for (Player player : players) {
				if (player.getEstates().size() > p.getEstates().size())
					landlord = player;
			}
			landlord.addCash(randMoney);
			IO.alert("公开表扬第一地主 " + landlord.getName() + " 奖励 " + randMoney + " 元。");
			break;
		case 1:
			Player poorMan = p;
			for (Player player : players) {
				if (player.getEstates().size() < p.getEstates().size())
					poorMan = player;
			}
			poorMan.addCash(randMoney);
			IO.alert("公开补助土地最少者 " + poorMan.getName() + " " + randMoney + " 元。");
			break;
		case 2:
			players.forEach(player -> {
				player.setDeposit((int) (player.getDeposit() * 1.1));
			});
			IO.alert("银行加发储金红利，每个人得到存款10%。");
			break;
		case 3:
			players.forEach(player -> {
				player.setDeposit((int) (player.getDeposit() * 0.9));
			});
			IO.alert("所有人缴纳财产税10%。");
			break;
		case 4:
			IO.alert("每个人得到一张卡片。");

			p.getPeers(false).forEach(player -> {
				int randCard = (int) (Math.random() * 7);
				Card newCard = null;
				switch (randCard) {
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
				IO.alert(player.getName() + "获得 " + newCard.getName() + " 。");
				player.addCard(newCard);
			});

			break;

		case 5:
			p.injure();
			IO.alert("玩家" + p.getName() + "受伤，住院2天。");
			break;
		}
	}

}
