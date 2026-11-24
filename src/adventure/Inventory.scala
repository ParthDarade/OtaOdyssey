package adventure

import scala.collection.mutable

class Inventory:

  private val bag = mutable.Buffer[Item]()
  private val equipped = mutable.Map[EquipSlot, Equipment]()

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

  def foods: Vector[Food] = bag.collect { case f: Food => f }.toVector
  def beverages: Vector[Beverage] = bag.collect { case b: Beverage => b }.toVector
  def chests: Vector[Chest] = bag.collect { case c: Chest => c }.toVector
  def questItems: Vector[QuestItem] = bag.collect { case q: QuestItem => q }.toVector

  def hasSpecialBeverage: Boolean = beverages.exists(_.isSpecial)
  def unopenedChestCount: Int = chests.size

  def describe: String =
    val equippedLines =
      EquipSlot.values.toVector.map: slot =>
        val what = equipped.get(slot).map(_.name).getOrElse("-")
        s"  ${slot.displayName}: $what"

    val snapshot = bag.toVector
    val bagLines =
      if snapshot.isEmpty then Vector("  (empty)")
      else
        snapshot.distinct.map: item =>
          val count = snapshot.count(_ == item)
          if count > 1 then s"  ${item.describe} (x$count)" else s"  ${item.describe}"

    (("Equipped:" +: equippedLines) ++ ("Bag:" +: bagLines)).mkString("\n")

end Inventory