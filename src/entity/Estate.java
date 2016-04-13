package entity;

import dao.IOHelper;

public class Estate extends Building {
	private static final int MAX_LEVEL = 5;
	private Player owner;
	private int level;
	private int price;
	private int number;
	private String street;

	public Estate(String street, int number, double priceRate) {
		this.owner = null;
		this.level = 0;
		this.price = (int) (priceRate * 500);
		this.street = street;
		this.number = number;
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

	public int getOriginalPrice() {
		return this.price;
	}

	public int getPrice() {
		double index = 1;
		if (this.getOwner() != null) {
			long sameOwnerCount = getOwner().getMap().getCells().stream().map(c -> c.getBuilding())
					.filter(b -> (b instanceof Estate)).filter(e -> ((Estate) e).getStreet().equals(getStreet()))
					.filter(e -> e.getOwner() == this.getOwner()).count();
			index += (sameOwnerCount - 1) * 0.1;
		}
		return (int) (price * Math.pow(2, level) * index);
	}

	public int getUpgradeCost() {
		return (int) (price * (level + 1) / 2);
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
			if (IOHelper.InputYN("是否购买这栋住宅？价格: " + this.getPrice())) {
				if (p.getCash() >= this.getPrice()) {
					p.cost(this.getPrice());
					this.setOwner(p);
					IOHelper.alert("购买成功。");
				} else {
					IOHelper.alert("无法支付");
				}
			}
		} else if (this.getOwner() == p) {
			if (this.getLevel() < Estate.MAX_LEVEL - 1) {
				if (IOHelper.InputYN("是否升级这栋住宅？价格: " + this.getUpgradeCost())) {
					if (p.getCash() >= this.getUpgradeCost()) {
						p.cost(this.getUpgradeCost());
						this.setLevel(this.getLevel() + 1);
						IOHelper.alert("成功升级至" + getLevel() + "级。");
					} else {
						IOHelper.alert("无法支付");
					}
				}
			}
		} else {
			IOHelper.alert("您需要支付过路费: " + this.getPrice());
			p.cost(this.getPrice());
		}
	}

	@Override
	public String getOriginalIcon() {
		return "◎";
	}

	@Override
	public String getType() {
		return "房产";
	}

	@Override
	public String getDescription() {
		return "类型：" + this.getType() + "\n名称：" + this.getName() + "\n初始价格：" + this.getOriginalPrice() + "\n当前等级："
				+ (this.getLevel() + 1) + "\n拥有者：" + (this.getOwner() == null ? "<待售>" : this.getOwner().getName());
	}

	public String getName() {
		return this.getStreet() + this.getNumber() + "号";
	}

	public int getNumber() {
		return this.number;
	}

	public String getStreet() {
		return this.street;
	}

	public void setOwnerNull() {
		this.owner = null;
	}

}
