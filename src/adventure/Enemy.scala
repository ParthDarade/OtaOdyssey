package adventure

import scala.util.Random




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