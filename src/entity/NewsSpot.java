package entity;

import java.util.ArrayList;
import java.util.Collection;

import dao.IOHelper;

public class NewsSpot extends Building {

	@Override
	public String getIcon() {
		return "新";
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

		Collection<Player> c = new ArrayList<Player>();
		p.getMap().getCells().stream().filter(cell -> (!cell.getPlayers().isEmpty())).forEach(cell -> {
			c.addAll(cell.getPlayers());
		});

		int rand = (int) (Math.random() * 5);
		int randMoney = (int) (Math.random() * 49 + 1) * 100;
		switch (rand) {
		case 0:
			Player landlord = p;
			for (Player player : c) {
				if (player.getEstates().size() > p.getEstates().size())
					landlord = player;
			}
			landlord.setCash(landlord.getCash() + randMoney);
			IOHelper.showInfo("公开表扬第一地主 " + landlord.getName() + " 奖励 " + randMoney + " 元。");
			break;
		case 1:
			Player poorMan = p;
			for (Player player : c) {
				if (player.getEstates().size() < p.getEstates().size())
					poorMan = player;
			}
			poorMan.setCash(poorMan.getCash() + randMoney);
			IOHelper.showInfo("公开补助土地最少者 " + poorMan.getName() + " " + randMoney + " 元。");
			break;
		case 2:
			c.forEach(player -> {
				player.setDeposit((int) (player.getDeposit() * 1.1));
			});
			IOHelper.showInfo("银行加发储金红利，每个人得到存款10%。");
			break;
		case 3:
			c.forEach(player -> {
				player.setDeposit((int) (player.getDeposit() * 0.9));
			});
			IOHelper.showInfo("所有人缴纳财产税10%。");
			break;
		case 4:
			IOHelper.showInfo("每个人得到一张卡片。");

			p.getGame().getPlayers().stream().filter(player -> !player.isBroke()).forEach(player -> {
				int randCard = (int) (Math.random() * 7);
				Card newCard = null;
				switch (randCard) {
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
				IOHelper.alert(player.getName() + "获得 " + newCard.getName() + " 。");
				player.getCards().add(newCard);
			});

			break;
		}
	}

	@Override
	public String getType() {
		return "新闻点";
	}

}
