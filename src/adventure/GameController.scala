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
    WeaponCatalog.cutlass,
    ArmorCatalog.leatherChestplate,
    ArmorCatalog.leatherLeggings,
    FoodCatalog.apple,
    BeverageCatalog.rum
  ))
  player.inventory.equip(WeaponCatalog.cutlass)
  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = player.isAlive && QuestCatalog.OtaniemiQuest.isFinished

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit

  /** Prints out a message that is to be displayed to the player at the beginning of the game. */
  def printWelcome(): Unit =
    println("Captain! Otaniemi is in dire need of supplies. A shipment of beer awaits collection in Tallinn, and you have been entrusted with its delivery to the shores of Otaniemi.")
    println("Your passage lies across the treacherous Gulf of Finland. Navigate the currents, mind the reefs, and keep a weather eye open for passing vessels. The Helsinki authorities are notoriously particular about their waters.")
    println("Make landfall at Otaniemi, deliver the cargo, and you shall have earned the gratitude of the entire teekkari community.")
    println("Fair winds and following seas, Captain. Otaniemi awaits!")


  /** Prints out a message that is to be displayed to the player at the end of the game.
    * The message will be different depending on whether the player has completed the quest. */
  def printGoodbye(): Unit =
    println()
    if this.isComplete then
      println("Otaniemi will remember your adventure untill the possible Vappu. Until then, You have a Matrix algebra exercise in 2 hours !")
    else if this.turnCount == this.timeLimit then
      println("Oh no! Your voyage has come to an end. With no provisions, no port in sight, and no hope of rescue, you slump against the helm and weep like a cabin boy.\nGame over!")
    else   // game over due to player quitting
      println("Haha! Made you rage quit!")


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
    val command = readLine("Command: ")
    val action = Action(command)
    if action.execute(this.player) then
      this.turnCount += 1
    else
      println("Unknown command: \"" + command + "\".")

end GameController


