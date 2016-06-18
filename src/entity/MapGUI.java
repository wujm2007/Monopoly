package entity;

import javax.swing.JPanel;

import views.BankPanel;
import views.CardSpotPanel;
import views.CellPanel;
import views.DicePanel;
import views.EstatePanel;
import views.HospitalPanel;
import views.NewsSpotPanel;
import views.RoadPanel;
import views.StorePanel;
import views.TicketSpotPanel;

@SuppressWarnings("serial")
public class MapGUI extends Map {

	private static CellPanel cellPanels[] = { new EstatePanel(), new EstatePanel(), new EstatePanel(),
			new EstatePanel(), new EstatePanel(), new StorePanel(), new EstatePanel(), new EstatePanel(),
			new EstatePanel(), new EstatePanel(), new EstatePanel(), new NewsSpotPanel(), new EstatePanel(),
			new EstatePanel(), new EstatePanel(), new EstatePanel(), new EstatePanel(), new BankPanel(),
			new EstatePanel(), new EstatePanel(), new EstatePanel(), new EstatePanel(), new EstatePanel(),
			new HospitalPanel(), new EstatePanel(), new EstatePanel(), new EstatePanel(), new EstatePanel(),
			new EstatePanel(), new CardSpotPanel(), new TicketSpotPanel(), new TicketSpotPanel(), new TicketSpotPanel(),
			new TicketSpotPanel(), new TicketSpotPanel(), new TicketSpotPanel() };

	private static RoadPanel roadPanels[] = { new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel() };

	private static int mapl[][] = { { 1, 3 }, { 1, 2 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 }, { 9, 1 }, { 10, 1 },
			{ 10, 4 }, { 11, 4 }, { 12, 4 }, { 13, 3 }, { 16, 3 }, { 18, 4 }, { 18, 5 }, { 18, 6 }, { 18, 7 },
			{ 16, 8 }, { 15, 9 }, { 14, 9 }, { 14, 6 }, { 13, 6 }, { 12, 6 }, { 9, 6 }, { 11, 9 }, { 10, 11 },
			{ 9, 11 }, { 8, 11 }, { 7, 11 }, { 4, 9 }, { 3, 9 }, { 2, 9 }, { 2, 8 }, { 2, 7 }, { 2, 6 }, { 2, 5 } };

	private static int roadl[][] = { { 2, 3 }, { 2, 2 }, { 3, 2 }, { 4, 2 }, { 5, 2 }, { 7, 2 }, { 9, 2 }, { 10, 2 },
			{ 10, 3 }, { 11, 3 }, { 12, 3 }, { 14, 3 }, { 16, 4 }, { 17, 4 }, { 17, 5 }, { 17, 6 }, { 17, 7 },
			{ 17, 9 }, { 15, 8 }, { 14, 8 }, { 14, 7 }, { 13, 7 }, { 12, 7 }, { 10, 7 }, { 10, 9 }, { 10, 10 },
			{ 9, 10 }, { 8, 10 }, { 7, 10 }, { 5, 10 }, { 3, 9 }, { 2, 9 }, { 2, 8 }, { 2, 7 }, { 2, 6 }, { 2, 5 } };

	public MapGUI(Game game, String strMap) {
		super(game, strMap);
	}

	public void init(JPanel mapPanel, DicePanel dicePanel) {
		mapPanel.removeAll();
		mapPanel.repaint();
		for (int i = 0; i < cellPanels.length; i++) {
			roadPanels[i].setCellPanel(cellPanels[i]);
			roadPanels[i].setCell(this.getCell(i));

			cellPanels[i].setLocation(mapl[i][0], mapl[i][1]);
			roadPanels[i].setLocation(roadl[i][0], roadl[i][1]);
			mapPanel.add(cellPanels[i]);
			mapPanel.add(roadPanels[i]);
		}
		mapPanel.add(dicePanel);
	}

	public void reinit(JPanel mapPanel) {
		for (int i = 0; i < cellPanels.length; i++)
			roadPanels[i].setCell(this.getCell(i));
	}

	public void addPlayer(Player p) {
		roadPanels[p.getPosition()].setPlayerAvatar(p);
	}

	public void addRoadBlock(int pos) {
		roadPanels[pos].setRoadBlock();
	}

	public void removeRoadBlock(int pos) {
		roadPanels[pos].clear();
		if (getCells().get(pos).getLastPlayer() != null)
			roadPanels[pos].setPlayerAvatar(getCells().get(pos).getLastPlayer());
	}

	public void removePlayer(Player p, int pos) {
		roadPanels[pos].clear();
		if (getCells().get(pos).getAnotherPlayer(p) != null)
			roadPanels[pos].setPlayerAvatar(getCells().get(pos).getAnotherPlayer(p));
	}
}