package adventure.Catalogs

import adventure.*

object QuestItemsCatalog:

  val compass = new QuestItem(900, "Old Brass Compass", "They say it shows the way to a sort of piece?")
  val tekkariDiploma = new QuestItem(902, "Forged Diploma", "Looks convincing from a distance, especially in dim light.")
  val rejectedPortfolio = new QuestItem(903, "Rejected Art Portfolio", "Covered in red ink and a massive 'F'. Professor Mori might want to see this.")
  val all: Vector[QuestItem] = Vector(
    compass,
    tekkariDiploma,
    rejectedPortfolio
  )

end QuestItemsCatalog