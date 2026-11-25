package adventure

class Quest(val name: String, val description: String, val dialogues: Vector[String]):

  private var dialogueIndex: Int = 0

  def isFinished: Boolean = dialogueIndex >= dialogues.length

  def currentLine: Option[String] =
    dialogues.lift(dialogueIndex)


  def advanceDialogue(): Boolean =
    currentLine match
          case Some(line) =>
            println(line)
            dialogueIndex += 1
            if isFinished then
              println(s"[End of dialogue]")
              //TODO: Start Fight
            else
              println("(Press Enter to continue...)")
            true
          case None =>
            false



