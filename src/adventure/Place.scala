package adventure

class Place(val name: String, val location: Position, val quest: Quest):
  val description = "You smell sea salt and boundless water in the sea."
  val radius = 2 //In Nautical Miles
  val isLandedAtPlace = false
  val isQuestOver = false

  //val bounds = Vector[(Double, Double)]
  def isAtPlace(position: Position): Boolean = {
    if GameMap().distanceNmBetween(position,this.location) > radius then
      true
    else false
  }


