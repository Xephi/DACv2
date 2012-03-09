package fr.aumgn.dac.api.config;

import org.bukkit.configuration.Configuration;

/**
 * Represents user defined configuration.  
 */
public interface DACConfig {

    void load(Configuration config);

    boolean getResetOnStart();

    boolean getResetOnEnd();

    boolean getCancelJumpDamage();

    int getSafeRegionHeight();

    int getSafeRegionMargin();

    int getTurnTimeOut();

    boolean getTpAfterJump();

    boolean getTpAfterFail();

    int getTpAfterFailDelay();

    /**
     * Gets the maximum number of player allowed in a game. 
     * <p/>
     * This value is given by the number of colors available.
     */
    int getMaxPlayers();

    String getDeathSignFirstLine();

    DACColors getColors();

}