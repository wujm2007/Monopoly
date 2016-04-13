package dao;

import entity.*;

public class Main {
	public static void main(String args[]) {
		Game g = new Game();
		Player p = g.getPlayer(0);
		Store s = new Store();
		s.stay(p);
		CardSpot cs = new CardSpot();
		cs.stay(p);
		p.getCards().forEach(c -> {
			System.out.println(c.getName());
		});

	}
}