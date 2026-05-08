package pkg_commands;

import pkg_engine.GameEngine;

import pkg_entities.Room;
import pkg_entities.Trainer;

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
    }

    /**
     * Executes the battle command.
     *
     * @param pGameEngine the game engine containing the current room and battle manager
     * @return always {@code false}; battles do not close the application directly
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (!this.hasSecondWord()) {
            pGameEngine.getGui().println("Trainers in this room:");
            final java.util.ArrayList<String> vTrainerNames =
                    pGameEngine.getPlayer().getCurrentRoom().getTrainerNames();

            if (vTrainerNames.isEmpty()) {
                pGameEngine.getGui().println("No trainers here!");
            } else {
                for (final String vName : vTrainerNames) {
                    pGameEngine.getGui().println("- " + vName);
                }
                pGameEngine.getGui().println("\nUse: battle <trainer_name>");
            }
            return false;
        }

        final String vTrainerName = this.getSecondWord();
        final Room vCurrentRoom = pGameEngine.getPlayer().getCurrentRoom();
        final java.util.HashMap<String, Trainer> vTrainers = vCurrentRoom.getAllTrainers();
        final Trainer vTrainer = vTrainers.get(vTrainerName);

        if (vTrainer == null) {
            pGameEngine.getGui().println("There is no trainer by that name here!");
            return false;
        }

        if (vTrainer.isDefeated()) {
            pGameEngine.getGui().println(vTrainerName + " has already been defeated!");
            return false;
        }

        if (vTrainerName.equals("Rayquaza")
                && pGameEngine.getPlayer().getItem("delta-orb") == null) {
            pGameEngine
                    .getGui()
                    .println("You need the Delta Orb to battle Rayquaza! Find it and come back!");
            return false;
        }

        pGameEngine.getBattleManager().startBattle(vTrainer);
        return false;
    }

    /**
     * Indicates whether the command is in a player turn.
     *
     * @return {@code true} when it is the player's turn
     */
    public boolean isTurn() {
        return this.aIsTurn;
    }
}
