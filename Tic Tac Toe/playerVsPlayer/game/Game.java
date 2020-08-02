package game;

import java.util.HashMap;
import java.util.Scanner;

public class Game {

	private static Scanner sc = new Scanner(System.in);
	private String player1;
	private String player2;
	private String[][] table;
	private int nextPlay;
	private HashMap<String, int[]> placesOnBoard = new HashMap<>();
	private int winner;
	
	public void start() { // before start of any game, first initialized everything, and it is in charge of the game
		initialize();
		getNamesForPlayers();
				
		System.out.println("Hello player X: " + this.player1 + " and " + "player O: " + this.player2 
				+ ", you can select a place on the board with the appropriate coordinates.");
		
		printTable();
		
		this.nextPlay = 1;
		
		boolean draw = false;
		
		while( this.winner == 0 ){
			
			nextMove();
			
			printTable();
			
			weHaveAWinner();
			
			if(this.nextPlay == 10){
				draw = true;
				break;
			}
			
		}	
		
		if(draw){
			System.out.println("Game is draw.");
		}else if(this.winner == 1){
			System.out.println("--------------WINNER IS " + this.player1 + "--------------");
		}else{
			System.out.println("--------------WINNER IS " + this.player2 + "--------------");
		}
		
		System.out.println("Restart game ? Y or N");
		
		while(true){
			String answer = sc.nextLine();
			
			if(answer.equals("Y")){
				start();
				break;
			}else if(answer.equals("N")){
				System.out.println("Thank you for playing Tic Tac Toe, see you soon!");
				break;
			}else{
				System.out.println("Enter Y for yes and N for no.");
			}
		}
			
		
	}

    private void nextMove(){ // main method to check if everything is good with coordinates and put sigh in correct place on board
    	
    	boolean doMove = false;
    	String coordinatesString = "";
    	int[] coordinatesArray = new int[2];
    	int[] tmpCoordinates = new int[2];
    	
    	while(!doMove){
    		
    		if( nextPlay % 2 == 1){ // player X
        		System.out.println("Player X: " + this.player1 + " choose place on board: ");
        	}else{ // player O
        		System.out.println("Player O: " + this.player2 + " choose place on board: ");
        	}
    		
    		coordinatesString = sc.nextLine(); // read from console
    		
    		if(coordinatesString.equals("resign")){ // if player wants to resign
    			if(this.nextPlay % 2 == 0){
    				this.winner = 1;
    			}else{
    				this.winner = 2;
    			}
    			return;
    		}
    		
    		if(!checkFormat(coordinatesString)){
    			System.out.println("Invalid input: you must enter the x and y coordinates separated by spaces");
    			continue;
    		}
    		
    		coordinatesArray = getCoordinates(coordinatesString); // get coordinates in int array
    		
    		if(!checkCoordinates(coordinatesArray)){
    			System.out.println("Invalid input: those coordinates are outside the playable area");
    			continue;
    		}
    		
    		if(!checkOccupancy(coordinatesString)){
    			System.out.println("Invalid input: that space is already taken");
    			continue;
    		}
    		
    		// coordinates are good and it is possible to do move
    		doMove = true;
    		
    	}
    	
    	tmpCoordinates = this.placesOnBoard.get(coordinatesString); // took value for coordinate on table to put X or O
    	this.placesOnBoard.remove(coordinatesString); // remove from hash to know that place is taken
    	
    	if( nextPlay % 2 == 1){ // player X
    		this.table[tmpCoordinates[0]][tmpCoordinates[1]] = "X";
    	}else{
    		this.table[tmpCoordinates[0]][tmpCoordinates[1]] = "O";
    	}
    	
    	this.nextPlay++; // increment nextPlay to know which player is on move
    	
    	
    }
    
    private int[] getCoordinates(String str){ // we now that format is good because it was checked before
    	int[] coordinates = new int[2];
    	
    	String[] tmp = str.split(" ");
    	
    	coordinates[0] = Integer.parseInt(tmp[0]); // we can parse int without try and catch 
    	coordinates[1] = Integer.parseInt(tmp[1]);
    	
    	return coordinates;
    }
    
    private boolean checkFormat(String str){ // format must be: number space number
    	
    	if(str.length() != 3){ // it must be 3 character long
    		return false;
    	}
    	
    	if(! (str.charAt(1) == ' ' ) ){ // second character must be space
    		return false;
    	}
    	
    	String[] tmp = str.split(" "); // now we know we can split with space 
    	
    	try{ // we check if first and third character are numbers
    		Integer.parseInt(tmp[0]);
    		Integer.parseInt(tmp[1]);
    	}catch (NumberFormatException e) {
			return false;
		}
    	
    	return true;
    }
    
