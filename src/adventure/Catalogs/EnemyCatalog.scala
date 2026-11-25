package adventure.Catalogs

import adventure.*

object EnemyCatalog {

  val pirate: EnemyType = new EnemyType(
    name = "Parti Dörade",
    description = "A scruffy pirate swings over from another ship, cutlass drawn.",
    maxHp = 30,
    armor = 2,
    attack = 5,
    accuracy = 0.80,
    chestDrops = Vector(
      new ChestDrop(ChestCatalog.woodenChest, 0.50),
      new ChestDrop(ChestCatalog.ironChest, 0.10),
      new ChestDrop(ChestCatalog.diamondChest, 0.30)
    ),
    itemDrops = Vector(
      new ItemDrop(QuestItemsCatalog.compass, 1),
      new ItemDrop(BeverageCatalog.rum, 0.50)
    ),
    dialogs = Map(
      DialogTrigger.Appear -> Vector("Hand over the goods or walk the plank!", "This ship is ours now!"),
      DialogTrigger.Attack -> Vector("Yarr!", "Feel the edge of my blade!"),
      DialogTrigger.Hurt   -> Vector("Ye scratched me!", "Ow, me favourite arm!"),
      DialogTrigger.Defeat -> Vector("Tell me mother I was brave...", "Blast it all...")
    )
  )

  val student: EnemyType = new EnemyType(
    name = "Exhausted Student",
    description = "A pale student crawls onto the deck, clutching a half-finished lab report.",
    maxHp = 20,
    armor = 1,
    attack = 6,
    accuracy = 0.90,
    stealChance = 0.50,
    chestDrops = Vector(
      new ChestDrop(ChestCatalog.woodenChest, 0.60)
    ),
    itemDrops = Vector(
      new ItemDrop(QuestItemsCatalog.tekkariDiploma, 1),
      new ItemDrop(BeverageCatalog.tapWater, 0.50)
    ),
    dialogs = Map(
      DialogTrigger.Appear -> Vector("Is that... a drink? I haven't slept in three days.", "Do you have a pen? And a drink?"),
      DialogTrigger.Attack -> Vector("My deadline was yesterday!", "I just need a little energy!"),
      DialogTrigger.Hurt   -> Vector("This is worse than the exam!", "Ow! I have a presentation tomorrow!"),
      DialogTrigger.Defeat -> Vector("At least... the exam is over...", "Tell the professor, I tried...")
    )
  )

  val ronnin: EnemyType = new EnemyType(
    name = "Ronnin",
    description = "A disgruntled arts student who failed his course and is hunting the professor for revenge.",
    maxHp = 75,
    armor = 3,
    attack = 14,
    accuracy = 0.85,
    pierce = 0.15,
    stealChance = 0.15,
    openingDamage = 0,
    chestDrops = Vector(
      new ChestDrop(ChestCatalog.woodenChest, 0.70),
      new ChestDrop(ChestCatalog.ironChest, 0.25)
    ),
    itemDrops = Vector(
      new ItemDrop(QuestItemsCatalog.rejectedPortfolio, 1),
      new ItemDrop(BeverageCatalog.beer, 0.50)
    ),
    dialogs = Map(
      DialogTrigger.Appear -> Vector(
        "You'are with the faculty, aren't you?! Tell the professor my portfolio deserved a pass!",
        "Failed?! On an interpretive critique?! I'll show you critical theory!"
      ),
      DialogTrigger.Attack -> Vector(
        "Take this pure, unfiltered artistic rage!",
        "My thesis was ahead of its time!"
      ),
      DialogTrigger.Hurt -> Vector(
        "Ugh... this hurts almost as much as that F on my transcript!",
        "You're grading me harsher than he did!"
      ),
      DialogTrigger.Defeat -> Vector(
        "Fine... I'll retake the course next semester...",
        "Tell the professor... I'm filing an appeal..."
      )
    )
  )
  /** Only appears from a mimic chest. Its opening damage comes from the chest. */
  val mimic: EnemyType = new EnemyType(
    name = "Mimic",
    description = "The chest sprouts teeth and a long, hungry tongue!",
    maxHp = 35,
    armor = 3,
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
  val wanderers: Vector[EnemyType] = Vector(pirate, student, ronnin)

  val all: Vector[EnemyType] = Vector(pirate, student, ronnin, mimic)

}