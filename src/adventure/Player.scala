package adventure
import scala.util.Try
import scala.collection.mutable
import scala.util.Random
/** A `Player` object represents a player character controlled by the real-life
  * user of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can
  * change, for instance.
  *
  * **NOTE: The AdventureDraft module is not even close to being well-designed.
  * See Chapter 9.3 in the course materials.**
  *
  * @param startingArea  the player’s initial location */

  private val DefaultMaxHp = 100
  private val DefaultLuck = 5
  private val UnarmedDamage = 5
  private val UnarmedAccuracy = 0.9
  private val MinAccuracyModifier = 0.2
  private val BuffDuration = 5
  private val MaxHopNm = 10.0
  private val DefaultHopNm = 2.0

class Player(startingArea: Position, map: GameMap, val maxHp: Int = DefaultMaxHp, val baseLuck: Int = DefaultLuck):
  private var currentLocation = startingArea
  private var quitCommandGiven = false
  private var currentHp  = maxHp

  val inventory = new Inventory
  private val effects    = mutable.Buffer[ActiveEffect]()

  /** Returns the current Hit points of the player. */
  def hp: Int = currentHp

  /** Determines whether the player has indicated a desire to live */
  def isAlive: Boolean = currentHp > 0

  /** Determines whether the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the current location of the player. */
  def location = this.currentLocation

  /** Causes the player to rest for a short while. (This has no substantial effect in game terms.)
    * Prints out a message stating that the player rests. */
  def rest() =
    println("You rest for a while. Better get a move on, though.")

  def go(modifiers: String): Unit =
    val parts = modifiers.trim.split("\\s+").filter(_.nonEmpty)

    if parts.isEmpty then
      println("Go where? Specify a heading (and optional distance in nautical miles), e.g.: go 90 5")
      return

    val headingOpt = Try(parts(0).toDouble).toOption
    val distanceOpt =
      if parts.length >= 2 then Try(parts(1).toDouble).toOption
      else Some(DefaultHopNm)

    (headingOpt, distanceOpt) match
      case (Some(heading), Some(distanceNm)) =>
        if distanceNm <= 0.0 then
          println(s"Distance must be positive (got $distanceNm nm).")
        else if distanceNm > MaxHopNm then
          println(s"Cannot travel $distanceNm nm in a single hop (max hop is $MaxHopNm nm).")
        else
          map.travel(currentLocation, heading, distanceNm) match
            case Some(nextPos) =>
              currentLocation = nextPos
            case None =>
              () // Map.travel already prints why travel failed
      case _ =>
        println(s"Invalid 'go' command: '$modifiers'. Expected format: go <heading> [distanceNm]")


  /** Signals that the player wants to quit the game. */
  def quit() =
    this.quitCommandGiven = true


  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Now at: " + this.location.LatToDDMMSS._1 + this.location.LatToDDMMSS._2 + "' N   "+this.location.LongToDDMMSS._1 + this.location.LongToDDMMSS._2 + "' E"


  // EFFECT MECHANICS__________________
  /** Helper function for finding the bonus for a particular buff. */
  private def bonusFrom(buff: BuffType): Int =
    effects.filter(_.buff == buff).map(_.amount).sum

  def luck: Int = baseLuck + bonusFrom(BuffType.Luck)

  def attackPower: Int =
    inventory.weapon.map(_.attackDamage).getOrElse(UnarmedDamage) + bonusFrom(BuffType.Attack)

  def pierce: Double =
    inventory.weapon.map(_.pierce).getOrElse(0.0)

  def armor: Int = inventory.totalArmor + bonusFrom(BuffType.Armor)

  def accuracyModifier: Double =
    math.max(MinAccuracyModifier, 1.0 - effects.map(_.accuracyPenalty).sum)

  def hitChance: Double =
    inventory.weapon.map(_.accuracy).getOrElse(UnarmedAccuracy) * accuracyModifier

  def takeDamage(amount: Int): Unit =
    currentHp = math.max(0, currentHp - math.max(0, amount))

  def heal(amount: Int): Int =
    val before = currentHp
    currentHp = math.min(maxHp, currentHp + math.max(0, amount))
    currentHp - before

  def eat(food: Food): Option[Int] =
    if inventory.remove(food) then Some(heal(food.healAmount(luck)))
    else None

  def drink(beverage: Beverage, rng: Random): Option[ActiveEffect] =
    if inventory.remove(beverage) then
      val buff = BuffType.values(rng.nextInt(BuffType.values.length))
      val effect = new ActiveEffect(buff, beverage.buffAmount(luck), beverage.accuracyPenalty, BuffDuration)
      effects += effect
      Some(effect)
    else None

  /** Call once per game turn (after a move, and after each combat round). */
  def tick(): Unit =
    val updated = effects.toVector
      .map(_.ticked)
      .filter(_.turnsLeft > 0)
    effects.clear()
    effects ++= updated

  def activeEffects: Vector[ActiveEffect] = effects.toVector

  /** Shows the player stats. */
  def status: String =
    val effectText = if effects.isEmpty then "none" else effects.map(_.describe).mkString(", ")
    s"HP: $hp/$maxHp | Armor: $armor | Attack: $attackPower | Luck: $luck | " +
      s"Hit chance: ${(hitChance * 100).round}% | Effects: $effectText"

end Player

class ActiveEffect(
  val buff: BuffType,
  val amount: Int,
  val accuracyPenalty: Double,
  val turnsLeft: Int
):
  def describe: String = s"${buff.displayName} +$amount (accuracy -${(accuracyPenalty * 100).round}%, $turnsLeft turns left)"

  def ticked: ActiveEffect =
    new ActiveEffect(buff, amount, accuracyPenalty, math.max(0, turnsLeft - 1))
