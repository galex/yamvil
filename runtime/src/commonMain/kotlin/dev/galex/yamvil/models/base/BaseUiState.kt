package dev.galex.yamvil.models.base

/**
 * Represents a UI state that can be consumed by the UI, with an action that can be consumed only once.
 * @param Action the type of the action
 */
interface BaseUiState<Action> {
    val action: Consumable<Action>?

    /**
     * Consumes the action if it is not null and not consumed yet.
     */
    fun onAction(block: (Action) -> Unit) = action
        ?.takeIf { it.consumed.not() }
        ?.run { block(consume()) }
}