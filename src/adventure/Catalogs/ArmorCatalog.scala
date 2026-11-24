package adventure.Catalogs

import adventure.*

object ArmorCatalog:

  val leatherHelmet = new ArmorPiece(250, "Leather Helmet", "Leathery.", EquipSlot.Helmet, defenseBonus = 2)
  val leatherChestplate = new ArmorPiece(251, "Leather Chestplate", "What do you mean 'plate'?.", EquipSlot.Chestplate, defenseBonus = 4)
  val leatherLeggings = new ArmorPiece(252, "Leather Leggings", "Wraps nicely.", EquipSlot.Legging, defenseBonus = 3)
  val leatherGloves = new ArmorPiece(253, "Leather Gloves", "Keeps rope burn away.", EquipSlot.Gloves, defenseBonus = 1)
  val leatherBoots = new ArmorPiece(254, "Leather Boots", "Good for slippery decks.", EquipSlot.Shoes, defenseBonus = 1)

  val ironHelmet = new ArmorPiece(260, "Iron Helmet", "Dented but sturdy.", EquipSlot.Helmet, defenseBonus = 5)
  val ironChestplate = new ArmorPiece(261, "Iron Chestplate", "Heavy and reliable.", EquipSlot.Chestplate, defenseBonus = 12)
  val ironLeggings = new ArmorPiece(262, "Iron Leggings", "Clanks when you walk.", EquipSlot.Legging, defenseBonus = 8)
  val ironGloves = new ArmorPiece(263, "Iron Gloves", "Sounds... Ineffective?", EquipSlot.Gloves, defenseBonus = 3)
  val ironBoots = new ArmorPiece(264, "Iron Boots", "Clanks when you walk aswell.", EquipSlot.Shoes, defenseBonus = 3)

  val tekkariCap = new ArmorPiece(299, "Tekkari Cap", "Honor of a tekkari.", EquipSlot.Helmet, defenseBonus = 15, isSpecial = true)

  val leather: Vector[ArmorPiece] = Vector(
    leatherHelmet,
    leatherChestplate,
    leatherLeggings,
    leatherGloves,
    leatherBoots
  )

  val iron: Vector[ArmorPiece] = Vector(
    ironHelmet,
    ironChestplate,
    ironLeggings,
    ironGloves,
    ironBoots
  )

  val special: Vector[ArmorPiece] = Vector(
    tekkariCap
  )

  val all: Vector[ArmorPiece] = leather ++ iron ++ special

end ArmorCatalog