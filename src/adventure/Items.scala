package adventure

import scala.util.Random

object Luck {
  def scale(base: Int, luck: Int): Int =
    math.max(0, (base * (1 + luck / 100.0)).round.toInt)
}

sealed trait EquipSlot {
  def displayName: String
}

object EquipSlot {
  object Helmet     extends EquipSlot { val displayName = "Helmet" }
  object Chestplate extends EquipSlot { val displayName = "Chestplate" }
  object Legging    extends EquipSlot { val displayName = "Legging" }
  object Shoes      extends EquipSlot { val displayName = "Shoes" }
  object Gloves     extends EquipSlot { val displayName = "Gloves" }
  object Weapon     extends EquipSlot { val displayName = "Weapon" }

  val values: Vector[EquipSlot] = Vector(Helmet, Chestplate, Legging, Shoes, Gloves, Weapon)
}

/** The possible random buffs a beverage can give when drunk. */
sealed trait BuffType {
  def displayName: String
}

object BuffType {
  object Attack extends BuffType { val displayName = "Attack" }
  object Armor  extends BuffType { val displayName = "Armor" }
  object Pierce extends BuffType { val displayName = "Pierce" }
  object Luck   extends BuffType { val displayName = "Luck" }

  val values: Vector[BuffType] = Vector(Attack, Armor, Pierce, Luck)
}

sealed trait Item {
  def id: Int
  def name: String
  def description: String
  def describe: String = s"$name - $description"
}

sealed trait Consumable extends Item

/** Food only restores health. The real heal amount depends on luck. */
class Food(
  val id: Int,
  val name: String,
  val description: String,
  val baseHeal: Int
) extends Consumable {
  def healAmount(luck: Int): Int = Luck.scale(baseHeal, luck)
}

/** A beverage gives a random buff (rolled when drunk) and lowers accuracy.
  * - baseBuff is scaled by luck; accuracyPenalty is subtracted from the player's
  * - accuracy (e.g. 0.05 = 5 percentage points).
  */
class Beverage(
  val id: Int,
  val name: String,
  val description: String,
  val baseBuff: Int,
  val accuracyPenalty: Double,
  val isSpecial: Boolean = false
) extends Consumable {
  def buffAmount(luck: Int): Int = Luck.scale(baseBuff, luck)
}

sealed trait Equipment extends Item {
  def slot: EquipSlot
}

class Weapon(
  val id: Int,
  val name: String,
  val description: String,
  val attackDamage: Int,
  val accuracy: Double = 0.9,
  val pierce: Double = 0.0
) extends Equipment {
  val slot: EquipSlot = EquipSlot.Weapon
}

class ArmorPiece(
  val id: Int,
  val name: String,
  val description: String,
  val slot: EquipSlot,
  val defenseBonus: Int,
  val isSpecial: Boolean = false
) extends Equipment

class LootEntry(
  val item: Item,
  val weight: Int
)

class Chest(
  val id: Int,
  val name: String,
  val description: String,
  val mimicChance: Double = 0.20,
  val mimicDamage: Int = 25,
  val mainLoots: Vector[LootEntry] = Vector.empty,
  val beverageLoots: Vector[LootEntry] = Vector.empty,
  val minBeverages: Int = 1,
  val maxBeverages: Int = 2
) extends Item {

  def rollLoot(rng: Random = new Random()): Vector[Item] = {
    val mainDrop = pickOne(mainLoots, rng).toVector
    val drinkCount =
      if beverageLoots.isEmpty then 0
      else minBeverages + rng.nextInt(maxBeverages - minBeverages + 1)
    val drinkDrops = Vector.fill(drinkCount)(pickOne(beverageLoots, rng)).flatten
    mainDrop ++ drinkDrops
  }

  private def pickOne(entries: Vector[LootEntry], rng: Random): Option[Item] = {
    if entries.isEmpty then None
    else
      val totalWeight = entries.map(_.weight).sum
      var roll = rng.nextInt(totalWeight)
      var chosen: Option[Item] = None
      var i = 0
      while i < entries.length && chosen.isEmpty do
        val entry = entries(i)
        if roll < entry.weight then chosen = Some(entry.item)
        else roll -= entry.weight
        i += 1
      chosen
  }
}

class QuestItem(
  val id: Int,
  val name: String,
  val description: String,
) extends Item