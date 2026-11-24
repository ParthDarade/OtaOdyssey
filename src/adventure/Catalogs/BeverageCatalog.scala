package adventure.Catalogs

import adventure.*

object BeverageCatalog:
  
  val rum = Beverage(150, "Rum", "Burns on the way down.", baseBuff = 5, accuracyPenalty = 0.05)
  val beer = Beverage(151, "Beer", "Cheap but cheerful.", baseBuff = 3, accuracyPenalty = 0.03)
  val whiskey = Beverage(152, "Whiskey", "Strong and smoky.", baseBuff = 8, accuracyPenalty = 0.10)
  val vodka = Beverage(153, "Vodka", "Strong asf.", baseBuff = 10, accuracyPenalty = 0.20)
  val mysteriousTekkariCoctail = Beverage(154, "Mysterious Tekkari Coctail", "Maybe you shouldnt drink this?", baseBuff = 30, accuracyPenalty = 0.50)
  
  val all: Vector[Beverage] = Vector(rum, beer, whiskey, vodka, mysteriousTekkariCoctail)