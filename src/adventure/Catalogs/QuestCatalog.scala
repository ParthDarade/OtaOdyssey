package adventure.Catalogs
import adventure.Quest
import adventure.Enemy
object QuestCatalog:
  val Goblin = Enemy(EnemyCatalog.pirate)
  val Ronin = Enemy(EnemyCatalog.student)
  val TokyoDialogues = Vector(

  "Professor Mori: Ah! You must be the captain I was expecting.\nProfessor Hidetaka Mori, at your service. I am most grateful that you have come all this way." ,

  "You: What brings a professor to such a remote island?",

  "Professor Mori: An unfortunate matter concerning a former student of mine. \nA rather talented young man, I must say, though perhaps not the most disciplined." ,

  "You: What happened to him?",

  "Professor Mori: I taught him in my arts course several years ago. His work was... unconventional.\n He had a peculiar fascination with samurai, Japanese history and the romanticism of the wandering warrior.",

  "You: And?",

  "Professor Mori: I failed him. His final submission was, frankly, quite dreadful.\n I could hardly award him a passing grade.",

  "You: The professor pauses and adjusts his spectacles.",

  "Unfortunately, he did not take the news particularly well. He disappeared shortly afterwards.\n We recently learned that he has established himself on a neighbouring island, calling himself a Rōnin.",

  "You: A Rōnin?",

  "Indeed! He has gathered a small following and appears to have taken control of the island. \nMerchant vessels have reported being harassed, and some have even been forced to pay passage fees.",

  "You: And what do you expect me to do?",

  "Professor Mori: I would ask you to find him and persuade him to abandon this foolish enterprise. \nI should like to speak with my former student once more, preferably without a sword being involved.",

  "Professor Mori: He calls himself a masterless warrior, but I suspect he is still that \nsame frustrated young artist. Perhaps you might succeed where I have failed.",

  "Professor Mori: Will you help an old professor settle an unfinished matter, Captain?",

  "Professor Mori:Excellent! You shall find his island to the east. And Captain... do be careful. \nHe has always been rather dramatic."
  )


  val SirenDialogues = Vector(

  "Teekkari: HEI! Captain! Over here! We desperately need your help!",

  "You: What on earth happened here?",

  "Teekkari: A goblin! A bloody goblin has seized our sauna!\nWe were having a perfectly respectable sauna evening when the little beast came out of the forest and claimed the building as his own.",

  "You: And you simply let him have it?",

  "Teekkari: Let him?! He threw our chairman out, stole our vihta and barricaded the entrance!\nHe even demanded that we pay him in sausages for every minute we wish to spend inside!",

  "You: Why don't you just throw him out?",

  "Teekkari: Have you seen the thing? He's armed with a wooden ladle, knows the forest better than we do, and keeps throwing hot water at anyone who approaches!\nBesides, we're not exactly dressed for combat.",

  "You: So what do you expect me to do?",

  "Teekkari: We need someone with a little more authority. And preferably someone who isn't afraid of a goblin with a ladle.",

  "You: Where can I find him?",

  "Teekkari: He's inside the sauna, naturally. The little bastard has made himself quite comfortable.\nWe can hear him singing in there. Terribly, I might add.",

  "You: And you want me to throw him out?",

  "Teekkari: Precisely! Teach that miserable creature a lesson! Reclaim our sauna, and the guild shall be forever indebted to you.",

  "Teekkari: We'll even let you enjoy the first round of löyly. On the house!",

  "You: Very well. I'll see what I can do.",

  "Teekkari: EXCELLENT! Finally, some action! Now, Captain, go and reclaim our sauna!\nAnd do be careful with the ladle. It's guild property."
  )

  val SirenQuest = Quest(
    name = "The Sauna Goblin",
    description = "You approach a small rocky island and notice a wooden sauna nestled between the pine trees.\\nSeveral semi-naked students wearing nothing but towels and Teekkari caps are gathered outside. They look rather miserable.\\nOne of them notices your vessel and rushes towards the shore.",
    dialogues = SirenDialogues,
    enemy = Goblin
  )
  val TokyoQuest = Quest( name = "Tokyo Quest",
    description = "You bring your vessel alongside a small wooden pier and step ashore.\nA narrow stone path leads from the beach towards a traditional Japanese residence. An elderly gentleman stands outside, leaning on a wooden walking stick.\nUpon seeing you, he approaches.", dialogues = TokyoDialogues,
    enemy = Ronin
    )