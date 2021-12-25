import java.util.Random;

public class Board {

	private Square[] squares;
	private int[] dice;
	private int diceTotal;
	private Random r;
	
	public Board() {
		squares = new Square[9];
		for(int i = 0; i < squares.length; i++) {
			squares[i] = new Square(i);
		}
		dice = new int[] {-1, -1};
		r = new Random();
	}
	
	public Square[] getSquares() {
		return squares;
	}
	
	public int choose(int num) {
		if(diceTotal > 0) {
			if(squares[num].isChosen()) {
				return 0;//picked
			}
			if(!validMove(num)) {
			    return 4;//won't add up
			}
			squares[num].hit();
			diceTotal -= squares[num].value();
			if(diceTotal > 0) {
				return 1;//pick more
			} else {
				return 2;//roll
			}
		}
		return 3;//roll first
	}
	
	public boolean validMove(int target) {
	    if(!squares[target].isChosen() && squares[target].value() == diceTotal) {
	        return true;//!chosen && num=diceTotal
	    }
	    if(squares[target].value() > diceTotal) {
	        return false;//num>diceTotal
	    }
	    for(int i = 0; i < squares.length; i++) {
	        if(squares[i].isChosen() || squares[i].value() == squares[target].value()) {
                continue;//chosen||target
            }
	        if(squares[i].value() + squares[target].value() == diceTotal) {
	            return true;//num1+targenNum==diceTotal
	        }
	        for(int j = i + 1; j < squares.length; j++) {
	            if(squares[j].isChosen() || squares[j].value() == squares[target].value()) {
	                continue;//chosen||target
	            }
	            if(squares[i].value() + squares[j].value() + squares[target].value() == diceTotal) {
	                return true;//num1+num2+targetNum==diceTotal
	            } 
	            else if(squares[i].value() + squares[j].value() + squares[target].value() > diceTotal) {
	                break;//num1+num2+targetNum>diceTotal
	            }
	        }
	    }
	    return false;
	}
	
	public int[] rollDice(int num) {
		if(canRoll()) {
			dice[0] = r.nextInt(6) + 1;
			if(num == 2) {
				dice[1] = r.nextInt(6) + 1;
			} else {
				dice[1] = 0;
			}
			diceTotal = dice[0] + dice[1];
			return dice;
		}
		dice[0] = -1;
		dice[1] = -1;
		return dice;
	}
	
	public boolean canUseOneDice() {
		if(squares[6].isChosen() && squares[7].isChosen() && squares[8].isChosen()) {
			return true;
		}
		return false;
	}
	
	public boolean canRoll() {
		if(getDiceTotal() <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean checkLose() {
		for(int i = 0; i < squares.length; i++) {
			if(squares[i].isChosen()) {
				continue;
			}
			if(squares[i].value() == diceTotal) {
				return false;
			}
			if(squares[i].value() > diceTotal) {
				break;
			}
			for(int j = i + 1; j < squares.length; j++) {
				if(squares[j].isChosen()) {
					continue;
				}
				if(squares[i].value() + squares[j].value() == diceTotal) {
					return false;
				}
				if(squares[j].value() > diceTotal) {
					break;
				}
			}
		}
		return true;
	}
	
	public int checkScore() {
		int score = 0;
		for(int i = 0; i < squares.length; i++) {
			if(!squares[i].isChosen()) {
				score += squares[i].value();
			}
		}
		return score;
	}
	
	public int getDiceTotal() {
	    System.out.println("diceTotal = " + diceTotal);
	    return diceTotal;
	}
	
	public void resetDiceTotal() {
	    diceTotal = 0;
	}
}
