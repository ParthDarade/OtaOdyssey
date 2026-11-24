package adventure.ui


import adventure.*
import scala.io.StdIn.*

/** Starts a fully text-based version of the Adventure game in the console.
  * (Cf. ``.)
  *
   */

@main def startAdventureTextUI() =
  TextUI(GameController()).run()


private class TextUI(val game: GameController):
  val player = game.player

  /** Runs the game. First, a welcome message is printed, then the player gets the chance to
    * play any number of turns until the game is over, and finally a goodbye message is printed. */
  def run() =
    this.game.printWelcome()
    while !this.game.isOver do
      this.game.playTurn()
    println("\n")
    this.game.printGoodbye()



  /** Requests a command from the player, plays a game turn accordingly, and prints out a
    * report of what happened.  */

end TextUI
