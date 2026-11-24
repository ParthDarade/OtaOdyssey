package adventure

class Position( val latitude: Double, val longitude: Double):
  def LatToDDMMSS: (Int,Double)=
    (latitude.abs.toInt, (latitude- latitude.floor)/60)
  def LongToDDMMSS: (Int,Double)=
    (longitude.abs.toInt, (longitude-longitude.floor)/60)
