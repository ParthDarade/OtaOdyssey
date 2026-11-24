package adventure.Catalogs

import adventure.*

object EnemyCatalog {

  val pirate: EnemyType = new EnemyType(
    name = "Matti Dörade",
    description = "A scruffy pirate swings over from another ship, cutlass drawn.",
    maxHp = 40,
    armor = 10,
    attack = 10,
    accuracy = 0.80,
    chestDrops = Vector(
      new ChestDrop(ChestCatalog.woodenChest, 0.50),
      new ChestDrop(ChestCatalog.ironChest, 0.10),
      new ChestDrop(ChestCatalog.diamondChest, 0.30)
    ),
    dialogs = Map(
      DialogTrigger.Appear -> Vector("Hand over the goods or walk the plank!", "This ship is ours now!"),
      DialogTrigger.Attack -> Vector("Yarr!", "Feel the edge of my blade!"),
      DialogTrigger.Hurt   -> Vector("Ye scratched me!", "Ow, me favourite arm!"),
      DialogTrigger.Defeat -> Vector("Tell me mother I was brave...", "Blast it all...")
    )
  )

  val seaSerpent: EnemyType = new EnemyType(
    name = "Sea Serpent",
    description = "A scaly head rises out of the waves, hissing.",
    maxHp = 80,
    armor = 20,
    attack = 18,
    accuracy = 0.75,
    chestDrops = Vector(
      new ChestDrop(ChestCatalog.ironChest, 0.40),
      new ChestDrop(ChestCatalog.diamondChest, 0.15)
    ),
    dialogs = Map(
      DialogTrigger.Appear -> Vector("SSSSSS...", "The water around the ship starts to boil."),
      DialogTrigger.Attack -> Vector("HISSSS!", "The serpent lashes its tail."),
      DialogTrigger.Hurt   -> Vector("The serpent shrieks.", "Dark blood stains the water."),
      DialogTrigger.Defeat -> Vector("The serpent sinks beneath the waves.")
    )
  )

  /** Steals a beverage and drinks it on the spot. */
  val student: EnemyType = new EnemyType(
    name = "Exhausted Student",
    description = "A pale student crawls onto the deck, clutching a half-finished lab report.",
    maxHp = 30,
    armor = 5,
    attack = 6,
    accuracy = 0.90,
    stealChance = 0.50,
    chestDrops = Vector(
      new ChestDrop(ChestCatalog.woodenChest, 0.60)
    ),
    dialogs = Map(
      DialogTrigger.Appear -> Vector("Is that... a drink? I haven't slept in three days.", "Do you have a pen? And a drink?"),
      DialogTrigger.Attack -> Vector("My deadline was yesterday!", "I just need a little energy!"),
      DialogTrigger.Hurt   -> Vector("This is worse than the exam!", "Ow! I have a presentation tomorrow!"),
      DialogTrigger.Defeat -> Vector("At least... the exam is over...", "Tell the professor I tried...")
    )
  )

  /** Only appears from a mimic chest. Its opening damage comes from the chest. */
  val mimic: EnemyType = new EnemyType(
    name = "Mimic",
    description = "The chest sprouts teeth and a long, hungry tongue!",
    maxHp = 35,
    armor = 8,
    attack = 12,
    accuracy = 0.85,
    dialogs = Map(
      DialogTrigger.Appear -> Vector("The lid snaps open, revealing rows of teeth!"),
      DialogTrigger.Attack -> Vector("CHOMP!", "The mimic gnashes its teeth."),
      DialogTrigger.Hurt   -> Vector("The mimic wheezes and creaks."),
      DialogTrigger.Defeat -> Vector("The mimic falls apart into splinters.")
    )
  )

  /** The kinds that can attack the player at sea (the mimic is not one of them). */
  val wanderers: Vector[EnemyType] = Vector(pirate, seaSerpent, student)
  
  val all: Vector[EnemyType] = Vector(pirate, seaSerpent, student, mimic)

}