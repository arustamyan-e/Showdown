package showdown.web.ui.game

import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.menuitem.menuItem
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.ReactElement
import react.dom.br
import react.dom.div
import react.dom.key
import react.dom.p
import react.setState


val gameModeOptions: List<Pair<String, Int>>
    get() = listOf(
        "Fibonacci" to 0,
        "T-Shirt" to 1,
        "Modified Fibonacci" to 2,
        "Power of 2" to 3,

        "Custom" to 4
    )

interface SettingsProps : RProps {
    var onSave: (Int, String) -> Unit
    var gameModeId: Int
}

interface SettingsState : RState {
    var onClick: (Int, String) -> Unit
    var gameModeId: Int
    var customOptions: String
}

/**
 * Shows the settings where the player can choose a game mode
 */
class GameSettingsComponent(prps: SettingsProps) : RComponent<SettingsProps, SettingsState>(prps) {

    val CUSTOM_MODE = 4

    override fun SettingsState.init(props: SettingsProps) {
        onClick = props.onSave
        gameModeId = props.gameModeId
        customOptions = ""
    }

    override fun RBuilder.render() {
        br { }
        div {
            textField {
                attrs {
                    select = true
                    label { +"GameMode" }
                    inputProps {}
                    value(state.gameModeId)
                    onChangeFunction = { event ->
                        val value = event.target.asDynamic().value
                        setState {
                            this.gameModeId = value
                        }
                    }
                }
                gameModeOptions.forEach {
                    menuItem {
                        attrs {
                            key = it.first
                            setProp("value", it.second)
                        }
                        +it.first
                    }
                }
            }
        }

        if (state.gameModeId == CUSTOM_MODE) {
            div {
                textField {
                    attrs {
                        variant = FormControlVariant.filled
                        fullWidth = true

                        label {
                            +"Insert Custom options separate with semicolon (;)"
                        }
                        value(state.customOptions)
                        onChangeFunction = {
                            val target = it.target as HTMLInputElement

                            setState {
                                this.customOptions = target.value
                            }
                        }
                    }

                }
            }
        }

        p {
            button {
                attrs {
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    text("Change Mode")
                    onClickFunction = {
                        state.onClick(state.gameModeId, state.customOptions)
                    }
                }
            }
        }


    }
}

fun RBuilder.gameModeSettings(gameModeId: Int, onSave: (Int, String) -> Unit): ReactElement {
    return child(GameSettingsComponent::class) {
        attrs.onSave = onSave
        attrs.gameModeId = gameModeId

    }
}
