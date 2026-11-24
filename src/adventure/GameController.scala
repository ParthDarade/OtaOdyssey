package adventure
import scala.io.StdIn.*

class GameController:
  val title = "A Forest Adventure"

  val startingPoint = Position(59.523886701211076, 24.733346378698798)
  val destination = Position(60.17004798739003, 24.832385157420127)
  val gameMap = new GameMap()
  /** The character who is the protagonist of the adventure and whom the real-life player controls. */
  val player = Player(startingPoint, gameMap)
  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = this.player.location == this.destination

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit

  /** Prints out a message that is to be displayed to the player at the beginning of the game. */
  def printWelcome(): Unit =
    println("You are Otaniemi's last hope, Sail to Tallinn, get a crate of beer and deliver it back home \n")
    println("Goodluck sailor, Be careful of the currents, sharks, and steer clear of the Helsinki police on your final course.")


  /** Prints out a message that is to be displayed to the player at the end of the game.
    * The message will be different depending on whether the player has completed the quest. */
  def printGoodbye(): Unit =
    println()
    if this.isComplete then
      println("Home at last... and phew, just in time! Well done!")
    else if this.turnCount == this.timeLimit then
      println("Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!")
    else   // game over due to player quitting
      println("Quitter!")


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


