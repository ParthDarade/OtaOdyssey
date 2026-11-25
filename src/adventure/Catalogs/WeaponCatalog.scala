package adventure.Catalogs

import adventure.*

object WeaponCatalog:

  val rubberChiken = Weapon(200, "Rubber Chicken", "Deals emotional damage and mild confusion.", attackDamage = 15, accuracy = 0.95)
  val spear = Weapon(201, "Spear", "Long reach, pierces armor.", attackDamage = 10, accuracy = 0.85, pierce = 0.4)
  val totallyNormalStick = Weapon(202, "Totally Normal Stick", "It is just a stick. Wouldnt make a good weapon, would it?", attackDamage = 100, accuracy = 1.0, pierce = 0.9)
  val solderingIron = Weapon(203, "Overengineered Soldering Iron", "Runs hot, smells like flux, voids every warranty.", attackDamage = 20, accuracy = 0.9, pierce = 1.0)
  val thesisDraft = Weapon(204, "Thesis Draft", "Heavy enough to block a blow, dense enough to put a tekkarş to sleep.", attackDamage = 10, accuracy = 0.8, pierce = 0.5)

  /** Quest reward. Not in the loot lists below. */
  val legendaryBlade = Weapon(249, "Tekkari's Ruler", "Forged from a tekkari's tears in an exam week.", attackDamage = 60, accuracy = 0.95, pierce = 0.5)
  
  val all: Vector[Weapon] = Vector(rubberChiken, spear, totallyNormalStick, solderingIron, thesisDraft, legendaryBlade)