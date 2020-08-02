package gameAI;

import java.util.Scanner;

public class PlayGame {

	private static Scanner sc = new Scanner(System.in);
	
	private Table table;
	private String player;
	private int currentPlayer; // every odd number will be AI, and even number will be player, plus we can keep track which round is
	private int winner; // 0 no winner, 1 X symbol is winner, 2 O is winner
	private int[] lastMovePlayer; // we used it just to track because of algorithm
	
	public PlayGame(){
		this.table = new Table();
		this.currentPlayer = 1;
		this.winner = 0;
		this.lastMovePlayer = new int[2];
	}
	
	public void start(){
		
		System.out.print("Enter player O name: ");

		this.player = sc.nextLine();
		
		System.out.println("Hello player: " + this.player);
		
		this.table.printTable();
		
		Symbol s = Symbol.X;
		
		while( this.winner == 0 ){
			
			if(this.currentPlayer % 2 == 1){
				nextMoveAI();
			}else{
				nextMovePlayer();
			}
		
			this.table.printTable();
			
			if(currentPlayer > 9){ // it is draw
				break;
			}
			
			if(currentPlayer % 2 == 0){
				s = Symbol.X;
			}else{
				s = Symbol.O;
			}

			int[] tmp = this.table.possibilityToWin(s.toString(), 3); // we put in parametar 3 for the win and 
																	  //symbol that is possible to win, after O player,
																	  // it checks only for him, and vice versa for X symbol
			
			if(tmp[0] == 0){
				winner = this.currentPlayer;
			}
			
		}
		
		if(currentPlayer > 9){
			System.out.println("It is a draw game!");
		}else if(this.winner % 2 == 1){
			System.out.println("----------Player " + this.player + " won!----------");
		}else{
			System.out.println("----------Computer won!----------");
		}
		
		restartGame();
		
	}
	
	public void nextMoveAI(){
		
		System.out.println("It is computer turn...");
		try { // to simulated thinking of computer
			Thread.sleep(1500); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String[] corners = {"1 1", "3 1", "1 3", "3 3"};
		
		if(this.table.checkOccupancy("2 2")){
			this.table.makeMove("2 2", "X");
			this.currentPlayer++;
			return;
		}
		
		if(this.currentPlayer == 3){ // this is second play for AI, we are searching for furthest corner from last move of player
			
			
			if( (this.lastMovePlayer[0] == 1 && this.lastMovePlayer[1] == 1) ) { // corner upper left
				this.table.makeMove("3 3", Symbol.X.toString());
				this.currentPlayer++;
				return;
			}
			if( (this.lastMovePlayer[0] == 3 && this.lastMovePlayer[1] == 3) ) {	// corner down right
				this.table.makeMove("1 1", Symbol.X.toString());
				this.currentPlayer++;
				return;
			}
			for(int i = 0; i < 4; i++){
				int[] tmp = this.table.getCoordinatesFromString(corners[i]);
				if(tmp[0] != this.lastMovePlayer[0] && tmp[1] != this.lastMovePlayer[1]){
					this.table.makeMove(corners[i], Symbol.X.toString());
					this.currentPlayer++;
					return;
				}	
			}
			
		}
	
		
		
		if(this.currentPlayer == 5){ // this is a third move by AI, we also need to check if there is chance to win now
		
			if(tryToWin(Symbol.X.toString())){
				return;
			}
			
			if(tryToWin(Symbol.O.toString())){
				return;
			}
			
			for(int i = 0; i < 4; i++){ // if not, then we search for furthest corner again
				int[] tmp = this.table.getCoordinatesFromString(corners[i]);
				if(tmp[0] != this.lastMovePlayer[0] && tmp[1] != this.lastMovePlayer[1]){
					this.table.makeMove(corners[i], Symbol.X.toString());
					this.currentPlayer++;
					return;
				}	
			}
			
		}
		// after three moves, we need to check always to win and to block opponent
		if(tryToWin(Symbol.X.toString())){
			return;
		}
		
		if(tryToWin(Symbol.O.toString())){
			return;
		}
		
		for(int i = 0; i < 4; i++){ // this is the move after which we can guarantee it will be draw
			if(this.table.checkOccupancy(corners[i])){
				this.table.makeMove(corners[i], Symbol.X.toString());
				this.currentPlayer = 10; // set play to be more than possible, to know that it is draw
			}
			
		}
		
		this.currentPlayer++; // notify that AI is done with the move
	}
	
	public void nextMovePlayer(){
		
		while(true){
			System.out.println("Player " + this.player + " choose your coordinates:");
			
			String tmp = sc.nextLine();
			
			if(tmp.equals("resign")){ // if player wants to quit current game
				this.winner = 2;
				this.currentPlayer++;
				return;
			}
			
			if(!this.table.checkFormat(tmp)){
				System.out.println("Invalid input: you must enter the x and y coordinates separated by spaces");
				continue;
			}
			if(!this.table.checkCoordinates(tmp)){
				System.out.println("Invalid input: those coordinates are outside the playable area");
				continue;
			}
			if(!this.table.checkOccupancy(tmp)){
				System.out.println("Invalid input: that space is already taken");
				continue;
			}
			
			this.lastMovePlayer = this.table.getCoordinatesFromString(tmp);
			this.table.makeMove(tmp, "O");
			break;
			
		}
		
		this.currentPlayer++;
		
	}
	
	public boolean tryToWin(String s){
		// this is how you try to win
		int[] tmp = this.table.possibilityToWin(s, 2); 
		if(tmp[0] > 0){ // if it is not 0, then we can win, lets do it, or player can win so we need to block
			this.table.makeMove(tmp[0] + " " + tmp[1], Symbol.X.toString());
			this.currentPlayer++;
			return true;
		}
		return false;
	}
	
	public void restartGame(){
		System.out.println("Restart game ? Y or N");

		while (true) {
			String answer = sc.nextLine();

			if (answer.equals("Y")) {
				this.currentPlayer = 1;
				this.winner = 0;
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
