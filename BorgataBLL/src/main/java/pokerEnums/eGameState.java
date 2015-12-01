package pokerEnums;

public enum eGameState {
	StartGame(1),
	PlayGame(2),
	EndGame(3),
	DrawingCard(4);
	
	private int gameState;

	private eGameState(final int gameState){
		this.gameState = gameState;
	}
	
	public int getgameState(){
		return gameState;
	}
}