import java.awt.Color;

public class Square {
	
	private int sNum;
	private boolean isChosen;
	private Color bg;
	
	public Square(int num) {
		isChosen = false;
		sNum = num;
		bg = null;
	}
	
	public int getNum() {
		return sNum;
	}
	
	public int value() {
		return sNum + 1;
	}
	
	public boolean isChosen() {
		return isChosen;
	}
	
	public Color getColor() {
		return bg;
	}
	
	public void setNum(int num) {
		sNum = num;
	}
	
	public void hit() {
		isChosen = true;
		bg = new Color(240,240,130);
	}
	
	public void setSquareHit(boolean hit) {
		isChosen = hit;
	}
	
	public void setBackground(Color background) {
		this.bg = background;
	}
	
}
