package adventure
import scala.math.*

class GameMap:
  private val Width = 1066.0
  private val Height = 835.0
  private val VisibleRange = 5
  // Map projection based on the geographical grid.
  // Reference: 24°00'E, 59°40'N at pixel (155, 570).
  def toPixels(position: Position): (Double,Double)=
    val x = 155.0 + (position.longitude - 24.0)*550.0
    val y = 570.0 + (59.6667- position.latitude)*1020.0
    (x,y)
  def fromPixels(x: Double, y: Double): Position =
    val longitude = 24.0 + (x - 155.0) / 550.0
    val latitude = 59.6667 - (y - 570.0) / 1020.0
    Position(latitude, longitude)

  def pointInPolygon(point: (Double,Double), polygon: Vector[(Double,Double)]): Boolean=
    val (px, py) = point
    var inside = false
    for i <- polygon.indices do
      val j = (i + 1) % polygon.size
      val (xi,yi) = polygon(i)
      val (xj,yj) = polygon(j)
      if ((yi >py) != (yj>py))&& (px < (xj - xi) * (py - yi) / (yj - yi) + xi)
      then inside = !inside
    inside

  def insideBounds(position: Position): Boolean =
    val (x, y) = toPixels(position)
    x >= 0.0 && x <= Width && y >= 0.0 && y <= Height

  def isLand(position: Position): Boolean =
    if !insideBounds(position) then
      true
    else
      val point = toPixels(position)
      land.exists(polygon => pointInPolygon(point, polygon))

  // Earth navigation using the great circle destination formula.
  // in nautical miles.
  private def destination(
    start: Position,
    heading: Double,
    distanceNm: Double
  ): Position =
    val earthRadiusNm = 3440.065
    val angularDistance = distanceNm / earthRadiusNm

    val bearing = toRadians(heading)
    val lat1 = toRadians(start.latitude)
    val lon1 = toRadians(start.longitude)

    val lat2 = asin(
      sin(lat1) * cos(angularDistance) +
      cos(lat1) * sin(angularDistance) * cos(bearing)
    )

    val lon2 = lon1 + atan2(
      sin(bearing) * sin(angularDistance) * cos(lat1),
      cos(angularDistance) - sin(lat1) * sin(lat2)
    )

    Position(
      toDegrees(lat2),
      toDegrees(lon2)
    )

  def distanceNmBetween(a: Position, b: Position): Double =
    val earthRadiusNm = 3440.065
    val dLat = toRadians(b.latitude - a.latitude)
    val dLon = toRadians(b.longitude - a.longitude)
    val lat1 = toRadians(a.latitude)
    val lat2 = toRadians(b.latitude)
    val h = sin(dLat / 2) * sin(dLat / 2) +
            cos(lat1) * cos(lat2) * sin(dLon / 2) * sin(dLon / 2)
    2.0 * earthRadiusNm * asin(min(1.0, sqrt(h)))

  def canTravel (start: Position, heading:Double, distanceNm: Double): Boolean=
    if distanceNm < 0 then return false
     //Check for all 0.1 Nautical mile steps if they pass through land.
    val steps = max(1, ceil(distanceNm/0.1).toInt)
    (1 to steps).forall{step=>
      val travelled = distanceNm * step/steps
      val point = destination(start, heading,travelled)
      !isLand(point)
    }

  def heading(pos1: Position, pos2: Position): Double =
    val lat1 = toRadians(pos1.latitude)
    val lat2 = toRadians(pos2.latitude)
    val dLon = toRadians(pos2.longitude - pos1.longitude)

    val y = sin(dLon) * cos(lat2)
    val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(dLon)

    val degrees = toDegrees(atan2(y, x))
    val normalized= (degrees + 360.0) % 360.0
    ceil(normalized * 10.0) / 10.0


  def checkSurroundings(pos: Position, thresholdNm: Double): Unit =
    landmarks.find((_, target) => distanceNmBetween(pos, target) <= thresholdNm).foreach { (name, targetloc) =>

      println(s"Spotted $name at ${this.heading(pos,targetloc)}")
    }

  def travel(start: Position, heading: Double, distanceNm: Double): Option[Position] =
    if !canTravel(start,heading,distanceNm) then
      println("I'm afraid the sea does not extend in that direction, Captain. Might you be obstructed?")
      None
    else
      val nextPos = destination(start,heading,distanceNm)
      checkSurroundings(nextPos, VisibleRange)
      Some(nextPos)
      



  private val land: Vector[Vector[(Double, Double)]] = Vector(
    // Poliisi coast
    Vector(
      (728, 0),
      (713, 55),
      (646, 76),
      (614, 69),
      (635, 45),
      (641, 0),
      (728,0)
    ),
    // Finland coast
    Vector(
      (558, 0),
      (565, 16),
      (578, 58),
      (496, 93),
      (474, 153),
      (380, 250),
      (337 ,228),
      (341 ,143),
      (0 ,313),
      (558, 0)

    ),
    // Estonian mainland
    Vector(
      (299 ,835),
      (336 ,791),
      (346 ,764),
      (380 ,766),
      (445 ,793),
      (452 ,777),
      (499 ,803),
      (498 ,778),
      (529 ,776),
      (570 ,788),
      (586 ,753),
      (570 ,675),
      (616 ,686),
      (632 ,731),
      (661 ,744),
      (687 ,725),
      (725 ,733),
      (746 ,752),
      (764 ,735),
      (752 ,699),
      (778 ,725),
      (817 ,712),
      (868 ,730),
      (934 ,716),
      (952 ,684),
      (1034 ,688),
      (1052 ,645),
      (1066 ,653),
      (299 ,835)
    ),

    // Island of the Sirens
    Vector(
      (426, 569),
      (413, 560),
      (428, 534),
      (438, 539),
      (451, 529),
      (455, 554),
      (431, 569),
      (426, 569)
    ),
    // TOKYO Tower Island
    Vector(
      (592, 387),
      (576, 375),
      (585, 342),
      (602, 344),
      (616, 357),
      (611, 380),
      (597, 387),
      (592, 387)
    )
  )
  private val landmarks: List[(String, Position)] = List(
    ("Otaniemi", fromPixels(473.0, 75.0)),
    ("Island of the Sirens", fromPixels(433.0, 550.0)),
    ("TOKYO Tower Island", fromPixels(596.0, 365.0))
  )