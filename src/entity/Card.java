package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Card implements Serializable {

	public abstract int act(Player p);

	public abstract int getPrice();

	public abstract String getName();

	public abstract String getDescription();
}
