package adventure.Catalogs
import adventure.*

object FoodCatalog:

  val apple = Food(100, "Apple", "A crisp red apple.", baseHeal = 10)
  val banana = Food(101, "Banana", "A delicious banana.", baseHeal = 20)
  val beefWellington = Food(102, "Beef Wellington", "How did a tekkari afford this??", baseHeal = 50)

  val all: Vector[Food] = Vector(apple, banana, beefWellington)