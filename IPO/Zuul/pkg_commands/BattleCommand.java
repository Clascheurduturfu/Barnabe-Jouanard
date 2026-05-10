package pkg_commands;

import pkg_engine.GameEngine;
import pkg_entities.Room;
import pkg_entities.Trainer;

import java.util.HashMap;

/**
 * Starts a trainer battle, or lists available trainers when no trainer name is provided.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class BattleCommand extends Command {
    /** Indicates whether the battle command is currently in a player turn. */
    private boolean aIsTurn;

    /** Creates a battle command ready for the player's turn. */
    public BattleCommand() {
        this.aIsTurn = true;
    } // BattleCommand()

    /**
     * Executes the battle command.
     *
     * @param pGameEngine the game engine containing the current room and battle manager
     * @return always {@code false}; battles do not close the application directly
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (!this.hasSecondWord()) {
            pGameEngine.getGui().println("\nUse: battle <trainer_name>");
            return false;
        }

        String vTrainerName = this.getSecondWord();
        Room vCurrentRoom = pGameEngine.getPlayer().getCurrentRoom();
        HashMap<String, Trainer> vTrainers = vCurrentRoom.getAllTrainers();
        Trainer vTrainer = vTrainers.get(vTrainerName);

        if (vTrainer == null) {
            pGameEngine.getGui().println("There is no trainer by that name here!");
            return false;
        }

        if (vTrainer.isDefeated()) {
            pGameEngine.getGui().println(vTrainerName + " has already been defeated!");
            return false;
        }

        if (vTrainerName.equals("Rayquaza") && pGameEngine.getPlayer().getItem("delta-orb") == null) {
            pGameEngine.getGui().println("You need the Delta Orb to battle Rayquaza! Find it and come back!");
            return false;
        }

        pGameEngine.getBattleManager().startBattle(vTrainer);
        return false;
    } // execute()

    /**
     * Indicates whether the command is in a player turn.
     *
     * @return {@code true} when it is the player's turn
     */
    public boolean isTurn() {
        return this.aIsTurn;
    } // isTurn()
}
