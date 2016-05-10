package debug;

import java.util.ArrayList;

import entity.AverageCashCard;
import entity.BlackCard;
import entity.BuyEstateCard;
import entity.Estate;
import entity.Game;
import entity.LotteryCard;
import entity.Player;
import entity.RedCard;

public class Debug {
	// set initial states for debugging
	public static void debug(Game g) {
		ArrayList<Player> players = (ArrayList<Player>) g.getPlayers(true);
		g.getMap().getCells().stream().filter(c -> (c.getBuilding() instanceof Estate))
				.map(c -> (Estate) c.getBuilding()).forEach(e -> {
					if ((e.getName().equals("水星街1号")) || (e.getName().equals("水星街2号")))
						e.setOwner(players.get(0));
					else
						e.setOwner(players.get(1));
				});
		g.getPlayers(true).forEach(p -> {
			p.setCash(0);
			p.setDeposit(0);
			p.addCard(new AverageCashCard());
			p.addCard(new BlackCard());
			p.addCard(new RedCard());
			p.addCard(new BuyEstateCard());
			p.addCard(new LotteryCard());
		});
		for (int i = 0; i < 29; i++)
			g.addDay();
	}
}
