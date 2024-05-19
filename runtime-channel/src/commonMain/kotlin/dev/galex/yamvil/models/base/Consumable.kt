package dev.galex.yamvil.models.base

/**
 * A class that wraps an element that should be consumed only once.
 * @param T the type of the element
 * @property element the element to wrap
 */
data class Consumable<out T>(private val element: T) {

    var consumed: Boolean = false
        private set

    fun peek(): T = element

    @Throws(IllegalStateException::class)
    fun consume(): T {
        if(consumed) throw IllegalStateException("The element has already been consumed")
        consumed = true
        return element
    }
}