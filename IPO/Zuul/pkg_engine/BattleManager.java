package pkg_engine;

import pkg_entities.Pokemon;
import pkg_entities.Trainer;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/** Manages battle state and turn processing. */
public class BattleManager {
    /** Game engine used to access player and world state. */
    private GameEngine aEngine;

    /** User interface used to display battle updates. */
    private UserInterface aGui;

    /** Current trainer opponent. */
    private Trainer aOpponent;

    /** Active player Pokemon in battle. */
    private Pokemon aPlayerPokemon;

    /** Active opponent Pokemon in battle. */
    private Pokemon aOpponentPokemon;

    /** Current HP of the active player Pokemon. */
    private int aPlayerCurrentHP;

    /** Current HP of the active opponent Pokemon. */
    private int aOpponentCurrentHP;

    /** Indicates whether a battle is currently active. */
    private boolean aBattleActive;

    /** Number of player Pokemon left that can battle. */
    private int aPlayerPokemonLeft;

    /** Number of opponent Pokemon left that can battle. */
    private int aOpponentPokemonLeft;

    /** Team slot index of the active player Pokemon. */
    private int aPlayerPokemonIndex;

    /** Team slot index of the active opponent Pokemon. */
    private int aOpponentPokemonIndex;

    /**
     * Creates a battle manager attached to the engine and UI.
     *
     * @param pEngine the game engine that owns player and world state
     * @param pGui the user interface used for battle output
     */
    public BattleManager(final GameEngine pEngine, final UserInterface pGui) {
        this.aEngine = pEngine;
        this.aGui = pGui;
        this.aBattleActive = false;
    } // BattleManager()

