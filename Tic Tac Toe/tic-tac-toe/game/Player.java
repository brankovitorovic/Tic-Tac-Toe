package game;

public class Player implements Playable {

	
	private String name;
	private Symbol symbol;

	public Player(String name, Symbol symbol) {
		super();
		this.name = name;
		this.symbol = symbol;
	}

	@Override
	public int playNextMove(Table table,int currentPlay) {
		while(true){
			System.out.println("Player " + this.name + " choose your coordinates:");
			
			String tmp = PlayGame.sc.nextLine();
			
			if(tmp.equals("resign")){ // if player wants to quit current game
				return 11 + currentPlay; // it will add 11 to notify PlayGame class that this player gave up
			}
			
			if(!table.checkFormat(tmp)){
				System.out.println("Invalid input: you must enter the x and y coordinates separated by spaces");
				continue;
			}
			if(!table.checkCoordinates(tmp)){
				System.out.println("Invalid input: those coordinates are outside the playable area");
				continue;
			}
			if(!table.checkOccupancy(tmp)){
				System.out.println("Invalid input: that space is already taken");
				continue;
			}
			
			table.makeMove(tmp, this.symbol.toString());
			
			break;
			
		}
			
		return ++currentPlay;
	}
	
	// GETTERS AND SETTERS
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}
	
}
