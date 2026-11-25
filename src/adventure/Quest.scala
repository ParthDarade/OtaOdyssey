package adventure

import scala.util.Random

class Quest(
  val name: String,
  val description: String,
  val dialogues: Vector[String],
  val enemy: Enemy,
  val rewards: Vector[Item] = Vector.empty
):
  private val rng = Random()
  private var dialogueIndex: Int = 0
  private var fightWon = false

  /** All dialogue lines have been shown. */
  def isFinished: Boolean = dialogueIndex >= dialogues.length

  /** The dialogue is over AND the player won the fight at its end. */
  def isCompleted: Boolean = isFinished && fightWon

  def currentLine: Option[String] = dialogues.lift(dialogueIndex)

  def advanceDialogue(player: Player): Boolean =
    currentLine match
      case Some(line) =>
        println(line)
        dialogueIndex += 1
        if isFinished then
          println("[End of dialogue]")
          val combat = Combat(player, enemy, rng)
          combat.run()
          fightWon = combat.playerWon
          if fightWon then giveRewards(player)
        else
          println("(Press Enter to continue...)")
        true
      case None =>
        false

  private def giveRewards(player: Player): Unit =
    if rewards.nonEmpty then
      player.inventory.addAll(rewards)
      println(s"Reward received: ${rewards.map(_.name).mkString(", ")}")