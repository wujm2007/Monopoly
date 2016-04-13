package entity;

import dao.IOHelper;

public class BuyEstateCard extends Card {

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

}
