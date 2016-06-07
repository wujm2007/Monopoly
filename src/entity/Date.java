package entity;

import java.io.Serializable;

import entity.buildings.Bank;
import entity.buildings.LotterySpot;

@SuppressWarnings("serial")
public class Date implements Serializable {
	private int year = 2016, month = 1, day = 0, dayOfWeek = 4;
	private int dayOfMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static String dayName[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
	private Game game;

	public Date(Game game) {
		this.game = game;
	}

	private static boolean isLeapYear(int year) {
		return (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public String getDayOfWeek() {
		return dayName[dayOfWeek];
	}

	public void addDay() {
		day++;
		dayOfWeek = (dayOfWeek + 1) % 7;
		game.getStockMarket().refresh();
		if (day > dayOfMonth[month - 1]) {
			month++;
			day = 1;
			LotterySpot.announceWinner(game.io());
			game.getPlayers(false).forEach(p -> {
				Bank.payInterest(p);
			});
		}
		if (month > 12) {
			year++;
			if (isLeapYear(getYear()))
				dayOfMonth[1] = 29;
			else
				dayOfMonth[1] = 28;
			month = 1;
		}
		this.game.updateInfo();
	}

	public void setGame(Game game) {
		this.game = game;
	}
}