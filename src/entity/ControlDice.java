package entity;

@SuppressWarnings("serial")
public class ControlDice extends Card {

	private static ControlDice instance;

	private static final String NAME = "遥控骰子";
	private static final String DESCRIPTION = "使用时可以任意控制骰子的结果(1-6)";

	private ControlDice() {
	};

	public static ControlDice getInstance() {
		if (instance == null) {
			return (instance = new ControlDice());
		}
		return instance;
	}

	@Override
	public int act(Player p) {
		IOHelper IO = p.getGame().io();
		int steps;
		while (((steps = IO.InputInt("请输入步数(1-6)：")) <= 0) || (steps > 6)) {
			IO.alert("请输入正确的步数！");
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