package entity;

import biz_cmdLine.IOHelper;

public class ControlDice extends Card {
	private static final String NAME = "遥控骰子";
	private static final String DESCRIPTION = "使用时可以任意控制骰子的结果(1-6)";

	@Override
	public int act(Player p) {
		int steps;
		while (((steps = IOHelper.InputInt("请输入步数(1-6)：")) <= 0) || (steps > 6)) {
			IOHelper.alert("请输入正确的步数！");
		}
		p.setSteps(steps);
		return 0;
	}

	@Override
	public int getPrice() {
		return 30;
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