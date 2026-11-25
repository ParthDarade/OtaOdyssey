package adventure

sealed trait DialogTrigger
object DialogTrigger {
  object Appear extends DialogTrigger
  object Attack extends DialogTrigger
  object Hurt   extends DialogTrigger
  object Defeat extends DialogTrigger

  val values: Vector[DialogTrigger] = Vector(Appear, Attack, Hurt, Defeat)
}
