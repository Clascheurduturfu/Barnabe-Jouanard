package v1;
/**
 * Classe Game - le moteur du jeu d'aventure Zuul.
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */
public class Game
{
   private Room aCurrentRoom;
   private Parser aParser;
   
   /**
     * Constructeur par défaut : initialise la carte et le parser.
     */
   public Game()
   {
       this.createRooms();
       this.aParser = new Parser();
   } // Game()
   
   /**
     * Crée toutes les pièces et relie leurs sorties.
     */
   private void createRooms()
   {
       Room vLittleroot = new Room("in your Littleroot, your Home Town");
       Room vHouse      = new Room("in your house in Littleroot Town");
       Room vRoute101   = new Room("on Route 101, wild Pokemon might appear in the tall grass");
       Room vOldale     = new Room("in Oldale Town, a small junction with a Pokemon Center");
       Room vRoute102   = new Room("on Route 102, heading west towards Petalburg");
       Room vPetalburg  = new Room("in Petalburg City, where your father is the Gym Leader");
       vLittleroot.setExits(vRoute101, vHouse, null, null);
       vHouse.setExits(null, null, null, vLittleroot);
       vRoute101.setExits(vOldale, null, vHouse, null);
       vOldale.setExits(null, vRoute102, vRoute101, null);
       vRoute102.setExits(null, vPetalburg, null, vOldale);
       vPetalburg.setExits(null, null, null, vRoute102);
       this.aCurrentRoom=vHouse;
   } // createRooms()
   
   /**
     * Procédure principale pour jouer. Boucle jusqu'à l'arrêt du jeu.
     */
   private void play()
   {
       boolean vFinished = false;
       this.printWelcome();
       while (vFinished==false)
       {
           Command vCommand = this.aParser.getCommand();
           vFinished=this.processCommand(vCommand);
       } // while
       System.out.print("Thank you for playing. Good bye.");
   } // play()
   
   /**
     * Affiche le message de bienvenue et la position actuelle.
     */
   private void printWelcome()
   {
       System.out.println("Welcome to the World of Zuul!");
       System.out.println("World of Zuul is a new, incredibly boring adventure game.");
       System.out.println("Type 'help' if you need help.");
       System.out.println("You are "+this.aCurrentRoom.getDescription()+ "of the university");
       System.out.print("Exits: ");
       if (this.aCurrentRoom.aNorthExit != null)
       {
           System.out.print(" north");
       } 
       if (this.aCurrentRoom.aEastExit != null)
       {
            System.out.print(" east");
       }
       if (this.aCurrentRoom.aSouthExit != null)
       {
            System.out.print(" south");
       }
       if (this.aCurrentRoom.aWestExit != null)
       {
            System.out.print(" west");
       } 
   } // printWelcome()
   
