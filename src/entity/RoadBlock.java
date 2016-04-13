package entity;

import dao.IOHelper;

public class RoadBlock extends Card {

	@Override
	public int act(Player p) {
		int relativePos;
		while (((relativePos = IOHelper.InputInt("请输入位置((-8)-8)：")) < -8) || (relativePos > 8)) {
			IOHelper.alert("Invalid position");
		}
		p.getMap().getCell(p.getPosition()).getCellByRelativePos(relativePos).setBlocked();
		return 0;
	}

}