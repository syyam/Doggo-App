package com.syyam.doggobreedapp.model.breeds

import com.google.gson.annotations.SerializedName

data class Breeds(

    @SerializedName("message") var message: ArrayList<String> = arrayListOf(
        "affenpinscher",
        "african",
        "airedale",
        "akita",
        "appenzeller",
        "australian",
        "shepherd",
        "basenji",
        "beagle",
        "bluetick",
        "borzoi",
        "bouvier",
        "boxer",
        "brabancon",
        "briard",
        "buhund",
        "norwegian",
        "bulldog",
        "boston",
        "english",
        "french",
        "bullterrier",
        "staffordshire",
        "cairn",
        "cattledog",
        "australian",
        "chihuahua",
        "chow",
        "clumber",
        "cockapoo",
        "collie",
        "border",
        "coonhound",
        "corgi",
        "cotondetulear",
        "dachshund",
        "dalmatian",
        "dane",
        "great",
        "deerhound",
        "scottish",
        "dhole",
        "dingo",
        "doberman",
        "entlebucher",
        "eskimo",
        "finnish",
        "lapphund",
        "frise",
        "bichon",
        "germanshepherd",
        "greyhound",
        "italian",
        "groenendael",
        "havanese",
        "husky",
        "keeshond",
        "kelpie",
        "komondor",
        "kuvasz",
        "labrador",
        "leonberg",
        "lhasa",
        "malamute",
        "malinois",
        "maltese",
        "mexicanhairless",
        "mix",
        "newfoundland",
        "otterhound",
        "papillon",
        "pekinese",
        "pembroke",
        "pinscher",
        "miniature",
        "pitbull",
        "pointer",
        "german",
        "germanlonghair",
        "pomeranian",
        "poodle",
        "miniature",
        "standard",
        "toy",
        "pug",
        "schnauzer",
        "giant",
        "miniature",
        "setter",
        "english",
        "gordon",
        "irish",
        "sheepdog",
        "english",
        "shetland",
        "shiba",
        "shihtzu",
        "spaniel",
        "blenheim"
    ),
    @SerializedName("status") var status: String? = null

)