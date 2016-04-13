package entity;

import dao.IOHelper;

public class ControlDice extends Card {

	@Override
	public int act(Player p) {
		int steps;
		while (((steps = IOHelper.InputInt("请输入步数(1-6)：")) <= 0) || (steps > 6)) {
			IOHelper.alert("Invalid number of steps");
		}
		p.setSteps(steps);
		return 0;
	}

}