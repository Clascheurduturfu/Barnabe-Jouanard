package pkg_commands;
import pkg_engine.GameEngine;
import pkg_entities.Trainer;
import pkg_entities.Room;


/**
 * Décrivez votre classe BattleCommand ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class BattleCommand extends Command
{
    private boolean aIsTurn;
    public BattleCommand()
    {
        this.aIsTurn = true;
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (!hasSecondWord()) {
            // List trainers in current room
            gameEngine.getGui().println("Trainers in this room:");
            java.util.ArrayList<String> vTrainerNames = gameEngine.getPlayer().getCurrentRoom().getTrainerNames();

            if (vTrainerNames.isEmpty()) {
                gameEngine.getGui().println("No trainers here!");
            } else {
                for (String vName : vTrainerNames) {
                    gameEngine.getGui().println("- " + vName);
                }
                gameEngine.getGui().println("\nUse: battle <trainer_name>");
            }
            return false;
        }

        String vTrainerName = getSecondWord();

        // Check if trainer is in current room
        Room vCurrentRoom = gameEngine.getPlayer().getCurrentRoom();
        java.util.HashMap<String, Trainer> vTrainers = vCurrentRoom.getAllTrainers();
        Trainer vTrainer = vTrainers.get(vTrainerName);

        if (vTrainer == null) {
            gameEngine.getGui().println("There is no trainer by that name here!");
            return false;
        }

        if (vTrainer.isDefeated()) {
            gameEngine.getGui().println(vTrainerName + " has already been defeated!");
            return false;
        }

        gameEngine.getBattleManager().startBattle(vTrainer);

        return false;
    }

    public boolean isTurn() {
        return this.aIsTurn;
    }


}