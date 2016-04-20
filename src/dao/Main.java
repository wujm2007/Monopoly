package dao;

import java.util.ArrayList;

import entity.*;

public class Main {
	private static Game g;

	public static void main(String args[]) {
		g = new Game();
		while (true) {
			g.addDay();
			IOHelper.showInfo(
					"今天是" + g.getDate().getYear() + "年" + g.getDate().getMonth() + "月" + g.getDate().getDay() + "日。");
			g.getPlayers().stream().filter(p -> !p.isBroke()).forEach(p -> {
				IOHelper.showInfo("现在是玩家" + p.getName() + "的操作时间，您的前进方向是" + (p.isClockWise() ? "顺时针" : "逆时针"));
				printMenu(p);
				if (g.getPlayers().stream().filter(player -> !player.isBroke()).count() <= 1) {
					IOHelper.alert("Game over!");
					System.exit(0);
				}
			});
		}
	}

	public static void printMenu(Player p) {
		IOHelper.showInfo("你现在可以执行如下操作");
		IOHelper.showInfo("0 - 查看地图");
		IOHelper.showInfo("1 - 查看原始地图");
		IOHelper.showInfo("2 - 使用道具");
		IOHelper.showInfo("3 - 前方10步内示警");
		IOHelper.showInfo("4 - 查看前后指定步数的具体信息");
		IOHelper.showInfo("5 - 查看玩家的资产信息");
		IOHelper.showInfo("6 - 掷骰子");
		IOHelper.showInfo("7 - 不玩了！认输！");
		IOHelper.showInfo("8 - 进入股市");
		int command;
		while (((command = IOHelper.InputInt("请输入")) < 0) || (command > 8)) {
			IOHelper.alert("请输入正确的操作代码。");
		}
		switch (command) {
		case 0:
			printMap();
			printMenu(p);
			break;
		case 1:
			printOriginalMap();
			printMenu(p);
			break;
		case 2:
			useItem(p);
			printMenu(p);
			break;
		case 3:
			showWarnings(p);
			printMenu(p);
			break;
		case 4:
			showMsgInCertainSteps(p);
			printMenu(p);
			break;
		case 5:
			showAsset();
			printMenu(p);
			break;
		case 6:
			p.go();
			break;
		case 7:
			p.broke();
			break;
		case 8:
			g.getStockMarket().stockOperation(p);
			printMenu(p);
			break;
		}
	}

	// a primitive way to print map
	public static void printMap() {
		Map map = g.getMap();
		int length = map.getLength();
		for (int i = 0; i < 20; i++)
			System.out.print(map.getCell(i).getIcon());
		for (int i = 20; i < 20 + (length - 40) / 2; i++) {
			int j = length - 1 + 20 - i;
			System.out.printf("\n%s                                     %s", map.getCell(i).getIcon(),
					map.getCell(j).getIcon());
		}
		System.out.printf("\n");
		for (int i = length - 1 - (length - 40) / 2; i > length - 1 - (length - 40) / 2 - 20; i--) {
			System.out.print(map.getCell(i).getIcon());
		}
		System.out.printf("\n");
	}

	// a primitive way to print map
	public static void printOriginalMap() {
		Map map = g.getMap();
		int length = map.getLength();
		for (int i = 0; i < 20; i++)
			System.out.print(map.getCell(i).getOriginalIcon());
		for (int i = 20; i < 20 + (length - 40) / 2; i++) {
			int j = length - 1 + 20 - i;
			System.out.printf("\n%s                                     %s", map.getCell(i).getOriginalIcon(),
					map.getCell(j).getIcon());
		}
		System.out.printf("\n");
		for (int i = length - 1 - (length - 40) / 2; i > length - 1 - (length - 40) / 2 - 20; i--) {
			System.out.print(map.getCell(i).getOriginalIcon());
		}
		System.out.printf("\n");
	}

	private static void useItem(Player p) {
		ArrayList<Card> cards = p.getCards();
		if (cards.size() == 0) {
			IOHelper.showInfo("您没有卡片。");
			return;
		}
		cards.forEach(c -> {
			IOHelper.showInfo((cards.indexOf(c) + 1) + ":" + c.getName());
		});

		int index;
		if (((index = IOHelper.InputInt("请输入您要使用的卡片编号(输入0退出)") - 1) >= 0) && (index < cards.size())) {
			if (cards.get(index).act(p) == 0) {
				cards.remove(index);
			} else {
				IOHelper.alert("使用失败。");
			}
		} else if (index == -1)
			return;
		else {
			IOHelper.alert("编号错误。");
			useItem(p);
		}

	}

	private static void showAsset() {
		IOHelper.showInfo("玩家资产信息如下:\n玩家名\t点券\t现金\t存款\t房产\t资产总额\t");
		g.getPlayers().forEach(p -> {
			IOHelper.showInfo(String.format("%s\t%d\t%d\t%d\t%d\t%d\t", p.getName(), p.getTickets(), p.getCash(),
					p.getDeposit(), p.getEstates().size(), p.getAsset()));
		});
	}

	private static void showMsgInCertainSteps(Player p) {
		int relativePos = IOHelper.InputInt("请输入要查询的地点与您的相对步数");
		Cell c = g.getMap().getCell(p.getPosition());
		IOHelper.showInfo(c.getCellByRelativePos(relativePos).getBuilding().getDescription());
	}

	private static void showWarnings(Player p) {
		Cell c = g.getMap().getCell(p.getPosition());
		boolean noBlock = true;
		for (int i = 0; i < 11; i++) {
			if (c.getCellByRelativePos(i).isBlocked()) {
				IOHelper.showInfo("前方" + i + "步处有路障。");
				noBlock = false;
			}
		}
		if (noBlock)
			IOHelper.showInfo("前方10步之内没有路障。");
	}

}