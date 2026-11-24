package adventure

import scala.util.Random

sealed trait DialogTrigger

object DialogTrigger {
  object Appear extends DialogTrigger
  object Attack extends DialogTrigger
  object Hurt   extends DialogTrigger
  object Defeat extends DialogTrigger

  val values: Vector[DialogTrigger] = Vector(Appear, Attack, Hurt, Defeat)
}

/** A drop entry pairing a chest with its drop chance (0 to 1). */
class ChestDrop(
  val chest: Chest,
  val chance: Double
)

class EnemyType(
  val name: String,
  val description: String,
  val maxHp: Int,
  val armor: Int,
  val attack: Int,
  val accuracy: Double = 0.85,
  val pierce: Double = 0.0,
  val stealChance: Double = 0.0,
  val openingDamage: Int = 0,
  val chestDrops: Vector[ChestDrop] = Vector.empty,
  val dialogs: Map[DialogTrigger, Vector[String]] = Map.empty
)

class Enemy(val kind: EnemyType, val openingDamage: Int) {

  /** Uses the opening damage of the kind (for a mimic, give the chest's damage instead). */
  def this(kind: EnemyType) = this(kind, kind.openingDamage)

  private var currentHp = kind.maxHp

  def name: String        = kind.name
  def maxHp: Int          = kind.maxHp
  def armor: Int          = kind.armor
  def attack: Int         = kind.attack
  def accuracy: Double    = kind.accuracy
  def pierce: Double      = kind.pierce
  def stealChance: Double = kind.stealChance

  def hp: Int            = currentHp
  def isAlive: Boolean   = currentHp > 0
  def isHurt: Boolean    = hp * 2 <= maxHp

  /** Applies damage that has ALREADY been reduced by armor. */
  def takeDamage(amount: Int): Unit = {
    currentHp = math.max(0, currentHp - math.max(0, amount))
  }

  /** A random line from this enemy's pool for the trigger, if it has any. */
  def dialog(trigger: DialogTrigger, rng: Random): Option[String] = {
    val pool = kind.dialogs.getOrElse(trigger, Vector.empty)
    if pool.isEmpty then None else Some(pool(rng.nextInt(pool.size)))
  }

}