    private boolean checkCoordinates(int[] coordinates){ // check if numbers are inside of range
    	if(coordinates[0] < 1 || coordinates[0] > 3 || coordinates[1] < 1 || coordinates[1] > 3){
    		return false;
    	}
    	return true;
    }
    
    private boolean checkOccupancy(String coordinates){ // check if choosen place is free
    	
    	if(this.placesOnBoard.get(coordinates) == null){ // if it is null, then place is taken
    		return false;
    	}
    	
    	return true;
    }
    
    private void weHaveAWinner(){ // check if there is a winner, if there is it puts number of player who won
    	
    	if(this.nextPlay < 6) return; // there is not enough moves to be possible for someone to win
    	
    	int sum = 0; // O = 79, X = 88, ASCII number for O and X character
    	
    	// check rows
    	for(int i = 2; i < 7; i+=2){
    		sum = 0;
    		for(int j = 4; j < 13; j+=4){
    			sum += (int) this.table[i][j].charAt(0); 
    		}
    		if(this.nextPlay % 2 == 0 && sum == 3 * 88){ // if it is 3 times X in a row and last move was made by player X
    			this.winner = 1;
        		return;
        	}
    		if(this.nextPlay % 2 == 1 && sum == 3 * 79){ // there is no need to check for X and O after every move, just player who was on the move
    			winner = 2;
    			return;
    		}
    	}
    	
    	// check columns
    	for(int i = 4; i < 13; i += 4){
    		sum = 0;
    		for(int j = 2; j < 7; j += 2){
    			sum += (int) this.table[j][i].charAt(0);
    		}
    		if(this.nextPlay % 2 == 0 && sum == 3 * 88){
        		this.winner = 1;
    			return;
        	}
    		if(this.nextPlay % 2 == 1 && sum == 3 * 79){
    			this.winner = 2;
    			return;
    		}
    	}
    	
    	// check main diagonal
    	sum = 0;
    	for(int i = 2; i < 7; i+=2){
    		sum += (int) this.table[i][i*2].charAt(0);
    	}
    	if(this.nextPlay % 2 == 0 && sum == 3 * 88){
    		this.winner = 1;
			return;
    	}
		if(this.nextPlay % 2 == 1 && sum == 3 * 79){
			this.winner = 2;
			return;
		}
    	
		// check secondary diagonal
		sum = 0;
		int tmp = 12;
		
		for(int i = 2; i < 7; i+=2){
			sum += (int) this.table[i][tmp].charAt(0);
			tmp -=4;
		}
		if(this.nextPlay % 2 == 0 && sum == 3 * 88){
    		this.winner = 1;
			return;
    	}
		if(this.nextPlay % 2 == 1 && sum == 3 * 79){
			this.winner = 2;
			return;
		}
		
    	return;
    }
    
    private void getNamesForPlayers() {

		System.out.print("Enter player X name: ");

		this.player1 = sc.nextLine();

		System.out.print("Enter player O name: ");

		this.player2 = sc.nextLine();

	}

    private void printTable(){
    	for(int i = 0; i < 8; i++){
    		for(int j = 0; j < 15; j++){
    			System.out.print(table[i][j]);
    		}
    		System.out.println("");
    	}
    }
	
    private void initialize(){
    			// initializing map to get coordinates on board, without 9 if statments
    			this.placesOnBoard.put("1 1", new int[]{2,4});
    			this.placesOnBoard.put("1 2", new int[]{4,4});
    			this.placesOnBoard.put("1 3", new int[]{6,4});
    			this.placesOnBoard.put("2 1", new int[]{2,8});
    			this.placesOnBoard.put("2 2", new int[]{4,8});
    			this.placesOnBoard.put("2 3", new int[]{6,8});
    			this.placesOnBoard.put("3 1", new int[]{2,12});
    			this.placesOnBoard.put("3 2", new int[]{4,12});
    			this.placesOnBoard.put("3 3", new int[]{6,12});
    			// initializing board
    			String[][] tmp = {
    					{" "," "," "," ", " ", "1", " ", " ", " ", "2", " ", " ", " ", "3", " "},
    					{" ", " ", " ~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
    					{"1", " ", " |", " ", " ", " ", "|", " ", " ", " ", "|", " ", " ", " ", "|"},
    					{" ", " ", " ~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
    					{"2", " ", " |", " ", " ", " ", "|", " ", " ", " ", "|", " ", " ", " ", "|"},
    					{" ", " ", " ~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
    					{"3", " ", " |", " ", " ", " ", "|", " ", " ", " ", "|", " ", " ", " ", "|"},
    					{" ", " ", " ~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"}
    					
    			};
    			this.table = tmp;
    			this.winner = 0;
    }
    
}
