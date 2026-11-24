package adventure

import adventure.*
import adventure.Catalogs.EnemyCatalog

import scala.util.Random

/** What opening a chest produced. */
sealed trait ChestResult

object ChestResult:
  class Contents(val items: Vector[Item]) extends ChestResult
  class Mimic(val enemy: Enemy) extends ChestResult

object Loot:

  /** Opens a chest from the player's inventory. The chest is used up either way.
    * Returns None if the player does not have it.
    *
    * - Normal result: rolls loot from the chest's loots table and adds it to inventory.
    * - Mimic result: nothing is added. The caller should start a Combat with the
    *   returned enemy (it already carries the chest's mimicDamage as opening damage).
    */
  def openChest(player: Player, chest: Chest, rng: Random): Option[ChestResult] =
    if !player.inventory.remove(chest) then None
    else if rng.nextDouble() < chest.mimicChance then
      Some(new ChestResult.Mimic(new Enemy(EnemyCatalog.mimic, chest.mimicDamage)))
    else
      val items = chest.rollLoot(rng)
      player.inventory.addAll(items)
      Some(new ChestResult.Contents(items))

  /** The chests an enemy drops after it is defeated: each of its chest types is rolled separately. */
  def chestDrops(kind: EnemyType, rng: Random): Vector[Chest] =
    kind.chestDrops.collect { case drop if rng.nextDouble() < drop.chance => drop.chest }

  /** Text for the result of openChest. For a mimic, Combat.start() prints the rest. */
  def describe(chest: Chest, result: ChestResult): Vector[String] =
    result match
      case _: ChestResult.Mimic =>
        Vector(s"The ${chest.name} was a mimic!")
      case contents: ChestResult.Contents =>
        if contents.items.isEmpty then
          Vector(s"You open the ${chest.name}. It is empty.")
        else
          val lines = contents.items.distinct.map: item =>
            val n = contents.items.count(_ == item)
            if n > 1 then s"  - ${item.name} (x$n)" else s"  - ${item.name}"
          s"You open the ${chest.name} and find:" +: lines

end Loot