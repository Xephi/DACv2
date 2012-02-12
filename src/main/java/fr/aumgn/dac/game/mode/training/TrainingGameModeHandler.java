package fr.aumgn.dac.game.mode.training;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.arenas.Pool;
import fr.aumgn.dac.arenas.column.DACGlassColumn;
import fr.aumgn.dac.arenas.column.DACSimpleColumn;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.mode.SimpleGameModeHandler;
import fr.aumgn.dac.player.DACPlayer;

public class TrainingGameModeHandler extends SimpleGameModeHandler {

	private Game game;
	private DACArena arena;

	public TrainingGameModeHandler(Game game) {
		this.game = game;
		this.arena = game.getArena();
	}
	
	@Override
	public void onStart() {
		if (DAC.getConfig().getResetOnStart()) {
			arena.getPool().reset();
		}
		game.send(DACMessage.GameStart);
	}

	@Override
	public void onTurn(DACPlayer dacPlayer) {
		TrainingGamePlayer player = (TrainingGamePlayer)dacPlayer;
		if (!player.isPlaying()) {
			game.nextTurn();
		} else {
			game.send(DACMessage.GamePlayerTurn.format(player.getDisplayName()), player);
			player.tpToDiving();
		}
	}

	@Override
	public void onSuccess(DACPlayer dacPlayer) {
		TrainingGamePlayer player = (TrainingGamePlayer)dacPlayer;
		game.send(DACMessage.GameJumpSuccess.format(player.getDisplayName()), player);
		Location loc = player.getPlayer().getLocation();
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		player.tpToStart();
		Pool pool = arena.getPool(); 
		if (pool.isADACPattern(x, z)) {
			player.incrementDACs();
			pool.putColumn(new DACGlassColumn(), player.getColor(), x, z);
		} else {
			player.incrementSuccesses();
			pool.putColumn(new DACSimpleColumn(), player.getColor(), x, z);
		}
		game.nextTurn();
	}

	@Override
	public void onFail(DACPlayer dacPlayer) {
		TrainingGamePlayer player = (TrainingGamePlayer)dacPlayer;
		game.send(DACMessage.GameJumpFail.format(player.getDisplayName()), player);
		player.tpToStart();
		player.incrementFails();
		game.nextTurn();	
	}
	
	@Override
	public void onQuit(DACPlayer dacPlayer) {
		TrainingGamePlayer player = (TrainingGamePlayer)dacPlayer;
		player.setPlaying(false);
		int count = 0;
		for (DACPlayer gamePlayer : game.getPlayers()) {
			player = (TrainingGamePlayer)gamePlayer;
			if (player.isPlaying()) {
				count++;
			}
		}
		if (count == 0) {
			game.stop();
		}
	}
	
	@Override
	public void onStop() {
		for (DACPlayer dacPlayer : game.getPlayers()) {
			TrainingGamePlayer gamePlayer = (TrainingGamePlayer)dacPlayer;
			Player player = gamePlayer.getPlayer();
			player.sendMessage("Reussi : " + gamePlayer.getSuccesses());
			player.sendMessage("Dés a coudre : " + gamePlayer.getDACs());
			player.sendMessage("Manqués : " + gamePlayer.getFails());
		}
	}

}
