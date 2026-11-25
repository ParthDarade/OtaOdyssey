package adventure
import scala.util.Random

class Quest(val name: String, val description: String, val dialogues: Vector[String], val enemy: Enemy):
  private val rng = Random()

  private var dialogueIndex: Int = 0

  def isFinished: Boolean = dialogueIndex >= dialogues.length

  def currentLine: Option[String] =
    dialogues.lift(dialogueIndex)


  def advanceDialogue(player: Player): Boolean =
    currentLine match
          case Some(line) =>
            println(line)
            dialogueIndex += 1
            if isFinished then
              println(s"[End of dialogue]")
              Combat(player,enemy, rng)
            else
              println("(Press Enter to continue...)")
            true
          case None =>
            false



