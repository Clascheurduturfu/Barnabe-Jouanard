package pkg_engine;

import pkg_entities.Trainer;
import pkg_entities.Pokemon;

/**
 * Manages battle state and turn processing.
 */
public class BattleManager {
    private GameEngine aEngine;
    private UserInterface aGui;
    private Trainer aOpponent;
    private Pokemon aPlayerPokemon;
    private Pokemon aOpponentPokemon;
    private int aPlayerCurrentHP;
    private int aOpponentCurrentHP;
    private boolean aBattleActive;
    private int aPlayerPokemonLeft;
    private int aOpponentPokemonLeft;
    private int aPlayerPokemonIndex;
    private int aOpponentPokemonIndex;

    public BattleManager(GameEngine pEngine, UserInterface pGui) {
        this.aEngine = pEngine;
        this.aGui = pGui;
        this.aBattleActive = false;
    }

    /**
     * Starts a battle with the given trainer.
     */
    public void startBattle(Trainer pOpponent) {
        this.aOpponent = pOpponent;
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
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            javax.swing.SwingUtilities.invokeLater(() -> {
                this.aGui.showPlayerPokemonImage(this.aPlayerPokemon.getName() + " solo.png");
                this.aGui.showOpponentPokemonImage(this.aOpponentPokemon.getName() + " solo.png");
                this.setButtonsEnabled(true);
            });
        }).start();
    }

    private void updateHP() {
        this.aGui.setPlayerHP(this.aPlayerCurrentHP, this.aPlayerPokemon.getHp());
        this.aGui.setOpponentHP(this.aOpponentCurrentHP, this.aOpponentPokemon.getHp());
    }

    private void clearListeners(javax.swing.JButton pButton) {
        for (java.awt.event.ActionListener al : pButton.getActionListeners()) {
            pButton.removeActionListener(al);
        }
    }

    private void setupAttackButtons() {
        clearListeners(this.aGui.getAttack1Button());
        clearListeners(this.aGui.getAttack2Button());
        clearListeners(this.aGui.getAttack3Button());
        clearListeners(this.aGui.getAttack4Button());
        clearListeners(this.aGui.getRunButton());

        this.aGui.getAttack1Button().setEnabled(true);
        this.aGui.getAttack2Button().setEnabled(true);
        this.aGui.getAttack3Button().setEnabled(true);
        this.aGui.getAttack4Button().setEnabled(true);
        this.aGui.getRunButton().setEnabled(true);

        java.util.HashMap<String, Integer> vMoves = this.aPlayerPokemon.getMoves();
        java.util.ArrayList<String> vMoveNames = new java.util.ArrayList<>(vMoves.keySet());

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
    }

    private void playerTurn(String pMoveName) {
        if (!this.aBattleActive) return;

        this.setButtonsEnabled(false);

        java.util.HashMap<String, Integer> vPlayerMoves = this.aPlayerPokemon.getMoves();
        Integer vDamage = vPlayerMoves.get(pMoveName);

        if (vDamage == null) {
            this.setButtonsEnabled(true);
            return;
        }

        // Player attacks - show damage gifs
        this.aGui.println("\n" + this.aPlayerPokemon.getName() + " used " + pMoveName + "!");
        this.aGui.showOpponentPokemonImage(this.aOpponentPokemon.getName() + " dammage.gif");
        this.aOpponentCurrentHP -= vDamage;
        if (this.aOpponentCurrentHP < 0) this.aOpponentCurrentHP = 0;
        this.updateHP();

        // Wait 2s for animation, then process result
        new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            javax.swing.SwingUtilities.invokeLater(() -> {
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

                // Opponent attacks back - show player damage gif
                java.util.HashMap<String, Integer> vOpponentMoves = this.aOpponentPokemon.getMoves();
                if (vOpponentMoves.size() > 0) {
                    String vOpponentMoveName = vOpponentMoves.keySet().iterator().next();
                    Integer vOpponentDamage = vOpponentMoves.get(vOpponentMoveName);

                    this.aGui.println(this.aOpponentPokemon.getName() + " used " + vOpponentMoveName + "!");
                    this.aGui.showPlayerPokemonImage(this.aPlayerPokemon.getName() + " dammage.gif");
                    this.aPlayerCurrentHP -= vOpponentDamage;
                    if (this.aPlayerCurrentHP < 0) this.aPlayerCurrentHP = 0;
                    this.updateHP();

                    // Wait 2s for player damage animation
                    new Thread(() -> {
                        try { Thread.sleep(2000); } catch (InterruptedException e2) {}
                        javax.swing.SwingUtilities.invokeLater(() -> {
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
    }

    private void delayThenEnable() {
        new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            javax.swing.SwingUtilities.invokeLater(() -> {
                if (this.aBattleActive) {
                    this.setButtonsEnabled(true);
                }
            });
        }).start();
    }

    private void setButtonsEnabled(boolean pEnabled) {
        int vMoveCount = this.aPlayerPokemon.getMoves().size();
        this.aGui.getAttack1Button().setEnabled(pEnabled);
        this.aGui.getAttack2Button().setEnabled(pEnabled && vMoveCount > 1);
        this.aGui.getAttack3Button().setEnabled(pEnabled && vMoveCount > 2);
        this.aGui.getAttack4Button().setEnabled(pEnabled && vMoveCount > 3);
        this.aGui.getRunButton().setEnabled(pEnabled);
    }

    private void endBattle(boolean pPlayerWon) {
        this.aBattleActive = false;
        this.setButtonsEnabled(false);

        if (pPlayerWon) {
            this.aGui.println("\nYou won the battle!");
            this.aOpponent.setDefeated(true);
        } else {
            this.aGui.println("\nYou lost the battle!");
        }

        // Show trainer images again
        this.aGui.showPlayerPokemonImage("trainer_battle.png");
        this.aGui.showOpponentPokemonImage(this.aOpponent.getName() + "_battle.png");

        // Wait then return to exploration
        new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            javax.swing.SwingUtilities.invokeLater(() -> {
                this.aGui.endBattle();
                this.aGui.println(this.aEngine.getPlayer().getCurrentRoom().getLongDescription());
            });
        }).start();
    }

    private void runAway() {
        this.aGui.println("\nYou ran away!");
        this.aBattleActive = false;
        this.setButtonsEnabled(false);

        // Show trainer images again
        this.aGui.showPlayerPokemonImage("trainer_battle.png");
        this.aGui.showOpponentPokemonImage(this.aOpponent.getName() + "_battle.png");

        new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            javax.swing.SwingUtilities.invokeLater(() -> {
                this.aGui.endBattle();
                this.aGui.println(this.aEngine.getPlayer().getCurrentRoom().getLongDescription());
            });
        }).start();
    }
}
