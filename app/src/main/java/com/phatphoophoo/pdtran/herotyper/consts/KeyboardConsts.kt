package com.phatphoophoo.pdtran.herotyper.consts

import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY

enum class BUTTONS(val id: Int) {
    BUTTON1(R.id.button1),
    BUTTON2(R.id.button2),
    BUTTON3(R.id.button3),
    BUTTON4(R.id.button4),
    BUTTON5(R.id.button5),
    BUTTON6(R.id.button6),
    BUTTON7(R.id.button7),
    BUTTON8(R.id.button8),
    BUTTON9(R.id.button9),
    BUTTON10(R.id.button10),
    BUTTON11(R.id.button11),
    BUTTON12(R.id.button12),
    BUTTON13(R.id.button13),
    BUTTON14(R.id.button14),
    BUTTON15(R.id.button15),
    BUTTON16(R.id.button16),
    BUTTON17(R.id.button17),
    BUTTON18(R.id.button18),
    BUTTON19(R.id.button19),
    BUTTON20(R.id.button20),
    BUTTON21(R.id.button21),
    BUTTON22(R.id.button22),
    BUTTON23(R.id.button23),
    BUTTON24(R.id.button24),
    BUTTON25(R.id.button25),
    BUTTON26(R.id.button26),
    BUTTON27(R.id.button27),
    BUTTON28(R.id.button28),
    BUTTON29(R.id.button29),
    BUTTON30(R.id.button30),
}

val qwerty: Map<Int, String> = mapOf(
    BUTTONS.BUTTON1.id to "Q",
    BUTTONS.BUTTON2.id to "W",
    BUTTONS.BUTTON3.id to "E",
    BUTTONS.BUTTON4.id to "R",
    BUTTONS.BUTTON5.id to "T",
    BUTTONS.BUTTON6.id to "Y",
    BUTTONS.BUTTON7.id to "U",
    BUTTONS.BUTTON8.id to "I",
    BUTTONS.BUTTON9.id to "O",
    BUTTONS.BUTTON10.id to "P",
    BUTTONS.BUTTON11.id to "A",
    BUTTONS.BUTTON12.id to "S",
    BUTTONS.BUTTON13.id to "D",
    BUTTONS.BUTTON14.id to "F",
    BUTTONS.BUTTON15.id to "G",
    BUTTONS.BUTTON16.id to "H",
    BUTTONS.BUTTON17.id to "J",
    BUTTONS.BUTTON18.id to "K",
    BUTTONS.BUTTON19.id to "L",
    BUTTONS.BUTTON20.id to "Z",
    BUTTONS.BUTTON21.id to "X",
    BUTTONS.BUTTON22.id to "C",
    BUTTONS.BUTTON23.id to "V",
    BUTTONS.BUTTON24.id to "B",
    BUTTONS.BUTTON25.id to "N",
    BUTTONS.BUTTON26.id to "M",
    BUTTONS.BUTTON27.id to "",
    BUTTONS.BUTTON28.id to "",
    BUTTONS.BUTTON29.id to "",
    BUTTONS.BUTTON30.id to ""
)
val colemak: Map<Int, String> = mapOf(
    BUTTONS.BUTTON1.id to "Q",
    BUTTONS.BUTTON2.id to "W",
    BUTTONS.BUTTON3.id to "F",
    BUTTONS.BUTTON4.id to "P",
    BUTTONS.BUTTON5.id to "G",
    BUTTONS.BUTTON6.id to "J",
    BUTTONS.BUTTON7.id to "L",
    BUTTONS.BUTTON8.id to "U",
    BUTTONS.BUTTON9.id to "Y",
    BUTTONS.BUTTON10.id to "",
    BUTTONS.BUTTON11.id to "R",
    BUTTONS.BUTTON12.id to "S",
    BUTTONS.BUTTON13.id to "T",
    BUTTONS.BUTTON14.id to "D",
    BUTTONS.BUTTON15.id to "H",
    BUTTONS.BUTTON16.id to "N",
    BUTTONS.BUTTON17.id to "E",
    BUTTONS.BUTTON18.id to "I",
    BUTTONS.BUTTON19.id to "O",
    BUTTONS.BUTTON20.id to "Z",
    BUTTONS.BUTTON21.id to "X",
    BUTTONS.BUTTON22.id to "C",
    BUTTONS.BUTTON23.id to "V",
    BUTTONS.BUTTON24.id to "B",
    BUTTONS.BUTTON25.id to "K",
    BUTTONS.BUTTON26.id to "M",
    BUTTONS.BUTTON27.id to "A",
    BUTTONS.BUTTON28.id to "",
    BUTTONS.BUTTON29.id to "",
    BUTTONS.BUTTON30.id to ""
)
val dvorak: Map<Int, String> = mapOf(
    BUTTONS.BUTTON1.id to "",
    BUTTONS.BUTTON2.id to "",
    BUTTONS.BUTTON3.id to "",
    BUTTONS.BUTTON4.id to "P",
    BUTTONS.BUTTON5.id to "Y",
    BUTTONS.BUTTON6.id to "F",
    BUTTONS.BUTTON7.id to "G",
    BUTTONS.BUTTON8.id to "C",
    BUTTONS.BUTTON9.id to "R",
    BUTTONS.BUTTON10.id to "L",
    BUTTONS.BUTTON11.id to "O",
    BUTTONS.BUTTON12.id to "E",
    BUTTONS.BUTTON13.id to "U",
    BUTTONS.BUTTON14.id to "I",
    BUTTONS.BUTTON15.id to "D",
    BUTTONS.BUTTON16.id to "H",
    BUTTONS.BUTTON17.id to "T",
    BUTTONS.BUTTON18.id to "N",
    BUTTONS.BUTTON19.id to "S",
    BUTTONS.BUTTON20.id to "J",
    BUTTONS.BUTTON21.id to "K",
    BUTTONS.BUTTON22.id to "X",
    BUTTONS.BUTTON23.id to "B",
    BUTTONS.BUTTON24.id to "M",
    BUTTONS.BUTTON25.id to "W",
    BUTTONS.BUTTON26.id to "V",
    BUTTONS.BUTTON27.id to "A",
    BUTTONS.BUTTON28.id to "",
    BUTTONS.BUTTON29.id to "Q",
    BUTTONS.BUTTON30.id to "Z"
)

