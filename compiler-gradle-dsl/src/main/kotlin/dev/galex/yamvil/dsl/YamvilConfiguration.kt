package dev.galex.yamvil.dsl

open class YamvilConfiguration {
    var level: YamvilLevel = YamvilLevel.Error
    var compose: ComposeConfiguration = ComposeConfiguration()

    fun compose(composeConfiguration: ComposeConfiguration.() -> Unit) {
        compose = ComposeConfiguration().apply { composeConfiguration() }
    }
}

class ComposeConfiguration {
    var screenSuffix = "Screen"
    var uiStateParameterName = "uiState"
    var handleEventParameterMame = "handleEvent"
}


