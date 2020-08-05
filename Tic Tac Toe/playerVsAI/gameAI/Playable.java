package gameAI;

public interface Playable {

	public int playNextMove(Table table,int currentPlay); // we need to return int to increment current play, plus if we return bigger 
														  // number than 9, then player typed resign
	public String getName(); // it is a getter but i need it so i can use it when i do not know which object it is

	public Symbol getSymbol();
	
}
