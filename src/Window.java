import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

public class Window extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    static Window window;
    ImageIcon faces[];
    int[] dice;
    int numOfDice;
    int prevPlayerResult;
    boolean justFinished=false;
    
    private final int BUTTON_WIDTH = 50;
    private final int BUTTON_HEIGHT = 50;
    private final int BUTTON_X = 140, BUTTON_Y = 50;
    private JPanel contentPane, resultPanel;
    private JButton[] buttons;
    private JButton btnRollOne, btnRollTwo;
    private JButton btnNewGame, btnGamePvC, btnGamePvP;
    private JLabel lblRoll;
    private JLabel lblGameResult, lblUpdate;
    private JLabel lblDice1;
    private JLabel lblDice2;
    
    private Board board;

    private static Image icon = Toolkit.getDefaultToolkit().getImage("Icon/icon.jpg");
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new Window();
                    window.setTitle("Close The Sector");
                    window.setVisible(true);
                    window.setResizable(false);
                    window.setDefaultCloseOperation(Window.EXIT_ON_CLOSE);
                    window.setIconImage(icon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public Window() {
        faces = new ImageIcon[7];
        for(int i = 0; i <= 6; i++) {
            faces[i] = new ImageIcon(this.getClass().getResource("resources/" + i + ".jpg"));
        }
        dice = new int[] {-1, -1};
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblGameName = new JLabel("Close The Sector");
        lblGameName.setHorizontalAlignment(SwingConstants.CENTER);
        lblGameName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblGameName.setBounds(129, 11, 177, 30);
        contentPane.add(lblGameName);
        
        lblDice1 = new JLabel("dice1");
        lblDice1.setIcon(faces[0]);
        lblDice1.setBounds(17, 108, 50, 50);
        contentPane.add(lblDice1);
        
        lblDice2 = new JLabel("dice2");
        lblDice2.setIcon(faces[0]);
        lblDice2.setBounds(77, 108, 50, 50);
        contentPane.add(lblDice2);
        
        lblUpdate = new JLabel("");
        lblUpdate.setHorizontalAlignment(SwingConstants.CENTER);
        lblUpdate.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblUpdate.setBounds(49, 209, 337, 52);
        contentPane.add(lblUpdate);

        lblRoll = new JLabel("Roll");
        lblRoll.setHorizontalAlignment(SwingConstants.CENTER);
        lblRoll.setBounds(31, 47, 84, 14);
        contentPane.add(lblRoll);
        
        buttons = new JButton[9];
        int x = 0;
        int y = 0;
        for(int i = 0; i <= 8; i++) {
            if(i % 3 == 0 && i != 0) {
                x = 0;
                y += BUTTON_HEIGHT;
            }
            buttons[i] = new JButton("" + (i + 1));
            buttons[i].setBounds(BUTTON_X + ((BUTTON_WIDTH + 1) * x), (BUTTON_Y + y), BUTTON_WIDTH, BUTTON_HEIGHT);
            buttons[i].addActionListener(this);
            contentPane.add(buttons[i]);
            x++;
        }
        
        btnRollOne = new JButton("One");
        btnRollOne.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnRollOne.setBounds(10, 67, 60, 30);
        btnRollOne.addActionListener(this);
        btnRollOne.setEnabled(false);
        contentPane.add(btnRollOne);
        
        btnRollTwo = new JButton("Two");
        btnRollTwo.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnRollTwo.setBounds(73, 67, 60, 30);
        btnRollTwo.addActionListener(this);
        contentPane.add(btnRollTwo);

        resultPanel = new JPanel();
        resultPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        resultPanel.setBounds(10, 160, 120, 30);
        lblGameResult = new JLabel("Result: ");
        lblGameResult.setFont(new Font("Tahoma", Font.PLAIN, 15));
        resultPanel.add(lblGameResult);
        contentPane.add(resultPanel);
        resultPanel.setVisible(false);
        
        btnNewGame = new JButton("New Game");
        btnNewGame.setBounds(312, 55, 112, 37);
        btnNewGame.addActionListener(this);
        contentPane.add(btnNewGame);

        btnGamePvC = new JButton("PvsC");
        btnGamePvC.setBounds(312, 105, 112, 37);
        btnGamePvC.addActionListener(this);
        contentPane.add(btnGamePvC);

        btnGamePvP = new JButton("PvsP");
        btnGamePvP.setBounds(312, 155, 112, 37);
        btnGamePvP.addActionListener(this);
        contentPane.add(btnGamePvP);
        
        board = new Board();
    }
    
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < buttons.length; i++) {
            if(e.getSource() == buttons[i]) {
                int hitResult = board.choose(i);
                buttons[i].setBackground(board.getSquares()[i].getColor());
                if(board.getDiceTotal() == 0 && board.checkScore() == 0) {
                    boardWin(0);
                }
                else {
                    lblUpdate.setText(boardResponse(hitResult));
                    if(hitResult == 2 && board.canUseOneDice()) {
                        btnRollOne.setEnabled(true);
                    }
                }               
            }
        }
        if(e.getSource() == btnRollTwo) {
            numOfDice = 2;
            dice = board.rollDice(numOfDice);
            System.out.println("dice1 = " + dice[0] + ", dice2 = " + dice[1]);
            if(dice[0] >= 0) {
                lblDice1.setIcon(faces[dice[0]]);
                lblDice2.setIcon(faces[dice[1]]);
                if(!board.checkLose()) {
                    lblUpdate.setText("[ "+board.getDiceTotal() + " ]   Pick  numbers.");
                } else {
                    boardWin(board.checkScore());
                }
            } else {
                lblUpdate.setText("Pick numbers first!");
            }
        }
        if(e.getSource() == btnRollOne) {
            numOfDice = 1;
            dice = board.rollDice(numOfDice);
            System.out.println("dice1 = " + dice[0] + ", dice2 = " + dice[1]);
            if(dice[0] >= 0) {
                lblDice1.setIcon(faces[dice[0]]);
                lblDice2.setIcon(faces[dice[1]]);
                if(!board.checkLose()) {
                    lblUpdate.setText("[ "+board.getDiceTotal() + " ]   Pick numbers.");
                } else {
                    boardWin(board.checkScore());
                }
            } else {
                lblUpdate.setText("Pick numbers first!");
            }
        }
        if(e.getSource() == btnNewGame) {
            resultPanel.setVisible(false);
            resetBoard();
        }
        if(e.getSource() == btnGamePvC){
            if(board.checkLose())
                if(board.checkScore()!=45)
                    playPvC(board.checkScore());
                else {
                    resultPanel.setVisible(true);
                    lblGameResult.setText("Finish first!");
            }
        }
        if(e.getSource() == btnGamePvP){
            if(board.checkLose())
                if(board.checkScore()!=45)
                    playPvP(board.checkScore());
                else {
                    resultPanel.setVisible(true);
                    lblGameResult.setText("Finish first!");
                }
        }
    }

    public void playPvP(int prevResult){
        prevPlayerResult=prevResult;
        resultPanel.setVisible(true);
        lblGameResult.setText("Player 1 got: "+prevResult);
        resetBoard();
    }

    public void playPvC(int prevResult){
        prevPlayerResult=prevResult;
        resultPanel.setVisible(true);
        lblGameResult.setText("Player got: "+prevResult);
        resetBoard();

        ArrayList<Square> sqs = new ArrayList<>();
        Collections.addAll(sqs, board.getSquares());

        Collections.reverse(sqs);
        ArrayList<Square> valid = new ArrayList<>();

        int score=45;

        Random r = new Random();
        int save=0;
        while(save<82&&!sqs.isEmpty()) {
                save++;

                int diceTotal;

                int oneOrTwo=0;
                for(Square s: valid){
                    if(s.getNum()==9||s.getNum()==8||s.getNum()==7)
                    oneOrTwo++;
                }

                if(oneOrTwo!=3){
                    diceTotal=r.nextInt(6) + 1 + r.nextInt(6) + 1;
                }
                else{
                    diceTotal=r.nextInt(6) + 1;
                }

                ArrayList<Square> temp = new ArrayList<>();
                temp.addAll(sqs);
                
                for(Square aS: sqs){
                    for(Square bS: temp){
                        if(bS.getNum()!=aS.getNum())
                            if(bS.getNum()+aS.getNum()==diceTotal){
                                valid.add(aS);
                                valid.add(bS);
                                sqs.remove(aS);
                                sqs.remove(bS);
                                diceTotal=-1;
                                break;
                            }
                    }
                    if(diceTotal==-1)
                        break;
                }
                
        }

        for(Square s: valid){
            score -= s.getNum();
        }
        
        if(score<prevPlayerResult)
            lblGameResult.setText("Winner: bot!");
        else if(score==prevPlayerResult)
            lblGameResult.setText("Tie!");
        else
            lblGameResult.setText("Winner: player!");
        lblUpdate.setText("Bot score: [ "+score+" ]  (Player got [ "+prevPlayerResult+" ])");

        System.out.println("Bot score: "+score);
        prevPlayerResult=45;
    }
    
    public String boardResponse(int num) {
        if(num == 0) {
            return "Number is already chosen. Choose another.";
        } else if(num == 1) {
            return "Pick one more.";
        } else if(num == 2) {
            return "Roll again.";
        } else if(num == 3) {
            return "Roll first!";
        } else if(num == 4) {
            return "Won't add up!";
        }
        return "Error";
    }
    
    public void boardWin(int score) {
        if(score == 0) {
            lblUpdate.setText("Winner! Score = [ 0 ]!");
        } else {
            lblUpdate.setText("Game over! Score = " + score);
            if(prevPlayerResult!=45){
                if(score<prevPlayerResult)
                    lblGameResult.setText("Winner: player 2!");
                else if(score==prevPlayerResult)
                    lblGameResult.setText("Tie!");
                else
                    lblGameResult.setText("Winner: player 1!");
                justFinished=true;
            }
        }
        btnRollOne.setEnabled(false);
        btnRollTwo.setEnabled(false);
        for(int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(false);
        }
        prevPlayerResult=45;
    }
    
    public void resetBoard() {
        board.resetDiceTotal();
        lblUpdate.setText("");
        btnRollOne.setEnabled(false);
        btnRollTwo.setEnabled(true);
        lblDice1.setIcon(faces[0]);
        lblDice2.setIcon(faces[0]);
        for(int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(true);
            board.getSquares()[i].setSquareHit(false);
            board.getSquares()[i].setBackground(null);
            buttons[i].setBackground(board.getSquares()[i].getColor());
        }
    }
}