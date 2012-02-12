package fr.aumgn.dac.game.mode.training;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.SimpleGamePlayer;
import fr.aumgn.dac.player.DACPlayer;

public class TrainingGamePlayer extends SimpleGamePlayer {

	private boolean playing = true;
	private int successes = 0;
	private int dacs = 0;
	private int fails = 0;
	
	public TrainingGamePlayer(Game stage, DACPlayer player, int index) {
		super(stage, player, index);
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	
	public int getSuccesses() {
		return successes;
	}

	public void incrementSuccesses() {
		this.successes++;
	}

	public int getDACs() {
		return dacs;
	}

	public void incrementDACs() {
		this.dacs++;
	}

	public int getFails() {
		return fails;
	}

	public void incrementFails() {
		this.fails++;
	}

}