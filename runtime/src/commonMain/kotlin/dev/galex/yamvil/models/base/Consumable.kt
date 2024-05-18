package dev.galex.yamvil.models.base

data class Consumable<out T>(private val element: T) {

    var consumed: Boolean = false
        private set

    fun peek(): T = element

    fun consume(): T? {
        if(consumed) return null
        consumed = true
        return element
    }
}