package entity;

import biz_cmdLine.IOHelper;

public class BuyEstateCard extends Card {
	private static final String NAME = "购地卡";
	private static final String DESCRIPTION = "强行用现价购买自己当前所在位置的土地(发动者不能购买自己的房屋)";

	@Override
	public int act(Player p) {
		Cell c = p.getMap().getCell(p.getPosition());
		if (c.getBuilding() instanceof Estate) {
			Estate e = (Estate) c.getBuilding();
			if (e.getOwner() != p) {
				p.cost(e.getPrice());
				((Estate) (c.getBuilding())).setOwner(p);
				IOHelper.alert("购买成功！");
				return 0;
			}
		}
		IOHelper.alert("无法购买！");
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
