package entity;

public abstract class Card {

	// the action of the card
	public abstract int act(Player p);

	public abstract int getPrice();

	public abstract String getName();

	public abstract String getDescription();
}
