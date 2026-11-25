package adventure

import scala.util.Random
/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as “go east” or “rest” */
class Action(input: String):

  private val command   = input.trim.toLowerCase
  private val verb      = command.takeWhile( _ != ' ' )
  private val modifiers = command.drop(verb.length).trim

  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns ``false` if the command was not recognized
    * and true` otherwise. */
  private var turnUsed = false

  /** True if the last call to execute used up a game turn. */
  def usesTurn: Boolean = turnUsed

  def execute(actor: Player): Boolean =
    if actor.isInQuest && command.isEmpty then
      actor.continueQuest()
      true
    else
      verb match
        case "sail" if !actor.isInQuest =>
          turnUsed = actor.go(modifiers)
          true
        case "inventory" | "bag" | "i" =>
          println(actor.inventory.openBag)
          true
        case "open" =>
          if modifiers == "chest" || modifiers.startsWith("chest ") then
            val chestName = if modifiers == "chest" then "" else modifiers.drop("chest".length).trim
            actor.openChestByName(chestName)
          else
            println(if modifiers.isEmpty then actor.inventory.openBag else actor.inventory.openCategory(modifiers))
          true
        case "unlock" =>
          actor.openChestByName(modifiers)
          true
        case "eat" =>
          actor.eatByName(modifiers)
          true
        case "drink" =>
          actor.drinkByName(modifiers)
          true
        case "equip" =>
          actor.equipByName(modifiers)
          true
        case "status" =>
          println(actor.status)
          true
        case "help" =>
          println("Commands: sail <heading> [nm] | inventory | open <category> | unlock <chest> | eat <item> | drink <item> | equip <item> | status | quit")
          true
        case "debug" =>
          actor.debug()
          true
        case "quit" =>
          actor.quit()
          true
        case _ =>
          false


  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString =
    this.verb + " (modifiers: " + this.modifiers + ")"

end Action

