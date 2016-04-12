package entity;

import dao.IOHelper;

public class Estate extends Building {
	private static final int MAX_LEVEL = 5;
	private Player owner;
	private int level;
	private int price;

	public Estate(double priceRate) {
		this.owner = null;
		this.level = 0;
		this.price = (int) (priceRate * 500);
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPrice() {
		return (int) (price * Math.pow(2, level));
	}

	public int getUpgradeCost() {
		return (int) (price * level);
	}

	@Override
	public String getIcon() {
		if (this.getOwner() == null)
			return "◎";
		else
			return this.getOwner().getEstateIcon();
	}

	@Override
	public void passby(Player p) {
		return;
	}

	@Override
	public void stay(Player p) {
		if (this.getOwner() == null) {

			if (IOHelper.InputYN("Do you want to buy the house? Price: " + this.getPrice())) {
				if (p.getCash() >= this.getPrice()) {
					p.cost(this.getPrice());
					this.setOwner(p);
					IOHelper.alert("Purchase success.");
				} else {
					IOHelper.alert("You can't afford this.");
				}
			}

		} else if (this.getOwner() == p) {
			if (this.getLevel() < Estate.MAX_LEVEL) {

				if (IOHelper.InputYN("Do you want to upgrade the house? Price: " + this.getUpgradeCost())) {
					if (p.getCash() >= this.getUpgradeCost()) {
						p.cost(this.getUpgradeCost());
						this.setLevel(this.getLevel() + 1);
						IOHelper.alert("Upgrade success.");
					} else {
						IOHelper.alert("You can't afford this.");
					}
				}

			}
		} else {

			IOHelper.alert("You should pay the toll fee: " + this.getPrice());
			p.cost(this.getPrice());

		}
	}

}
