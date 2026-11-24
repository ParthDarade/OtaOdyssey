package adventure.Catalogs

import adventure.*

object ChestCatalog:

  // --- Wooden Chest: basic items + 1-2 drinks ---
  val woodenMainLoots: Vector[LootEntry] = Vector(
    new LootEntry(FoodCatalog.apple, 30),
    new LootEntry(FoodCatalog.banana, 20),
    new LootEntry(WeaponCatalog.cutlass, 10),
    new LootEntry(WeaponCatalog.spear, 6),
    new LootEntry(ArmorCatalog.leatherHelmet, 10),
    new LootEntry(ArmorCatalog.leatherChestplate, 8),
    new LootEntry(ArmorCatalog.leatherLeggings, 9),
    new LootEntry(ArmorCatalog.leatherGloves, 12),
    new LootEntry(ArmorCatalog.leatherBoots, 12)
  )

  val woodenBeverageLoots: Vector[LootEntry] = Vector(
    new LootEntry(BeverageCatalog.beer, 30),
    new LootEntry(BeverageCatalog.rum, 20)
  )

  // --- Iron Chest: mid items + 2-3 drinks ---
  val ironMainLoots: Vector[LootEntry] = Vector(
    new LootEntry(FoodCatalog.banana, 15),
    new LootEntry(WeaponCatalog.cutlass, 12),
    new LootEntry(WeaponCatalog.spear, 12),
    new LootEntry(ArmorCatalog.ironHelmet, 12),
    new LootEntry(ArmorCatalog.ironChestplate, 8),
    new LootEntry(ArmorCatalog.ironLeggings, 10),
    new LootEntry(ArmorCatalog.ironGloves, 14),
    new LootEntry(ArmorCatalog.ironBoots, 14)
  )

  val ironBeverageLoots: Vector[LootEntry] = Vector(
    new LootEntry(BeverageCatalog.rum, 20),
    new LootEntry(BeverageCatalog.whiskey, 25),
    new LootEntry(BeverageCatalog.vodka, 20)
  )

  // --- Diamond Chest: high-tier / special items + 3-4 drinks ---
  val diamondMainLoots: Vector[LootEntry] = Vector(
    new LootEntry(FoodCatalog.beefWellington, 25),
    new LootEntry(ArmorCatalog.ironChestplate, 10),
    new LootEntry(ArmorCatalog.ironLeggings, 10),
    new LootEntry(WeaponCatalog.totallyNormalStick, 6),
    new LootEntry(WeaponCatalog.legendaryBlade, 4),
    new LootEntry(ArmorCatalog.tekkariCap, 5)
  )

  val diamondBeverageLoots: Vector[LootEntry] = Vector(
    new LootEntry(BeverageCatalog.whiskey, 20),
    new LootEntry(BeverageCatalog.vodka, 20),
    new LootEntry(BeverageCatalog.mysteriousTekkariCoctail, 25)
  )

  // --- Chest Definitions ---
  val woodenChest: Chest = new Chest(
    id = 500,
    name = "Wooden Chest",
    description = "A weathered wooden chest washed up from an old wreck.",
    mimicChance = 0.15,
    mimicDamage = 20,
    mainLoots = woodenMainLoots,
    beverageLoots = woodenBeverageLoots,
    minBeverages = 1,
    maxBeverages = 2
  )

  val ironChest: Chest = new Chest(
    id = 501,
    name = "Iron Chest",
    description = "A heavy iron-bound chest with a sturdy lock.",
    mimicChance = 0.35,
    mimicDamage = 30,
    mainLoots = ironMainLoots,
    beverageLoots = ironBeverageLoots,
    minBeverages = 2,
    maxBeverages = 3
  )

  val diamondChest: Chest = new Chest(
    id = 502,
    name = "Diamond Chest",
    description = "Gleaming and tempting—almost too tempting.",
    mimicChance = 0.75,
    mimicDamage = 45,
    mainLoots = diamondMainLoots,
    beverageLoots = diamondBeverageLoots,
    minBeverages = 3,
    maxBeverages = 4
  )

  val all: Vector[Chest] = Vector(woodenChest, ironChest, diamondChest)

end ChestCatalog