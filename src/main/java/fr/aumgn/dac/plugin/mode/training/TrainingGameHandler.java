package fr.aumgn.dac.plugin.mode.training;

import fr.aumgn.dac.api.area.column.GlassyColumn;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.event.GameFinish;
import fr.aumgn.dac.api.game.event.GameJumpFail;
import fr.aumgn.dac.api.game.event.GameJumpSuccess;
import fr.aumgn.dac.api.game.event.GameLoose;
import fr.aumgn.dac.api.game.event.GamePoolFilled;
import fr.aumgn.dac.api.game.event.GameStart;
import fr.aumgn.dac.api.game.event.GameTurn;
import fr.aumgn.dac.api.game.mode.SimpleGameHandler;

public class TrainingGameHandler extends SimpleGameHandler {

    @Override
    public void onStart(GameStart event) {
        event.send(DACMessage.GameStart);
    }

    @Override
    public void onTurn(GameTurn event) {
        event.sendToPlayer(DACMessage.GamePlayerTurn2);
        event.sendToOthers(DACMessage.GamePlayerTurn);
    }

    @Override
    public void onSuccess(GameJumpSuccess event) {
        TrainingGamePlayer player = event.getPlayer(TrainingGamePlayer.class);
        if (event.isADAC()) {
            player.incrementDACs();
            event.setColumnPattern(new GlassyColumn(player.getColor()));
            event.sendToPlayer(DACMessage.GameDAC2);
            event.sendToOthers(DACMessage.GameDAC);
        } else {
            player.incrementSuccesses();
            event.sendToPlayer(DACMessage.GameJumpSuccess2);
            event.sendToOthers(DACMessage.GameJumpSuccess);
        }
    }

    @Override
    public void onFail(GameJumpFail event) {
        TrainingGamePlayer player = event.getPlayer(TrainingGamePlayer.class);
        player.incrementFails();
        event.sendToPlayer(DACMessage.GameJumpFail2);
        event.sendToOthers(DACMessage.GameJumpFail);
    }

    private void sendStats(TrainingGamePlayer player) {
        player.send(DACMessage.StatsSuccess.getContent(player.getSuccesses()));
        player.send(DACMessage.StatsDAC.getContent(player.getDACs()));
        player.send(DACMessage.StatsFail.getContent(player.getFails()));
    }

    @Override
    public void onLoose(GameLoose event) {
        sendStats(event.getPlayer(TrainingGamePlayer.class));
    }

    @Override
    public void onPoolFilled(GamePoolFilled event) {
        event.getArena().getPool().reset();
    }

    @Override
    public void onFinish(GameFinish event) {
        for (TrainingGamePlayer player : event.getPlayers(TrainingGamePlayer.class)) {
            sendStats(player);
        }
    }

}