    /**
     * Starts a battle with the given trainer.
     *
     * @param pOpponent the trainer the player will battle
     */
    public void startBattle(final Trainer pOpponent) {
        this.aOpponent = pOpponent;

        // In test mode, auto-win the battle
        if (this.aEngine.isTest()) {
            this.aGui.println("\n[TEST] Auto-won battle against " + pOpponent.getName() + "!");
            pOpponent.setDefeated(true);
            if (pOpponent.getName().equals("Rayquaza")) {
                this.aEngine.getPlayer().setHasWon(true);
                this.aGui.println("Congratulations! You defeated Rayquaza and saved Hoenn!");
            }
            return;
        }

        this.aPlayerPokemonIndex = 1;
        this.aOpponentPokemonIndex = 1;
        this.aPlayerPokemon = this.aEngine.getPlayer().getPokemon(this.aPlayerPokemonIndex);
        this.aOpponentPokemon = pOpponent.getPokemon(this.aOpponentPokemonIndex);

        if (this.aPlayerPokemon == null || this.aOpponentPokemon == null) {
            this.aGui.println("Error: Missing Pokemon!");
            return;
        }

        this.aPlayerCurrentHP = this.aPlayerPokemon.getHp();
        this.aOpponentCurrentHP = this.aOpponentPokemon.getHp();
        this.aPlayerPokemonLeft = this.aEngine.getPlayer().getTeamSize();
        this.aOpponentPokemonLeft = pOpponent.getTeamSize();
        this.aBattleActive = true;

        // Show trainer images first, buttons disabled
        this.aGui.startBattle();
        this.aGui.showPlayerPokemonImage("trainer_battle.png");
        this.aGui.showOpponentPokemonImage(pOpponent.getName() + "_battle.png");
        this.updateHP();
        this.setupAttackButtons();
        this.setButtonsEnabled(false);
        this.aGui.println("\n" + pOpponent.getName() + " wants to battle!");

        // Wait 2s, then switch to pokemon solo images and enable buttons
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            SwingUtilities.invokeLater(() -> {
                this.aGui.showPlayerPokemonImage(this.aPlayerPokemon.getName() + " solo.png");
                this.aGui.showOpponentPokemonImage(this.aOpponentPokemon.getName() + " solo.png");
                this.setButtonsEnabled(true);
            });
        }).start();
    } // startBattle()

    /**
     * Refreshes both HP bars from current and maximum values.
     */
    private void updateHP() {
        this.aGui.setPlayerHP(this.aPlayerCurrentHP, this.aPlayerPokemon.getHp());
        this.aGui.setOpponentHP(this.aOpponentCurrentHP, this.aOpponentPokemon.getHp());
    } // updateHP()

    /**
     * Removes all action listeners from a button before re-binding battle actions.
     *
     * @param pButton the button to clear
     */
    private void clearListeners(JButton pButton) {
        for (java.awt.event.ActionListener al : pButton.getActionListeners()) {
            pButton.removeActionListener(al);
        }
    } // clearListeners()

    /**
     * Binds move and run actions to battle buttons for the active player Pokemon.
     */
    private void setupAttackButtons() {
        this.clearListeners(this.aGui.getAttack1Button());
        this.clearListeners(this.aGui.getAttack2Button());
        this.clearListeners(this.aGui.getAttack3Button());
        this.clearListeners(this.aGui.getAttack4Button());
        this.clearListeners(this.aGui.getRunButton());

        this.aGui.getAttack1Button().setEnabled(true);
        this.aGui.getAttack2Button().setEnabled(true);
        this.aGui.getAttack3Button().setEnabled(true);
        this.aGui.getAttack4Button().setEnabled(true);
        this.aGui.getRunButton().setEnabled(true);

        HashMap<String, pkg_entities.Attack> vMoves = this.aPlayerPokemon.getMoves();
        ArrayList<String> vMoveNames = new ArrayList<>(vMoves.keySet());

        if (vMoveNames.size() > 0) {
            this.aGui.getAttack1Button().setText(vMoveNames.get(0));
            this.aGui.getAttack1Button().addActionListener(e -> this.playerTurn(vMoveNames.get(0)));
        }
        if (vMoveNames.size() > 1) {
            this.aGui.getAttack2Button().setText(vMoveNames.get(1));
            this.aGui.getAttack2Button().addActionListener(e -> this.playerTurn(vMoveNames.get(1)));
        } else {
            this.aGui.getAttack2Button().setEnabled(false);
        }
        if (vMoveNames.size() > 2) {
            this.aGui.getAttack3Button().setText(vMoveNames.get(2));
            this.aGui.getAttack3Button().addActionListener(e -> this.playerTurn(vMoveNames.get(2)));
        } else {
            this.aGui.getAttack3Button().setEnabled(false);
        }
        if (vMoveNames.size() > 3) {
            this.aGui.getAttack4Button().setText(vMoveNames.get(3));
            this.aGui.getAttack4Button().addActionListener(e -> this.playerTurn(vMoveNames.get(3)));
        } else {
            this.aGui.getAttack4Button().setEnabled(false);
        }

        this.aGui.getRunButton().addActionListener(e -> this.runAway());
    } // setupAttackButtons()

    /**
     * Resolves one full player turn, including the opponent response and KO checks.
     *
     * @param pMoveName selected player move name
     */
    private void playerTurn(String pMoveName) {
        if (!this.aBattleActive) {
            return;
        }

        this.setButtonsEnabled(false);

        HashMap<String, pkg_entities.Attack> vPlayerMoves = this.aPlayerPokemon.getMoves();
        pkg_entities.Attack vAttack = vPlayerMoves.get(pMoveName);

        if (vAttack == null) {
            this.setButtonsEnabled(true);
            return;
        }

        int vDamage = vAttack.getDamage();

        // Player attacks - show damage gifs
        if (vDamage == 0) {
            this.aGui.println("\n" + this.aPlayerPokemon.getName() + " used " + pMoveName + "! But it missed!");
        } else {
            this.aGui.println("\n" + this.aPlayerPokemon.getName() + " used " + pMoveName + "! Dealt " + vDamage + " damage!");
        }
        this.aGui.showOpponentPokemonImage(this.aOpponentPokemon.getName() + " dammage.gif");
        this.aOpponentCurrentHP -= vDamage;
        if (this.aOpponentCurrentHP < 0) this.aOpponentCurrentHP = 0;
        this.updateHP();

        // Wait 2s for animation, then process result
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            SwingUtilities.invokeLater(() -> {
                // Check if opponent fainted
                if (this.aOpponentCurrentHP <= 0) {
                    this.aGui.println(this.aOpponentPokemon.getName() + " fainted!");
                    this.aOpponentPokemonLeft--;

                    if (this.aOpponentPokemonLeft <= 0) {
                        this.endBattle(true);
                    } else {
                        this.aOpponentPokemonIndex++;
                        this.aOpponentPokemon = this.aOpponent.getPokemon(this.aOpponentPokemonIndex);
                        this.aOpponentCurrentHP = this.aOpponentPokemon.getHp();
                        this.aGui.println(this.aOpponent.getName() + " sent out " + this.aOpponentPokemon.getName() + "!");
                        this.aGui.showOpponentPokemonImage(this.aOpponentPokemon.getName() + " solo.png");
                        this.aGui.showPlayerPokemonImage(this.aPlayerPokemon.getName() + " solo.png");
                        this.updateHP();
                        this.delayThenEnable();
                    }
                    return;
                }

                // Opponent attacks back - pick random move
                HashMap<String, pkg_entities.Attack> vOpponentMoves =
                        this.aOpponentPokemon.getMoves();
                if (vOpponentMoves.size() > 0) {
                    ArrayList<String> vOpponentMoveNames = new ArrayList<>(vOpponentMoves.keySet());
                    Random vRandom = new Random();
                    String vOpponentMoveName = vOpponentMoveNames.get(vRandom.nextInt(vOpponentMoveNames.size()));
                    int vOpponentDamage = vOpponentMoves.get(vOpponentMoveName).getDamage();

                    if (vOpponentDamage == 0) {
                        this.aGui.println(this.aOpponentPokemon.getName() + " used " + vOpponentMoveName + "! But it missed!");
                    } else {
                        this.aGui.println(this.aOpponentPokemon.getName() + " used " + vOpponentMoveName + "! Dealt " + vOpponentDamage + " damage!");
                    }
                    this.aGui.showPlayerPokemonImage(this.aPlayerPokemon.getName() + " dammage.gif");
                    this.aPlayerCurrentHP -= vOpponentDamage;
                    if (this.aPlayerCurrentHP < 0) {
                        this.aPlayerCurrentHP = 0;
                    }
                    this.updateHP();

                    // Wait 2s for player damage animation
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e2) {
                        }
                        SwingUtilities.invokeLater(() -> {
                            if (this.aPlayerCurrentHP <= 0) {
                                this.aGui.println(this.aPlayerPokemon.getName() + " fainted!");
                                this.aPlayerPokemonLeft--;

                                if (this.aPlayerPokemonLeft <= 0) {
                                    this.endBattle(false);
                                } else {
                                    this.aPlayerPokemonIndex++;
                                    this.aPlayerPokemon = this.aEngine.getPlayer().getPokemon(this.aPlayerPokemonIndex);
                                    this.aPlayerCurrentHP = this.aPlayerPokemon.getHp();
                                    this.aGui.println("Go, " + this.aPlayerPokemon.getName() + "!");
                                    this.aGui.showPlayerPokemonImage(this.aPlayerPokemon.getName() + " solo.png");
                                    this.aGui.showOpponentPokemonImage(this.aOpponentPokemon.getName() + " solo.png");
                                    this.updateHP();
                                    this.setupAttackButtons();
                                    this.delayThenEnable();
                                }
                            } else {
                                // Both alive - back to solo images, enable buttons
                                this.aGui.showPlayerPokemonImage(this.aPlayerPokemon.getName() + " solo.png");
                                this.aGui.showOpponentPokemonImage(this.aOpponentPokemon.getName() + " solo.png");
                                this.setButtonsEnabled(true);
                            }
                        });
                    }).start();
                } else {
                    // Opponent has no moves
                    this.aGui.showOpponentPokemonImage(this.aOpponentPokemon.getName() + " solo.png");
                    this.setButtonsEnabled(true);
                }
            });
        }).start();
    } // playerTurn()

    /**
     * Waits briefly, then re-enables battle buttons if battle is still active.
     */
    private void delayThenEnable() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            SwingUtilities.invokeLater(() -> {
                if (this.aBattleActive) {
                    this.setButtonsEnabled(true);
                }
            });
        }).start();
    } // delayThenEnable()

    /**
     * Enables or disables battle action buttons based on move count and state.
     *
     * @param pEnabled {@code true} to enable allowed actions
     */
    private void setButtonsEnabled(boolean pEnabled) {
        int vMoveCount = this.aPlayerPokemon.getMoves().size();
        this.aGui.getAttack1Button().setEnabled(pEnabled);
        this.aGui.getAttack2Button().setEnabled(pEnabled && vMoveCount > 1);
        this.aGui.getAttack3Button().setEnabled(pEnabled && vMoveCount > 2);
        this.aGui.getAttack4Button().setEnabled(pEnabled && vMoveCount > 3);
        this.aGui.getRunButton().setEnabled(pEnabled);
    } // setButtonsEnabled()

    /**
     * Finalizes a battle, shows outcome text, and returns to exploration mode.
     *
     * @param pPlayerWon {@code true} if the player won
     */
    private void endBattle(boolean pPlayerWon) {
        this.aBattleActive = false;
        this.setButtonsEnabled(false);

        if (pPlayerWon) {
            this.aGui.println("\nYou won the battle!");
            this.aOpponent.setDefeated(true);
            if (this.aOpponent.getName().equals("Rayquaza")) {
                this.aEngine.getPlayer().setHasWon(true);
                this.aGui.println("Congratulations! You defeated Rayquaza and saved Hoenn!");
            }
        } else {
            this.aGui.println("\nYou lost the battle!");
        }

        // Show trainer images again
        this.aGui.showPlayerPokemonImage("trainer_battle.png");
        this.aGui.showOpponentPokemonImage(this.aOpponent.getName() + "_battle.png");

        // Wait then return to exploration
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            SwingUtilities.invokeLater(() -> {
                this.aGui.endBattle();
                this.aGui.println(this.aEngine.getPlayer().getCurrentRoom().getLongDescription());
            });
        }).start();
    } // endBattle()

    /**
     * Ends the current battle as a run-away action and returns to exploration.
     */
    private void runAway() {
        this.aGui.println("\nYou ran away!");
        this.aBattleActive = false;
        this.setButtonsEnabled(false);

        // Show trainer images again
        this.aGui.showPlayerPokemonImage("trainer_battle.png");
        this.aGui.showOpponentPokemonImage(this.aOpponent.getName() + "_battle.png");

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            SwingUtilities.invokeLater(() -> {
                this.aGui.endBattle();
                this.aGui.println(this.aEngine.getPlayer().getCurrentRoom().getLongDescription());
            });
        }).start();
    } // runAway()
}