val alphabets: MutableCollection<String> = mutableListOf(
    "A",
    "B",
    "C",
    "D",
    "E",
    "F",
    "G",
    "H",
    "J",
    "K",
    "L",
    "M",
    "N",
    "O",
    "P",
    "Q",
    "R",
    "S",
    "T",
    "U",
    "V",
    "W",
    "X",
    "Y",
    "Z"
)

val WORD_DICTIONARY: Map<GAME_DIFFICULTY, List<String>> = mapOf(
    GAME_DIFFICULTY.EASY to listOf(
        "glow",
        "height",
        "deal",
        "waste",
        "brown",
        "leash",
        "rule",
        "home",
        "pat",
        "block",
        "tune",
        "cut",
        "spread",
        "weave",
        "deer",
        "cell",
        "leaf",
        "pluck",
        "north",
        "round",
        "guess",
        "plant",
        "say",
        "twist",
        "fast",
        "veil",
        "heal",
        "fat",
        "day",
        "steel",
        "cry",
        "burn",
        "lodge",
        "air",
        "sign",
        "fate",
        "nun",
        "damn",
        "fax",
        "bond",
        "wild",
        "clash",
        "print",
        "seed",
        "calf",
        "jail",
        "hang",
        "lake",
        "grace",
        "face",
        "cage",
        "proud",
        "read",
        "van",
        "sink",
        "rub",
        "left",
        "good",
        "cream",
        "gift",
        "myth",
        "squash",
        "beer",
        "sin",
        "bell",
        "wake",
        "warn",
        "harm",
        "test",
        "zeal",
        "zombie"
    ),
    GAME_DIFFICULTY.MEDIUM to listOf(
        "correction",
        "exemption",
        "formation",
        "exclusive",
        "candidate",
        "difference",
        "episode",
        "organize",
        "accurate",
        "average",
        "horizon",
        "apathy",
        "particle",
        "bulletin",
        "medieval",
        "objective",
        "analyst",
        "shareholder",
        "consciousness",
        "permanent",
        "training",
        "transport",
        "voter",
        "glory",
        "support",
        "feature",
        "construct",
        "abuse",
        "tension",
        "import",
        "ticket",
        "railcar",
        "apple",
        "reserve",
        "wonder",
        "offer",
        "quota",
        "singer",
        "struggle",
        "taxi",
        "factor",
        "owner",
        "woman",
        "remain",
        "birthday",
        "obese",
        "outlet",
        "classroom",
        "meeting",
        "patrol",
        "retreat",
        "hostage",
        "riot",
        "enhance",
        "expand",
        "orange",
        "hallway",
        "shatter",
        "protest",
        "honor",
        "quarrel",
        "suffer",
        "feeling",
        "notice",
        "cover",
        "tourist",
        "desert",
        "borrow",
        "positive",
        "modernize",
        "director",
        "clarify",
        "omission",
        "assembly",
        "ambition",
        "confession",
        "attractive",
        "benefit",
        "innocent",
        "victory",
        "decorative",
    ),
    GAME_DIFFICULTY.HARD to listOf(
        "intermediate",
        "decoration",
        "unanimous",
        "execution",
        "variation",
        "institution",
        "acceptable",
        "coincidence",
        "notorious",
        "qualification",
        "electronics",
        "resignation",
        "continental",
        "initiative",
        "economist",
        "architecture",
        "demonstrator",
        "integrated",
        "positive",
        "modernize",
        "abortion",
        "director",
        "clarify",
        "omission",
        "assembly",
        "ambition",
        "confession",
        "attractive",
        "benefit",
        "innocent",
        "victory",
        "decorative",
        "ghostwriter",
        "factory",
        "basketball",
        "arrangement",
        "linear",
        "primary",
        "chemistry",
        "advantage",
        "direction",
        "discover",
        "recycle",
        "equation",
        "quotation",
        "domestic",
        "position",
        "grandfather",
        "undertake",
        "imposter",
        "approval",
        "urgency",
        "delicate",
        "variant",
        "sensation",
        "preference",
        "calendar",
        "tendency",
        "eternal",
        "overwhelm",
        "activate",
        "consider",
        "reproduce",
        "gallery",
        "rebellion",
        "qualify",
        "performance",
        "projection",
        "overeat",
        "reinforce",
        "offender",
        "attitude",
        "convenience",
        "barrier",
        "referral",
        "terminal",
        "formation",
        "acceptance",
        "liberal",
        "pioneer",
        "abnormal",
        "avenue",
        "unity",
        "premium",
        "mutual",
        "elaborate",
        "president",
        "miracle",
        "appearance",
        "magnetic",
        "separate",
        "speculate",
        "photograph",
        "admission",
        "telephone",
        "exception",
        "volcano",
        "shareholder",
        "creation",
        "transmission",
        "injury",
        "rehearsal",
        "radical",
        "explosion",
        "computing",
        "penalty",
        "dedicate",
        "condition",
        "convention",
        "laborer",
        "loyalty",
        "recommend",
        "orchestra",
        "sensitive",
        "encourage",
        "predator",
        "residence",
        "overall",
        "average",
        "orthodox",
        "abundant",
        "depression",
        "diagram",
        "correction",
        "citizen",
        "opposite",
        "serious",
        "tradition",
        "classify",
        "skeleton",
        "edition",
        "talkative",
        "approval",
        "presidency",
        "overcharge",
        "concentration",
        "craftsman",
        "disagreement",
        "gregarious",
        "continuation",
        "retirement",
        "willpower",
        "invisible",
        "frequency",
        "intervention",
        "partnership",
        "orientation",
        "consensus",
        "nationalist",
        "expectation",
        "conductor",
        "relinquish",
        "enthusiasm",
        "wisecrack",
        "contradiction",
        "integration",
        "contribution",
        "remunerate",
        "favourite",
        "colleague",
        "satisfied",
        "profession",
        "provision",
        "cooperate",
        "restoration",
        "battlefield",
        "paralyzed",
        "chimpanzee",
        "foundation",
        "parameter",
        "spokesperson",
        "participate",
        "embarrassment",
        "obligation",
        "excavation",
        "constitution",
        "publisher",
        "dependence",
        "ambiguous",
        "indication",
        "hemisphere",
        "isolation",
        "threshold",
        "distortion",
        "recording",
        "conscious",
        "negligence",
        "reasonable",
        "resolution",
        "interrupt",
        "policeman",
        "cigarette",
        "breakfast",
        "fisherman",
        "cellphone",
        "illustrate",
        "magnitude",
        "deteriorate",
        "superintendent",
        "background",
        "cooperative",
        "temptation",
        "volunteer",
        "prescription",
        "spontaneous",
        "observation",
        "relaxation",
        "proportion",
        "celebration",
        "discourage",
        "revolutionary",
        "reproduction",
        "attachment",
        "lifestyle",
        "recognize",
        "compromise",
        "requirement",
        "selection",
        "convulsion",
        "grandmother",
        "executive",
        "transaction",
        "architect",
        "plaintiff",
        "circulation",
        "adventure",
        "cathedral",
        "performer",
        "ostracize",
        "leadership",
        "automatic",
        "atmosphere",
        "competence",
        "guideline",
        "unpleasant",
        "practical",
        "incongruous",
        "stimulation",
        "investment",
        "precedent",
        "entertainment",
        "sympathetic",
        "hospitality",
        "defendant",
        "instrument",
        "deviation",
        "leftovers",
        "distribute",
        "federation",
        "extension",
        "inhibition",
        "sentiment",
        "television",
        "advertising",
        "education",
        "mechanical",
        "timetable",
        "production",
        "conversation",
        "permission",
        "demonstrate",
        "multimedia",
        "character",
        "demonstration",
        "aluminium"
    )
)
