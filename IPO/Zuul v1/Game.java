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
       Room vPetalburgWoods  = new Room("in Petalburg Woods,");
       Room vRustboro   = new Room("in Petalburg City, where your father is the Gym Leader");
       Room vSkyPillar  = new Room("on the Sky Pilar, Rayquazza's home..");
       
       vHouse.setExit("West", vLittleroot);
       
       vLittleroot.setExit("North", vRoute101);
       vLittleroot.setExit("East", vHouse);
       
       vRoute101.setExit("North", vOldale);
       vRoute101.setExit("South", vLittleroot);
       
       vOldale.setExit("East", vRoute102);
       vOldale.setExit("South", vRoute101);
       
       vRoute102.setExit("North", vPetalburgWoods);
       vRoute102.setExit("East", vPetalburg);
       vRoute102.setExit("West", vOldale);
       
       vPetalburg.setExit("South", vRoute102);
       vPetalburg.setExit("West", vRustboro);
       
       vPetalburgWoods.setExit("South", vRoute102);
       vPetalburgWoods.setExit("West", vRustboro);
       
       vRustboro.setExit("Up", vSkyPillar);
       vRustboro.setExit("East", vPetalburgWoods);
       
       vSkyPillar.setExit("Down", vRustboro);
       
       this.aCurrentRoom=vHouse;    
   } // createRooms()
   
   /**
     * Procédure principale pour jouer. Boucle jusqu'à l'arrêt du jeu.
     */
   public void play()
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
       System.out.println("Welcome to the World of Pokemon!");
       System.out.println("A Wonderful World Where You Can Live An Adventure!");
       System.out.println("Type 'help' if you need help.");
       aCurrentRoom.printLocationInfo();
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
               if (this.aCurrentRoom.getExit("North")!=null)
               {
                   this.aCurrentRoom=this.aCurrentRoom.getExit("North");
                   aCurrentRoom.printLocationInfo();
               } else
               {
                   System.out.println("There is no door !");
               }   
           }
           else if (pCommand.getSecondWord().equals("East"))
           {
               if (this.aCurrentRoom.getExit("East")!=null)
               {
                   this.aCurrentRoom=this.aCurrentRoom.getExit("East");
                   aCurrentRoom.printLocationInfo();
               } else
               {
                   System.out.println("There is no door !");
               }   
           }
           else if (pCommand.getSecondWord().equals("South"))
           {
               if (this.aCurrentRoom.getExit("South")!=null)
               {
                   this.aCurrentRoom=this.aCurrentRoom.getExit("South");
                   aCurrentRoom.printLocationInfo();
                } else
                {
                   System.out.println("There is no door !");
                }   
           }
           else if (pCommand.getSecondWord().equals("West"))
           {
               if (this.aCurrentRoom.getExit("West")!=null)
               {
                   this.aCurrentRoom=this.aCurrentRoom.getExit("West");
                   aCurrentRoom.printLocationInfo();
               } else
               {
                   System.out.println("There is no door !");
               }   
           }
           else if (pCommand.getSecondWord().equals("Up"))
           {
               if (this.aCurrentRoom.getExit("Up")!=null)
               {
                   this.aCurrentRoom=this.aCurrentRoom.getExit("Up");
                   aCurrentRoom.printLocationInfo();
               } else
               {
                   System.out.println("There is no door !");
               }   
           }
           else if (pCommand.getSecondWord().equals("Down"))
           {
               if (this.aCurrentRoom.getExit("Down")!=null)
               {
                   this.aCurrentRoom=this.aCurrentRoom.getExit("Down");
                   aCurrentRoom.printLocationInfo();
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
