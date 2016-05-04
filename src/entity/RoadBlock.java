package entity;

import biz_cmdLine.IOHelper;

public class RoadBlock extends Card {
	private static final String NAME = "路障卡";
	private static final String DESCRIPTION = "可以在前后8步之内安放一个路障,任意玩家经过路障时会停在路障所在位置不能前行";

	@Override
	public int act(Player p) {
		int relativePos;
		while (((relativePos = IOHelper.InputInt("请输入位置((-8)-8)：")) < -8) || (relativePos > 8)) {
			IOHelper.alert("Invalid position");
		}
		p.getMap().getCell(p.getPosition()).getCellByRelativePos(relativePos).setBlocked();
		return 0;
	}

	@Override
	public int getPrice() {
		return 20;
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