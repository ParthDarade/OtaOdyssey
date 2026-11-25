package adventure

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
  val itemDrops: Vector[ItemDrop] = Vector.empty,
  val dialogs: Map[DialogTrigger, Vector[String]] = Map.empty
)

