package game;

import java.util.HashMap;

public class Table {

	private String[][] table;
	private HashMap<String, int[]> placesOnBoard;
	private int[] lastMoveOnTable;
	
	public Table() {

		this.table = new String[4][4]; // make 4x4 but 0 row and 0 column will always be empty, because it makes easier to follow coordinates
		this.placesOnBoard = new HashMap<>(); // this is just for the print table 
		this.lastMoveOnTable = new int[2];
		
		setEmptyBoard();
		
		this.placesOnBoard.put("1 1", new int[] { 2, 4 });
		this.placesOnBoard.put("1 2", new int[] { 4, 4 });
		this.placesOnBoard.put("1 3", new int[] { 6, 4 });
		this.placesOnBoard.put("2 1", new int[] { 2, 8 });
		this.placesOnBoard.put("2 2", new int[] { 4, 8 });
		this.placesOnBoard.put("2 3", new int[] { 6, 8 });
		this.placesOnBoard.put("3 1", new int[] { 2, 12 });
		this.placesOnBoard.put("3 2", new int[] { 4, 12 });
		this.placesOnBoard.put("3 3", new int[] { 6, 12 });

	}

	public void makeMove(String s, String symbol){ // put symbol in string for table
		int[] c = getCoordinatesFromString(s);
		
		this.lastMoveOnTable = c;
		
		this.table[c[0]][c[1]] = symbol;
		
	}

	public boolean isSomeoneWin(String symbol){ // check if there is a winner for given symbol
		int counter = 0;

		// check rows
		
		for(int i = 1; i < 4; i++){
			for(int j = 1; j < 4; j++){
				if(this.table[j][i].equals(symbol)){
					counter++;
				}
			}
			if(counter == 3){
				return true;
			}
			counter = 0;
		}
		
		counter = 0;
		
		// check columns
		
		for(int i = 1; i < 4; i++){
			for(int j = 1; j < 4; j++){
				if(this.table[i][j].equals(symbol)){
					counter++;
				}
			}
			if(counter == 3){
				return true; 
			}
			counter = 0;
		}

		counter = 0;
		
		// check main diagonal

		for(int i = 1; i < 4; i++){
			for(int j = 1; j < 4; j++){
				if(i == j){
					if(this.table[i][j].equals(symbol)){
						counter++;
					}
				}
			}
		}

		if(counter == 3){
			return true;
		}
		
		counter = 0;
		
		for(int i = 1; i < 4; i++){
			for(int j = 1; j < 4; j++){
				if(i + j == 4){
					if(this.table[i][j].equals(symbol)){
						counter++;
					}
				}
			}
		}

		if(counter == 3){
			return true;
		}
		
		return false;
	}
	
	public int[] isTwoSymbolsAndOneEmpty(String symbol){ // if there are two same in a row and third is missing, returns coordinate on board 
											// or -1 if there is no two same symbols and one empty in row, column and any diagonal
		int counter = 0;
		int counterEmpty = 0;
		int[] emptySpace = new int[2];
		
		// check rows
		
		for(int i = 1; i < 4; i++){
			for(int j = 1; j < 4; j++){
				if(this.table[j][i].equals(symbol)){
					counter++;
				}
				if(this.table[j][i].equals(" ")){
					counterEmpty++;
					emptySpace[0] = j;
					emptySpace[1] = i;
				}
			}
			if(counter == 2 && counterEmpty == 1){ // this means there is 2 in same row and third place is empty ( same for the columns and diagonals )
				return emptySpace;
			}
			counter = 0;
			counterEmpty = 0;
			emptySpace[0] = 0;
			emptySpace[1] = 0; 
		}
		
		counter = 0;
		counterEmpty = 0;
		emptySpace[0] = 0;
		emptySpace[1] = 0;
		
		// check columns
		
		for(int i = 1; i < 4; i++){
			for(int j = 1; j < 4; j++){
				if(this.table[i][j].equals(symbol)){
					counter++;
				}
				if(this.table[i][j].equals(" ")){
					counterEmpty++;
					emptySpace[0] = i;
					emptySpace[1] = j;
				}
			}

			if(counter == 2 && counterEmpty == 1){
				return emptySpace; 
			}
			counter = 0;
			counterEmpty = 0;
			emptySpace[0] = 0;
			emptySpace[1] = 0;
		}
		
		// check main diagonal 

		counter = 0;
		counterEmpty = 0;
		emptySpace[0] = 0;
		emptySpace[1] = 0;
		
		for(int i = 1; i < 4; i++){
			for(int j = 1; j < 4; j++){
				if(i == j){
					if(this.table[i][j].equals(symbol)){
						counter++;
					}
					if(this.table[i][j].equals(" ")){
						counterEmpty++;
						emptySpace[0] = i;
						emptySpace[1] = j;
					}
				}
				
			}
			
		}

		if(counter == 2 && counterEmpty == 1){
			return emptySpace;
		}
		
		counter = 0;
		counterEmpty = 0;
		emptySpace[0] = 0;
		emptySpace[1] = 0;
		
		// check secondary diagonal
		
		for(int i = 1; i < 4; i++){
			for(int j = 1; j < 4; j++){
				if(i + j == 4){
					if(this.table[i][j].equals(symbol)){
						counter++;
					}
					if(this.table[i][j].equals(" ")){
						counterEmpty++;
						emptySpace[0] = i;
						emptySpace[1] = j;
					}
				}
			}
			
		}

		if(counter == 2 && counterEmpty == 1){
			return emptySpace;
		}
		
		emptySpace[0] = -1;
		return emptySpace;
		
	}

