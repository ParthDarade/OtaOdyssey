package adventure

class Place(val name: String, val location: Position, val quest: Quest):
  val description = "You smell sea salt and boundless water in the sea."
  val radius = 2 //In Nautical Miles
  var isLandedAtPlace = false
  var isQuestOver = false

  //val bounds = Vector[(Double, Double)]
  def isAtPlace(position: Position): Boolean = {
    if GameMap().distanceNmBetween(position,this.location) < radius then
      true
    else false
  }
  def landAtPlace() ={
    isLandedAtPlace = true
    if !isQuestOver then
      println(" A quest is available, Would you like to participate? (type yes/y)")
      val choice = scala.io.StdIn.readLine().trim.toLowerCase
      if choice == "y"|| choice== "yes" then
        println( this.quest.description)
        this.quest.advanceDialogue()


  }


