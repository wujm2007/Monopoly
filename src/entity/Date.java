package entity;

public class Date {
	private int year = 2016, month = 1, day = 0;
	private int dayOfMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
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

	public void addDay() {
		day++;
		game.getStockMarket().refresh();
		if (day > dayOfMonth[month - 1]) {
			month++;
			day = 1;
			LotterySpot.announceWinner();
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
	}
}