	public void printTable() { 

		String[][] tmp = { { " ", " ", " ", " ", " ", "1", " ", " ", " ", "2", " ", " ", " ", "3", " " },
				{ " ", " ", " ~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
				{ "1", " ", " |", " ", " ", " ", "|", " ", " ", " ", "|", " ", " ", " ", "|" },
				{ " ", " ", " ~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
				{ "2", " ", " |", " ", " ", " ", "|", " ", " ", " ", "|", " ", " ", " ", "|" },
				{ " ", " ", " ~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" },
				{ "3", " ", " |", " ", " ", " ", "|", " ", " ", " ", "|", " ", " ", " ", "|" },
				{ " ", " ", " ~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~" }

		};

		for (int i = 1; i < 4; i++) { // put symbols in table for print
			for (int j = 1; j < 4; j++) {
				int[] tmpInt = this.placesOnBoard.get(j + " " + i);
				tmp[tmpInt[0]][tmpInt[1]] = this.table[j][i];
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 15; j++) {
				System.out.print(tmp[i][j]);
			}
			System.out.println("");
		}

	}

	public boolean checkFormat(String str) { // format must be: number space
		// number

		if (str.length() != 3) { // it must be 3 character long
			return false;
		}

		if (!(str.charAt(1) == ' ')) { // second character must be space
			return false;
		}

		String[] tmp = str.split(" "); // now we know we can split with space

		try { // we check if first and third character are numbers
			Integer.parseInt(tmp[0]);
			Integer.parseInt(tmp[1]);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public boolean checkCoordinates(String c) { // check if numbers
		// are inside of
		// range
		int[] coordinates = getCoordinatesFromString(c);
		
		if (coordinates[0] < 1 || coordinates[0] > 3 || coordinates[1] < 1 || coordinates[1] > 3) {
			return false;
		}
		return true;
	}

	public boolean checkOccupancy(String coordinates) { // check if choosen
		// place is free

		int[] tmp = getCoordinatesFromString(coordinates);
		
		if(this.table[tmp[0]][tmp[1]].equals(" ")){
			return true;
		}
		return false;
		
	}

	public int[] getCoordinatesFromString(String s){
		
		int[] coordinates = new int[2];

		String[] tmp = s.split(" ");

		coordinates[0] = Integer.parseInt(tmp[0]); // we can parse int without
													// try and catch
		coordinates[1] = Integer.parseInt(tmp[1]);

		return coordinates;
		
		
	}
	
	public void setEmptyBoard(){ // for first game and restart of game
		for (int i = 0; i < 4; i++) { // set every place on empty
			for (int j = 0; j < 4; j++) {
				this.table[j][i] = " ";
			}
		}
	}
	
	public int[] getLastMoveOnTable() {
		return lastMoveOnTable;
	}
	
}
