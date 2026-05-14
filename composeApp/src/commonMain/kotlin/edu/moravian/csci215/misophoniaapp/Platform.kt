package edu.moravian.csci215.misophoniaapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform