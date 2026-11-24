package adventure.Catalogs

import adventure.*

object WeaponCatalog:

  val cutlass = Weapon(200, "Cutlass", "A light sailor's blade.", attackDamage = 8, accuracy = 0.95)
  val spear = Weapon(201, "Spear", "Long reach, pierces armor.", attackDamage = 10, accuracy = 0.85, pierce = 0.4)
  val totallyNormalStick = Weapon(202, "Totally Normal Stick", "It is just a stick. Wouldnt make a good weapon, would it?", attackDamage = 50, accuracy = 1.0, pierce = 0.9)
  /** Quest reward. Not in the loot lists below. */
  val legendaryBlade = Weapon(249, "Tekkari's Ruler", "Forged from a tekkari's tears in an exam week.", attackDamage = 60, accuracy = 0.95, pierce = 0.5)
  
  val all: Vector[Weapon] = Vector(cutlass, spear, totallyNormalStick, legendaryBlade)