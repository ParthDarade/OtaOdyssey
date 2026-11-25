package adventure

import scala.io.StdIn.*
import adventure.Catalogs.*

class GameController:
  val title = "A Forest Adventure"

  val startingPoint = Position(59.523886701211076, 24.733346378698798)
  val destination = Position(60.17004798739003, 24.832385157420127)
  val gameMap = new GameMap()
  /** The character who is the protagonist of the adventure and whom the real-life player controls. */
  val player = Player(startingPoint, gameMap)
  player.inventory.addAll(Vector(
    WeaponCatalog.spear,
    ArmorCatalog.leatherChestplate,
    ArmorCatalog.leatherLeggings,
    FoodCatalog.apple,
    BeverageCatalog.rum,
  ))
  player.inventory.equip(WeaponCatalog.spear)
  player.inventory.equip(ArmorCatalog.leatherChestplate)
  player.inventory.equip(ArmorCatalog.leatherLeggings)
  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40



  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = player.isAlive && QuestCatalog.OtaniemiQuest.isCompleted

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit || !player.isAlive

  /** Prints out a message that is to be displayed to the player at the beginning of the game. */
  def printWelcome(): Unit =
    println("\nCaptain! Otaniemi is in dire need of supplies. A shipment of beer awaits collection in Tallinn, and you have been entrusted with its delivery to the shores of Otaniemi.")
    println("Your passage lies across the treacherous Gulf of Finland. Navigate the currents, mind the reefs, and keep a weather eye open for passing vessels. The Helsinki authorities are notoriously particular about their waters.")
    println("Make landfall at Otaniemi, deliver the cargo, and you shall have earned the gratitude of the entire teekkari community.")
    println("Fair winds and following seas, Captain. Otaniemi awaits!")
    println("\nTo set sail, use command 'sail direction distance'. 0 represents north, 90 represents east and so on. The maximum distance is 10 nautical miles!")
    println("When you are near a landmark, you will be able to see the landmark`s direction and distance to you.")
    println("Example: If you use sail 0 5, you will sail towards north for 5 nautical miles.")
    println("\nStart with: sail 0 10")

  /** Prints out a message that is to be displayed to the player at the end of the game.
    * The message will be different depending on whether the player has completed the quest. */
  def printGoodbye(): Unit =
    println()
    if this.isComplete then
      println(s"Otaniemi will remember your adventure untill the possible Vappu. Until then, You have a Matrix algebra exercise in 2 hours ! Final score: ${this.score}")
    else if this.turnCount == this.timeLimit then
      println("Oh no! Your voyage has come to an end. With no provisions, no port in sight, and no hope of rescue, you slump against the helm and weep like a cabin boy.\nGame over!")
    else if this.player.hasQuit then  // game over due to player quitting
      println("Haha! Made you rage quit!")
    else if !player.isAlive then
      println("you died :(")


  /** Runs the game. First, a welcome message is printed, then the player gets the chance
    * to play any number of turns until the game is over, and finally a goodbye message is printed. */
  def run(): Unit =
    this.printWelcome()
    while !this.isOver do
      this.playTurn()
    this.printGoodbye()


  /** Requests a command from the player, plays a game turn accordingly,
    * and prints out a report of what happened. */
  def playTurn(): Unit =
    println()
    val command = Option(readLine("Command: ")).getOrElse("quit")
    val action = Action(command)
    if action.execute(this.player) then
      if action.usesTurn then
        this.turnCount += 1
        this.player.tick()

    else
      println("Unknown command: \"" + command + "\".")


  def score: Int =
    val inv = this.player.inventory
    inv.beverages.size * 100 + (if inv.hasSpecialBeverage then 1000 else 0) + inv.unopenedChestCount * 25

end GameController