   /**
     * Exécute une commande de déplacement vers une pièce.
     * @param pCommand La commande contenant la direction.
     */
   private void goRoom (final Command pCommand)
   {
       if (pCommand.hasSecondWord())
       {
           if (pCommand.getSecondWord().equals("North"))
           {
               if (this.aCurrentRoom.aNorthExit!=null)
               {
                   String vDirection="";
                   this.aCurrentRoom=this.aCurrentRoom.aNorthExit;
                   System.out.println(this.aCurrentRoom.getDescription());
                   System.out.print("Exits: ");
                   Room vNextRoom = this.aCurrentRoom.getExit(vDirection);
                   if (this.aCurrentRoom.aNorthExit != null)
                   {
                       System.out.print(" north");
                    }
                   if (this.aCurrentRoom.aEastExit != null)
                   {
                        System.out.print(" east");
                    }
                   if (this.aCurrentRoom.aSouthExit != null)
                   {
                        System.out.print(" south");
                    }
                   if (this.aCurrentRoom.aWestExit != null)
                   {
                        System.out.print(" west");
                    }
               } else
               {
                   System.out.println("There is no door !");
               }   
           }
           else if (pCommand.getSecondWord().equals("East"))
           {
               if (this.aCurrentRoom.aEastExit!=null)
               {
                   this.aCurrentRoom=this.aCurrentRoom.aEastExit;
                   System.out.println(this.aCurrentRoom.getDescription());
                   System.out.print("Exits: ");
                   if (this.aCurrentRoom.aNorthExit != null)
                   {
                       System.out.print(" north");
                    }
                   if (this.aCurrentRoom.aEastExit != null)
                   {
                        System.out.print(" east");
                    }
                   if (this.aCurrentRoom.aSouthExit != null)
                   {
                        System.out.print(" south");
                    }
                   if (this.aCurrentRoom.aWestExit != null)
                   {
                        System.out.print(" west");
                    }
               } else
               {
                   System.out.println("There is no door !");
               }   
           }
           else if (pCommand.getSecondWord().equals("South"))
           {
               if (this.aCurrentRoom.aSouthExit!=null)
               {
                   this.aCurrentRoom=this.aCurrentRoom.aSouthExit;
                   System.out.println(this.aCurrentRoom.getDescription());
                   System.out.print("Exits: ");
                   if (this.aCurrentRoom.aNorthExit != null)
                   {
                       System.out.print(" north");
                    }
                   if (this.aCurrentRoom.aEastExit != null)
                   {
                        System.out.print(" east");
                    }
                   if (this.aCurrentRoom.aSouthExit != null)
                   {
                        System.out.print(" south");
                    }
                   if (this.aCurrentRoom.aWestExit != null)
                   {
                        System.out.print(" west");
                    }
                } else
                {
                   System.out.println("There is no door !");
                }   
           }
           else if (pCommand.getSecondWord().equals("West"))
           {
               if (this.aCurrentRoom.aWestExit!=null)
               {
                   this.aCurrentRoom=this.aCurrentRoom.aWestExit;
                   System.out.println(this.aCurrentRoom.getDescription());
                   System.out.print("Exits: ");
                   if (this.aCurrentRoom.aNorthExit != null)
                   {
                       System.out.print(" north");
                    }
                   if (this.aCurrentRoom.aEastExit != null)
                   {
                        System.out.print(" east");
                    }
                   if (this.aCurrentRoom.aSouthExit != null)
                   {
                        System.out.print(" south");
                    }
                   if (this.aCurrentRoom.aWestExit != null)
                   {
                        System.out.print(" west");
                    }
               } else
               {
                   System.out.println("There is no door !");
               }   
           }
           else
           {
               System.out.println("Unknown direction !");
           }
       } else 
       {
           System.out.println("Go where?");
       }
   } // goRoom()
   
   /**
     * Affiche l'aide à l'utilisateur.
     */
   private void printHelp()
   {
       System.out.println("You are lost. You are alone.");
       System.out.println("You wander around at the university.");
       System.out.println("Your command words are:");
       System.out.println("  go quit help");
   } // printHelp()
   
   /**
     * Vérifie si l'utilisateur veut quitter le jeu.
     * @param pCommand La commande à analyser.
     * @return true si la commande est de quitter, false sinon.
     */
   private boolean quit (final Command pCommand)
   {
       if (pCommand.hasSecondWord())
       {
           System.out.println("Quit what ?");
           return false;
       }
       return true;
   } // quit()
   
   /**
     * Analyse et traite la commande passée en paramètre.
     * @param pCommand La commande à traiter.
     * @return true si le jeu doit s'arrêter, false sinon.
     */
   private boolean processCommand (final Command pCommand)
   {
       if (pCommand.isUnknown()) 
       {
        System.out.println("I don't know what you mean...");
        return false;
       }
       String vCommandWord = pCommand.getCommandWord();
       if (vCommandWord.equals("help")) 
       {
           this.printHelp();
           return false;
       } else if (vCommandWord.equals("go")) 
       {
           this.goRoom(pCommand);
           return false;
       } else if (vCommandWord.equals("quit")) 
       {
           return this.quit(pCommand);
        } else {
            System.out.println("Erreur du programmeur : commande non reconnue !");
            return true;
       }
   } // processCommand()
} // Game
