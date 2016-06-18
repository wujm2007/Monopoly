package entity;

import java.io.Serializable;
import java.util.List;

import entity.Player;
import entity.buildings.Bank.BankOperation;

@SuppressWarnings("serial")
public abstract class IOHelper implements Serializable {

	// return true if the input is Y and false if the input is N
	public abstract boolean inputYN(String msg);

	// return the integer input
	public abstract int inputInt(String msg);

	// Pop an alert
	public abstract void alert(String msg);

	public abstract void showBankAccountInfo(Player p);

	// get StockTradeOperation (see entity.Bank.BankOperation)
	public abstract BankOperation getBankOperation(Player p);

	public abstract void showLotteryInfo();

	// return the lottery index bought by p
	public abstract int getLotteryOperation(Player p);

	public abstract void showLotteryWinner();

	public abstract int getStoreCardIndex(Player p);

	public abstract Player chosePlayer(List<Player> playersNear);

	public abstract void stockOperation(Player p);
}
