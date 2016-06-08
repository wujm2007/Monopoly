package entity.cards;

import entity.IOHelper;
import entity.Player;

@SuppressWarnings("serial")
public class RoadBlock extends Card {

	private static RoadBlock instance;

	private static final String NAME = "路障卡";
	private static final String DESCRIPTION = "可以在前后8步之内安放一个路障,任意玩家经过路障时会停在路障所在位置不能前行";

	private RoadBlock() {
	};

	public static RoadBlock getInstance() {
		if (instance == null) {
			return (instance = new RoadBlock());
		}
		return instance;
	}

	@Override
	public int act(Player p) {
		IOHelper IO = p.getGame().io();
		int relativePos;
		while (((relativePos = IO.inputInt("请输入位置((-8)-8)：")) < -8) || (relativePos > 8)) {
			IO.alert("位置错误！");
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