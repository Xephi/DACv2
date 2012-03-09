package fr.aumgn.dac.api.game.mode;

import java.util.Collection;
import java.util.Set;

/**
 * Class responsible for managing registered {@link GameMode}.
 */
public interface GameModes {

    /**
     * Gets the game modes which are flagged as default.
     * @see DACGameMode#isDefault()
     */
    Set<String> getDefaults();

    /**
     * Gets a collection of all available game modes names.
     * This does not include aliases.
     */
    Collection<String> getNames();

    /**
     * Gets the {@link GameMode} for the given name or alias.  
     */
    GameMode get(String name);

}
