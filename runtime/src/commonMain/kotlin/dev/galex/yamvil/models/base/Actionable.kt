package dev.galex.yamvil.models.base

/**
 * Interface for classes that can be acted upon for one-off actions.
 * @param Action the type of the action
 */
interface Actionable<Action> {
    val action: Consumable<Action>?

    /**
     * Consumes the action if it is not null and not consumed yet.
     */
    fun onAction(block: (Action) -> Unit) = action
        ?.takeIf { it.consumed.not() }
        ?.run { block(consume()) }
}