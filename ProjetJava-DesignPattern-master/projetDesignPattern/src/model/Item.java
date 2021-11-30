package model;

import java.util.Random;

import utils.ItemType;

public class Item {
	private ItemType itemType;
	private int x;
	private int y;


	public Item(int x, int y) {
		this.itemType = null;
		this.x=x;
		this.y=y;
	}
	
	// ON A UNE CHANCE SUR 20 DE METTRE UN ITEM
	public ItemType chooseAleatoireItem() {
		Random hasard= new Random(System.currentTimeMillis());
		switch (hasard.nextInt(6)) {
		case 0:
			setItemType(ItemType.FIRE_SUIT);
			return getItemType();
		case 1:
			setItemType(ItemType.FIRE_UP);
			return getItemType();
		case 2:
			setItemType(ItemType.SKULL);
			return getItemType();
		case 3:
			setItemType(ItemType.FIRE_DOWN);
			return getItemType();
		default:
			return null;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
}
