package adventure

import scala.collection.mutable.Buffer
import scala.collection.mutable.Map


class Inventory:

  private val bag = Buffer[Item]()
  private val equipped = Map[EquipSlot, Equipment]()

  def add(item: Item): Unit =
    bag += item

  def addAll(items: Iterable[Item]): Unit =
    bag ++= items

  def remove(item: Item): Boolean =
    val index = bag.indexOf(item)
    if index >= 0 then
      bag.remove(index)
      true
    else
      false

  def items: Vector[Item] = bag.toVector

  def hasItem(item: Item): Boolean =
    bag.contains(item)

  def findByName(name: String): Option[Item] =
    bag.find(_.name.equalsIgnoreCase(name.trim))

  def equip(item: Equipment): Boolean =
    if remove(item) then
      equipped.put(item.slot, item).foreach(old => bag += old)
      true
    else
      false

  def unequip(slot: EquipSlot): Option[Equipment] =
    val removed = equipped.remove(slot)
    removed.foreach(item => bag += item)
    removed

  def equippedIn(slot: EquipSlot): Option[Equipment] =
    equipped.get(slot)

  def weapon: Option[Weapon] =
    equipped.get(EquipSlot.Weapon).collect { case w: Weapon => w }

  def totalArmor: Int =
    equipped.values.collect { case a: ArmorPiece => a.defenseBonus }.sum

  def weaponsInBag: Vector[Weapon] = bag.collect { case w: Weapon => w }.toVector
  def armorsInBag: Vector[ArmorPiece] = bag.collect { case a: ArmorPiece => a }.toVector
  def foods: Vector[Food] = bag.collect { case f: Food => f }.toVector
  def beverages: Vector[Beverage] = bag.collect { case b: Beverage => b }.toVector
  def chests: Vector[Chest] = bag.collect { case c: Chest => c }.toVector
  def questItems: Vector[QuestItem] = bag.collect { case q: QuestItem => q }.toVector

  def hasSpecialBeverage: Boolean = beverages.exists(_.isSpecial)
  def unopenedChestCount: Int = chests.size

  private def equippedSummary: String =
    val equippedLines =
      EquipSlot.values.toVector.map: slot =>
        val what = equipped.get(slot).map(_.name).getOrElse("-")
        s"  ${slot.displayName}: $what"
    ("Equipped:" +: equippedLines).mkString("\n")

  private def formatItems(list: Vector[Item]): String =
    if list.isEmpty then "  (empty)"
    else
      list.distinct.map: item =>
        val count = list.count(_ == item)
        if count > 1 then s"  ${item.describe} (x$count)" else s"  ${item.describe}"
      .mkString("\n")

  def openBag: String =
    val categoryLines = Vector(
      s"  Weapons (${weaponsInBag.size})",
      s"  Armor (${armorsInBag.size})",
      s"  Beverages (${beverages.size})",
      s"  Food (${foods.size})",
      s"  Chests (${chests.size})"
    )

    (equippedSummary +: ("Bag Categories (use 'open <category>'):" +: categoryLines)).mkString("\n")

  def openCategory(category: String): String =
    val section = category.trim.toLowerCase match
      case "weapons" | "weapon" =>
        s"Weapons:\n${formatItems(weaponsInBag)}"
      case "armor" | "armors" =>
        s"Armor:\n${formatItems(armorsInBag)}"
      case "beverages" | "beverage" | "drinks" | "drink" =>
        s"Beverages:\n${formatItems(beverages)}"
      case "food" | "foods" =>
        s"Food:\n${formatItems(foods)}"
      case "chest" | "chests" =>
        s"Chests:\n${formatItems(chests)}"
      case other =>
        s"Unknown category '$other'. Choose from: weapons, armor, beverages, food, chests."

    s"$equippedSummary\n\n$section"

  def describe: String =
    val snapshot = bag.toVector
    val bagLines =
      if snapshot.isEmpty then Vector("  (empty)")
      else
        snapshot.distinct.map: item =>
          val count = snapshot.count(_ == item)
          if count > 1 then s"  ${item.describe} (x$count)" else s"  ${item.describe}"

    (equippedSummary +: ("Bag:" +: bagLines)).mkString("\n")

end Inventory