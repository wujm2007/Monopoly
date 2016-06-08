package biz_cmdLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import entity.*;
import entity.cards.Card;

public class Main {
	private static Game g;
	private static IOHelper_cmdLine IO = IOHelper_cmdLine.getInstance();

	public static void main(String args[]) {
		g = new Game(2, false, IO);
		IO.showInfo("========================\t游   戏   开   始\t========================");
		while (true) {
			g.addDay();
			Date date = g.getDate();
			IO.showInfo("今天是" + date.getYear() + "年" + date.getMonth() + "月" + date.getDay() + "日。");
			g.getPlayers(false).forEach(p -> {
				IO.showInfo("现在是玩家" + p.getName() + "的操作时间，您的前进方向是" + (p.isClockwise() ? "顺时针" : "逆时针"));
				printMenu(p);
				if (g.getPlayers(false).stream().count() <= 1) {
					IO.alert("游戏结束!");
					System.exit(0);
				}
			});
		}
	}

	public static void printMenu(Player p) {
		IO.showInfo("你现在可以执行如下操作");
		IO.showInfo("0 - 查看地图");
		IO.showInfo("1 - 查看原始地图");
		IO.showInfo("2 - 使用道具");
		IO.showInfo("3 - 前方10步内示警");
		IO.showInfo("4 - 查看前后指定步数的具体信息");
		IO.showInfo("5 - 查看玩家的资产信息");
		IO.showInfo("6 - 掷骰子");
		IO.showInfo("7 - 不玩了！认输！");
		IO.showInfo("8 - 进入股市");
		int command;
		while (((command = IO.inputInt("请输入")) < 0) || (command > 8)) {
			IO.alert("请输入正确的操作代码。");
		}
		switch (command) {
		case 0:
			printMap(false, p);
			printMenu(p);
			break;
		case 1:
			printMap(true, p);
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

	private static ArrayList<String> generateIconMap(boolean original, Player p) {
		ArrayList<String> rtn = new ArrayList<String>();
		g.getMap().getCells().forEach(c -> {
			if ((c.hasPlayer(p)) && (!original))
				rtn.add(p.getIcon());
			else
				rtn.add(c.getIcon(original));
		});
		return rtn;
	}

	// a primitive way to print map
	public static void printMap(boolean original, Player p) {
		ArrayList<String> iconMap = generateIconMap(original, p);
		int length = iconMap.size();
		for (int i = 0; i < 20; i++)
			System.out.print(iconMap.get(i) + "\t");
		System.out.printf("\n");
		for (int i = 20; i < 20 + (length - 40) / 2; i++) {
			int j = length - 1 + 20 - i;
			System.out.printf("\n%s\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t%s", iconMap.get(j), iconMap.get(i));
			System.out.printf("\n");
		}
		System.out.printf("\n");
		for (int i = length - 1 - (length - 40) / 2; i > length - 1 - (length - 40) / 2 - 20; i--) {
			System.out.print(iconMap.get(i) + "\t");
		}
		System.out.printf("\n\n");
	}

	// print ustItem menu
	private static void useItem(Player p) {
		HashMap<Card, Integer> cards = p.getCards();
		if (cards.size() == 0) {
			IO.showInfo("您没有卡片。");
			return;
		}

		int i = 0;
		HashMap<Integer, Card> selectionMap = new HashMap<Integer, Card>();
		for (Entry<Card, Integer> entry : cards.entrySet()) {
			i++;
			selectionMap.put(i, entry.getKey());
			System.out.println(i + ": " + entry.getKey().getName() + " x" + entry.getValue());
		}

		int index;
		if (((index = IO.inputInt("请输入您要使用的卡片编号(输入0退出)")) > 0) && (index <= i)) {
			if (selectionMap.get(index).act(p) == 0)
				p.removeCard(selectionMap.get(index));
			else
				IO.alert("使用失败。");
		} else if (index == 0)
			return;
		else {
			IO.alert("编号错误。");
			useItem(p);
		}
	}

	// print asset of players
	private static void showAsset() {
		IO.showInfo("玩家资产信息如下:\n玩家名\t点券\t现金\t存款\t房产\t资产总额\t");
		g.getPlayers(true).forEach(p -> {
			if (p.isBroke())
				IO.showInfo(String.format("%s\t已破产", p.getName()));
			else
				IO.showInfo(String.format("%s\t%d\t%d\t%d\t%d\t%d\t", p.getName(), p.getTickets(), p.getCash(),
						p.getDeposit(), p.getEstates().size(), p.getAsset()));
		});
	}

	// print the information of a cell in certain steps
	private static void showMsgInCertainSteps(Player p) {
		int relativePos = IO.inputInt("请输入要查询的地点与您的相对步数");
		Cell c = g.getMap().getCell(p.getPosition());
		IO.showInfo(c.getCellByRelativePos(relativePos).getBuilding().getDescription());
	}

	// print warnings in 10 steps
	private static void showWarnings(Player p) {
		Cell c = g.getMap().getCell(p.getPosition());
		boolean noBlock = true;
		for (int i = 0; i < 11; i++) {
			if (c.getCellByRelativePos(i).isBlocked()) {
				IO.showInfo("前方" + i + "步处有路障。");
				noBlock = false;
			}
		}
		if (noBlock)
			IO.showInfo("前方10步之内没有路障。");
	}

}