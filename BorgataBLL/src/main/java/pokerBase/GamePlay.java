package pokerBase;

import java.util.ArrayList;
import java.util.UUID;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import domain.DeckDomainModel;
import domain.GamePlayDomainModel;
import domain.RuleDomainModel;

public class GamePlay extends GamePlayDomainModel {
	private ArrayList<GamePlayPlayerHand> CommonHand = new ArrayList<GamePlayPlayerHand>();
	private ArrayList<Player> GamePlayers = new ArrayList<Player>();
	private ArrayList<GamePlayPlayerHand> GamePlayerHand = new ArrayList<GamePlayPlayerHand>();
	private Rule RLE;
	private Deck GameDeck = null;
	
	public GamePlay(Rule rle)
	{
		this.setGameID(UUID.randomUUID());
		this.setNbrOfCards(RLE.GetNumberOfCards());
		this.setMaxNbrOfPlayers(RLE.GetMaxNumberOfPlayers());
		this.setNbrOfJokers(RLE.GetNumberOfJokers());
		this.setWildCards(RLE.GetRuleCards());
	}

	public ArrayList<Player> getGamePlayers() {
		return GamePlayers;
	}

	public Rule getRule()
	{
		return this.RLE;
	}
	
	public void setGamePlayers(ArrayList<Player> gamePlayers) {
		GamePlayers = gamePlayers;
	}
	
	public void addPlayerToGame(Player p)
	{
		GamePlayers.add(p);
	}

	public Deck getGameDeck() {
		return GameDeck;
	}

	public void setGameDeck(Deck gameDeck) {
		GameDeck = gameDeck;
	}
	
	public void addGamePlayCommonHand(GamePlayPlayerHand GPCH)
	{
		CommonHand.add(GPCH);
	}
	
	public void addGamePlayPlayerHand(GamePlayPlayerHand GPPH)
	{
		GamePlayerHand.add(GPPH);
	}
	
	
	public GamePlayPlayerHand FindPlayerGame(GamePlay gme, Player p)
	{
		GamePlayPlayerHand GPPHReturn = null;
		
		for (GamePlayPlayerHand GPPH: GamePlayerHand)
		{
			if (p.getiPlayerPosition() == GPPH.getPlayer().getiPlayerPosition())
			{
				GPPHReturn = GPPH;
			}
		}
		return GPPHReturn;
	}
	
	public GamePlayPlayerHand FindCommonHand(GamePlay gme)
	{
		GamePlayPlayerHand GPCH = null;
		for (GamePlayPlayerHand GPPH: CommonHand)
		{
			if (GPPH.getGame().getGameID() == gme.getGameID())
			{
				GPCH = GPPH;
			}
		}		
		return GPCH;
	}
	
	
}
