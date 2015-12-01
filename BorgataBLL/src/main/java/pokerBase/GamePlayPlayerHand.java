package pokerBase;

public class GamePlayPlayerHand {

	private GamePlay game = null;
	private Player player = null;
	private Hand besthand = null;
	private Hand hand = null;
	
	
	private Player WinningPlayer = null;
	
	public GamePlayPlayerHand() {
	
	}

	public GamePlay getGame() {
		return game;
	}

	public void setGame(GamePlay game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public void addCardToHand( Card c)
	{
		getHand().AddCardToHand(c);
	}
	
	public Hand getBestHand() {
		return besthand;
	}

	public void setBestHand(Hand BestHand) {
		besthand = BestHand;
	}
	
	public Player getWinningPlayer() {
		return WinningPlayer;
	}

	public void setWinningPlayer(Player winningPlayer) {
		WinningPlayer = winningPlayer;
	}

	
}
