package game;

public class PlayerAI implements Playable{

	private String name;
	private Symbol symbol;
	
	public PlayerAI(Symbol symbol){
		this.name = "Computer";
		this.symbol = symbol;
	}
	
	@Override
	public int playNextMove(Table table, int currentPlay) {

		System.out.println("It is computer turn...");
		try { // to simulated thinking of computer
			Thread.sleep(1500); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String[] corners = {"1 1", "3 1", "1 3", "3 3"};
		
		if(table.checkOccupancy("2 2")){ // first play must be always at center according to algorithm for not loosing
			table.makeMove("2 2", this.symbol.toString());
			return ++currentPlay;
		}
		
		if(currentPlay == 3){ // this is second play for AI, we are searching for furthest corner from last move of player
			
			if( (table.getLastMoveOnTable()[0] == 1 && table.getLastMoveOnTable()[1] == 1) ) { // corner upper left
				table.makeMove("3 3", this.symbol.toString());
				return ++currentPlay;
			}
			if( (table.getLastMoveOnTable()[0] == 3 && table.getLastMoveOnTable()[1] == 3) ) {	// corner down right
				table.makeMove("1 1", this.symbol.toString());
				return ++currentPlay;
			}
			for(int i = 0; i < 4; i++){ // then 
				int[] tmp = table.getCoordinatesFromString(corners[i]);
				if(tmp[0] != table.getLastMoveOnTable()[0] && tmp[1] != table.getLastMoveOnTable()[1]){
					table.makeMove(corners[i], this.symbol.toString());
					return ++currentPlay;
				}	
			}
			
		}
		
		// after three moves, we always need to check can we win and do we need to block opponent
		if(canIWin(table)){
			return ++currentPlay;
		}
		
		if(doINeedToBlock(table)){
			return ++currentPlay;
		}
		
		for(int i = 0; i < 4; i++){ // if not, then we search for furthest corner again
			int[] tmp = table.getCoordinatesFromString(corners[i]);
			if(tmp[0] != table.getLastMoveOnTable()[0] && tmp[1] != table.getLastMoveOnTable()[1] && table.checkOccupancy(corners[i])){
				table.makeMove(corners[i], this.symbol.toString());
				return ++currentPlay;
			}	
		}
		
		for(int i = 0; i < 4; i++){ // this is the move after which we can guarantee it will be draw
			if(table.checkOccupancy(corners[i])){
				table.makeMove(corners[i],this.symbol.toString());
				return 10; // set play to be 10, to know that it is draw
			}
			
		}
		
		return ++currentPlay; // notify that AI is done with the move
	
	}
	
	public boolean canIWin(Table table){ // checks if there is place to win instantly
		
		int[] tmp = table.isTwoSymbolsAndOneEmpty(this.symbol.toString()); 
		if(tmp[0] > 0){ // if it is higher than 0, there is place on table to put X and win
			table.makeMove(tmp[0] + " " + tmp[1], this.symbol.toString());
			return true;
		}
		return false;
	}
	
	public boolean doINeedToBlock(Table table){ // similar as method canIWin
		int[] tmp = table.isTwoSymbolsAndOneEmpty(Symbol.O.toString()); // we know we need to block O player
		if(tmp[0] > 0){ 
			table.makeMove(tmp[0] + " " + tmp[1], this.symbol.toString());
			return true;
		}
		return false;
	}
	
	// GETTERS
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Symbol getSymbol() {
		return this.symbol;
	}
	
}
