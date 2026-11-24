package adventure.Catalogs

import adventure.*

object QuestItemsCatalog:

  val compass = new QuestItem(900, "Old Brass Compass", "They say it shows the way to a sort of piece?")
  val tekkariDiploma = new QuestItem(902, "Forged Diploma", "Looks convincing from a distance, especially in dim light.")

  val all: Vector[QuestItem] = Vector(
    compass,
    tekkariDiploma
  )

end QuestItemsCatalog