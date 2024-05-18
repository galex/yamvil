package dev.galex.yamvil.models.base


import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ConsumableTest {

    @Test
    fun `Consumable - Not consumed`() {
        val consumable = Consumable("Hello World")
        assertTrue(consumable.consumed.not())
    }

    @Test(expected = IllegalStateException::class)
    fun `Consumable - Consumed`() {
        val consumable = Consumable("Hello World")
        assertTrue(consumable.consumed.not())
        val consumed = consumable.consume()
        assertEquals(consumed, "Hello World")
        assertTrue(consumable.consumed)
        consumable.consume()
    }

    @Test
    fun `Consumable - Peeked`() {
        val consumable = Consumable("Hello World")
        assertTrue(consumable.consumed.not())
        consumable.consume()
        assertTrue(consumable.consumed)
        val peeked = consumable.peek()
        assertTrue(peeked == "Hello World")
    }
}