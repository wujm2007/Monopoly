package entity.cards;

import entity.Cell;
import entity.IOHelper;
import entity.Player;
import entity.buildings.Estate;

@SuppressWarnings("serial")
public class BuyEstateCard extends Card {

	private static BuyEstateCard instance;

	private static final String NAME = "购地卡";
	private static final String DESCRIPTION = "强行用现价购买自己当前所在位置的土地(发动者不能购买自己的房屋)";

	private BuyEstateCard() {
	};

	public static BuyEstateCard getInstance() {
		if (instance == null) {
			return (instance = new BuyEstateCard());
		}
		return instance;
	}

	@Override
	public int act(Player p) {
		IOHelper IO = p.getGame().io();
		Cell c = p.getMap().getCell(p.getPosition());
		if (c.getBuilding() instanceof Estate) {
			Estate e = (Estate) c.getBuilding();
			if (e.getOwner() != p) {
				p.cost(e.getPrice());
				((Estate) (c.getBuilding())).setOwner(p);
				IO.alert("购买成功！");
				return 0;
			}
		}
		IO.alert("无法购买！");
		return -1;
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
