package game;

import java.util.Scanner;

public class PlayGame {

	public static Scanner sc = new Scanner(System.in);
	
	private Table table;
	private Playable playerO;
	private Playable playerX;
	private Playable currentPlayer;
	private Playable[] players;
	private int currentPlay; 
	
	public PlayGame(){
		this.table = new Table();
		this.currentPlay = 1;
		this.players = new Playable[2];
	}
	
	public void start(){
		
		System.out.print("Enter player O name: ");

		playerO = new Player(sc.nextLine(), Symbol.O);
		
		System.out.print("Enter player X name ( if you enter AI as a name, you will play against computer ): ");

		String player2Name = sc.nextLine();
		
		if(player2Name.equals("AI")){
			playerX = new PlayerAI(Symbol.X);
		}else{
			playerX = new Player(player2Name, Symbol.X);
		}
		
		this.players[0] = playerO;
		this.players[1] = playerX;
		
		this.table.printTable();
	
		this.currentPlayer = playerX;
		
		while( true ){
		
			this.currentPlay = currentPlayer.playNextMove(this.table,this.currentPlay); // this is for player and AI, it doesn't matter
			
			this.table.printTable();
			
			if(this.currentPlay > 9 ){ // it can be bigger than 9 because of two reasons: 1) it is 10. play = draw game, 2) player typed resign
				this.currentPlayer = this.players[this.currentPlay%2]; // this is because player can resign at every moment, so other one is winner
				break;
			}
			
			if(this.table.isSomeoneWin(this.currentPlayer.getSymbol().toString())){
				break;
			}
			
			this.currentPlayer = this.players[this.currentPlay%2];
			
		}
	
		if(currentPlay == 10){
			System.out.println("It is a draw game!");
		}else{
			System.out.println(this.currentPlayer.getName() + " won!");
		}
		
		restartGame();
		
	}
	
	public void restartGame(){
		System.out.println("Restart game ? Y or N");

		while (true) {
			
			String answer = sc.nextLine();

			if (answer.equals("Y")) {
				this.currentPlay = 1;
				this.table.setEmptyBoard();
				start();
				break;
			} else if (answer.equals("N")) {
				System.out.println("Thank you for playing Tic Tac Toe, see you soon!");
				break;
			} else {
				System.out.println("Enter Y for yes and N for no.");
			}
		}
	}
}
