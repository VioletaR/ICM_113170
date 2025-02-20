package ua.deti.icm.lab01.Unit2.Pathway1

fun nullOperaions() {
    var favoriteActor: String? = "Sandra Oh" // enabled null
    println(favoriteActor)
    println(favoriteActor?.length)

    println("Favorite actor is: null")
    favoriteActor = null
    println(favoriteActor)

    println(favoriteActor?.length) // safe call operator
//    println(favoriteActor!!.length) // This will throw a NullPointerException

    var lengthOfName = if (favoriteActor != null) {
        favoriteActor.length
    } else {
        0
    } // cool if assignment

    lengthOfName = favoriteActor?.length ?: 0   // Elvis operator , better if assignment

}

