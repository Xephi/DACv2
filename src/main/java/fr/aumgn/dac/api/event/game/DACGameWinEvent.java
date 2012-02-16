package fr.aumgn.dac.api.event.game;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StagePlayer;

public class DACGameWinEvent extends DACGamePlayerEvent {

	private static final long serialVersionUID = 1L;
	private static final HandlerList handlers = new HandlerList();

	public DACGameWinEvent(Game<?> game, StagePlayer player) {
		super("DACClassicWinEvent", game, player);
	}
	
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}