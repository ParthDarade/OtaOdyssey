package adventure

import scala.io.StdIn
import scala.util.Random

class Combat(
  val player: Player,
  val enemy: Enemy,
  val rng: Random = new Random()
):

  private val CommandList =
    "Commands: attack | open bag | open <category> | eat <item> | drink <item> | equip <item> | status | quit"

  def isFinished: Boolean = !player.isAlive || !enemy.isAlive

  def playerWon: Boolean = enemy.hp <= 0 && player.isAlive

  def run(): Unit =
    println()
    println("=== COMBAT ===")
    println(CommandList)
    println()
    start().foreach(println)
    println()
    println(player.status)

    while !isFinished && !player.hasQuit do
      print("\n> ")
      val rawInput = Option(StdIn.readLine())
      if rawInput.isEmpty then
        player.quit()
        println("Exiting combat.")
      else
        rawInput.foreach(_ => handleCommand(_))

    println()
    println("=== COMBAT OVER ===")
    if playerWon then
      println("Result: Victory!")
    else if !player.isAlive then
      println("Result: Defeat!")
    else
      println("Result: Ended.")

  private def handleCommand(input: String): Unit =
    val parts = input.split("\\s+", 2)
    val cmd = parts(0).toLowerCase
    val arg = if parts.length > 1 then parts(1).trim else ""

    cmd match
      case "quit" | "q" =>
        player.quit()
        println("Exiting combat.")

      case "status" | "s" =>
        println(player.status)
        println(s"${enemy.name}: ${enemy.hp}/${enemy.maxHp} HP | Armor: ${enemy.armor} | Attack: ${enemy.attack}")
        println(CommandList)

      case "open" =>
        val lower = arg.toLowerCase
        if arg.isEmpty || lower == "bag" || lower == "inventory" || lower == "inv" then
          println(openBag())
        else
          println(openCategory(arg))
        println(CommandList)

      case "bag" | "i" | "inv" =>
        println(openBag())
        println(CommandList)

      case "equip" | "e" =>
        if arg.isEmpty then
          println("Equip what? Type: equip <item name>")
        else
          player.inventory.findByName(arg) match
            case Some(eq: Equipment) =>
              if player.inventory.equip(eq) then
                println(s"Equipped ${eq.name} (${eq.slot.displayName}).")
                println(player.status)
              else
                println(s"Could not equip ${eq.name}.")
            case Some(other) =>
              println(s"${other.name} is not equipment.")
            case None =>
              println(s"No item named '$arg' in your bag.")
        println(CommandList)

      case "eat" =>
        if arg.isEmpty then
          println("Eat what? Type: eat <food name>")
        else
          player.inventory.findByName(arg) match
            case Some(food: Food) =>
              consumeTurn(food).foreach(println)
            case Some(other) =>
              println(s"${other.name} is not food.")
            case None =>
              println(s"No food named '$arg' in your bag.")
        if !isFinished && !player.hasQuit then println(CommandList)

      case "drink" =>
        if arg.isEmpty then
          println("Drink what? Type: drink <beverage name>")
        else
          player.inventory.findByName(arg) match
            case Some(bev: Beverage) =>
              consumeTurn(bev).foreach(println)
            case Some(other) =>
              println(s"${other.name} is not a beverage.")
            case None =>
              println(s"No beverage named '$arg' in your bag.")
        if !isFinished && !player.hasQuit then println(CommandList)

      case "attack" | "a" =>
        attackTurn().foreach(println)
        if !isFinished && !player.hasQuit then println(CommandList)

      case "" =>
        println(CommandList)

      case _ =>
        println("Unknown command.")
        println(CommandList)

  def start(): Vector[String] =
    val lines = collection.mutable.Buffer[String]()
    lines += s"Combat begins against ${enemy.name} (${enemy.hp}/${enemy.maxHp} HP)."
    enemy.dialog(DialogTrigger.Appear, rng).foreach(line => lines += s"""${enemy.name}: "$line"""")

    if enemy.openingDamage > 0 && player.isAlive then
      player.takeDamage(enemy.openingDamage)
      lines += s"${enemy.name} strikes first for ${enemy.openingDamage} damage! (${player.hp}/${player.maxHp} HP)"

    lines.toVector

  def openBag(): String =
    player.inventory.openBag

  def openCategory(category: String): String =
    player.inventory.openCategory(category)

  def openInventory(): String =
    player.inventory.describe

  def attackTurn(): Vector[String] =
    if isFinished then return Vector("Combat has already ended.")
    finishRound(playerAttack())

  def consumeTurn(consumable: Consumable): Vector[String] =
    if isFinished then return Vector("Combat has already ended.")
    finishRound(playerConsume(consumable))

  private def finishRound(actionLines: Vector[String]): Vector[String] =
    val lines = collection.mutable.Buffer[String]()
    lines ++= actionLines

    if !enemy.isAlive then
      lines ++= onEnemyDefeated()
      player.tick()
      return lines.toVector

    lines ++= enemyTurn()

    if !player.isAlive then
      lines += s"You were defeated by ${enemy.name}."

    player.tick()
    lines.toVector

  private def playerAttack(): Vector[String] =
    val lines = collection.mutable.Buffer[String]()

    if rng.nextDouble() >= player.hitChance then
      lines += s"You attack ${enemy.name} and miss."
      return lines.toVector

    val reducedArmor = (enemy.armor * (1.0 - player.pierce)).round.toInt
    val damage = math.max(1, player.attackPower - reducedArmor)
    enemy.takeDamage(damage)
    lines += s"You hit ${enemy.name} for $damage damage. (${enemy.hp}/${enemy.maxHp} HP)"

    if enemy.isAlive && enemy.isHurt then
      enemy.dialog(DialogTrigger.Hurt, rng).foreach(line => lines += s"""${enemy.name}: "$line"""")

    lines.toVector

  private def playerConsume(consumable: Consumable): Vector[String] =
    consumable match
      case food: Food =>
        player.eat(food) match
          case Some(healed) =>
            Vector(s"You eat ${food.name} and heal $healed HP. (${player.hp}/${player.maxHp} HP)")
          case None =>
            Vector(s"You don't have ${food.name}.")

      case beverage: Beverage =>
        player.drink(beverage, rng) match
          case Some(effect) =>
            Vector(s"You drink ${beverage.name} and gain ${effect.describe}.")
          case None =>
            Vector(s"You don't have ${beverage.name}.")

  private def enemyTurn(): Vector[String] =
    val lines = collection.mutable.Buffer[String]()
    val drinks = player.inventory.beverages

    if enemy.stealChance > 0.0 && drinks.nonEmpty && rng.nextDouble() < enemy.stealChance then
      val stolen = drinks(rng.nextInt(drinks.size))
      player.inventory.remove(stolen)
      lines += s"${enemy.name} steals ${stolen.name} from your bag!"
      return lines.toVector

    enemy.dialog(DialogTrigger.Attack, rng).foreach(line => lines += s"""${enemy.name}: "$line"""")

    if rng.nextDouble() >= enemy.accuracy then
      lines += s"${enemy.name} attacks and misses."
      return lines.toVector

    val reducedArmor = (player.armor * (1.0 - enemy.pierce)).round.toInt
    val damage = math.max(1, enemy.attack - reducedArmor)
    player.takeDamage(damage)
    lines += s"${enemy.name} hits you for $damage damage. (${player.hp}/${player.maxHp} HP)"
    lines.toVector

  private def onEnemyDefeated(): Vector[String] =
    val lines = collection.mutable.Buffer[String]()
    lines += s"${enemy.name} is defeated!"
    enemy.dialog(DialogTrigger.Defeat, rng).foreach(line => lines += s"""${enemy.name}: "$line"""")

    val drops = Loot.chestDrops(enemy.kind, rng)
    if drops.nonEmpty then
      player.inventory.addAll(drops)
      lines += s"Looted chests: ${drops.map(_.name).mkString(", ")}."

    lines.toVector

end Combat