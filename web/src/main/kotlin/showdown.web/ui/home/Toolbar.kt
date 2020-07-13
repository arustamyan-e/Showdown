package showdown.web.ui.home

import Application
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import materialui.components.appbar.appBar
import materialui.components.appbar.enums.AppBarColor
import materialui.components.appbar.enums.AppBarPosition
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.menu.menu
import materialui.components.menuitem.menuItem
import org.w3c.dom.events.EventTarget
import react.*
import react.dom.div
import react.dom.label
import showdown.web.game.GameDataSource
import showdown.web.wrapper.material.AccountCircleIcon
import showdown.web.wrapper.material.AddCircleIcon
import showdown.web.wrapper.material.ShareIcon
import showdown.web.wrapper.material.VisibilityIcon
import kotlin.browser.window
import kotlin.math.floor


interface ToolbarState : RState {
    var onNewGameClicked: () -> Unit
    var onShowVotesClicked: () -> Unit
    var onGameModeClicked: () -> Unit

    var diffSecs: Double
    var openMenu: Boolean
    var showChangePassword: Boolean
    var anchor: EventTarget?
    var startTimer: Boolean
    var showShareDialog: Boolean

}


interface ToolbarProps : RProps {
    var onNewGameClicked: () -> Unit
    var onShowVotesClicked: () -> Unit
    var startTimer: Boolean
    var diffSecs: Double
    var onGameModeClicked: () -> Unit

}

class Toolbar(props: ToolbarProps) : RComponent<ToolbarProps, ToolbarState>(props) {

    private val gameDataSource: GameDataSource = Application.gameDataSource


    override fun ToolbarState.init(props: ToolbarProps) {
        this.onNewGameClicked = props.onNewGameClicked
        this.onShowVotesClicked = props.onShowVotesClicked
        this.startTimer = props.startTimer
        this.showShareDialog = false
        this.diffSecs = props.diffSecs
        this.onGameModeClicked = props.onGameModeClicked
    }

    override fun componentWillReceiveProps(nextProps: ToolbarProps) {
        setState {

            this.diffSecs = props.diffSecs
            this.startTimer = props.startTimer

        }
    }

    override fun RBuilder.render() {
        shareDialog(state.showShareDialog) {
            setState {
                this.showShareDialog = false
            }
        }

        appBar {
            attrs {
                position = AppBarPosition.static
                color = AppBarColor.primary
            }
            div {
                button {
                    attrs {
                        variant = ButtonVariant.contained
                        color = ButtonColor.primary
                        text("New Game")
                        onClickFunction = {
                            state.onNewGameClicked()
                        }
                        startIcon {
                            AddCircleIcon {}
                        }
                    }
                }

                button {
                    attrs {
                        variant = ButtonVariant.contained
                        color = ButtonColor.primary
                        text("Show Votes")
                        onClickFunction = {
                            state.onShowVotesClicked()
                        }
                        startIcon {
                            VisibilityIcon {}
                        }
                    }
                }


                button {
                    attrs {
                        variant = ButtonVariant.contained
                        color = ButtonColor.primary
                        text("Share")
                        onClickFunction = {
                            setState {
                                this.showShareDialog = true
                            }
                        }
                        startIcon {
                            ShareIcon {}
                        }
                    }
                }
                settingsPopupMenu(state)
                +"Estimation time: ${getTimerText()} seconds."
            }
        }


    }


    private fun getTimerText(): String {
        return if (state.startTimer) {
            floor(state.diffSecs).toString()
        } else {
            "0"
        }
    }

    private fun RBuilder.settingsPopupMenu(
        state: ToolbarState
    ) {
        button {
            +"Settings"
            attrs {
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                asDynamic()["aria-controls"] = "simple-menu"
                asDynamic()["aria-haspopup"] = true
                startIcon {
                    AccountCircleIcon {}
                }
                onClickFunction = { event ->
                    val currentTarget = event.currentTarget

                    setState {
                        anchor = currentTarget
                        openMenu = !state.openMenu
                    }

                }
            }
        }

        menu {
            attrs {
                id = "simple-menu"
                open = state.openMenu
                onClose = { event, s ->
                    setState {
                        // anchor = currentTarget
                        openMenu = false

                    }
                }
                anchorEl(state.anchor)
            }
            menuItem {
                attrs {
                    onClickFunction = {
                        setState {
                            openMenu = false
                        }

                    }
                }
                label {
                    +"Playername: ${gameDataSource.getPlayerName()}"
                }
            }

            menuItem {
                attrs {
                    onClickFunction = {
                        setState {
                            openMenu = false
                        }
                        state.onGameModeClicked()

                    }
                }
                label {
                    +" Change GameConfig"
                }

            }

            /***
             *   menuItem {
            attrs {
            onClickFunction = {
            setState {
            showChangePassword = true
            openMenu = false
            }
            }
            }
            label {
            +"Room password is: "
            }
            }
             */


            menuItem {
                attrs {
                    onClickFunction = {
                        window.location.href = "https://github.com/Foso/Showdown/issues";
                    }
                }
                label {
                    +"Issues/Feature Requests"
                }
            }

            menuItem {
                attrs {
                    onClickFunction = {
                        window.location.href = "https://github.com/Foso/Showdown";
                    }
                }
                label {
                    +"Source Code on Github"
                }
            }

        }


    }


}

fun RBuilder.myToolbar(
    startTimer: Boolean,
    onNewGameClicked: () -> Unit,
    onShowVotesClicked: () -> Unit,
    diffSecs: Double,
    onGameModeClicked: () -> Unit
): ReactElement {
    return child(Toolbar::class) {
        attrs {
            this.onNewGameClicked = onNewGameClicked
            this.onShowVotesClicked = onShowVotesClicked
            this.startTimer = startTimer
            this.diffSecs = diffSecs
            this.onGameModeClicked = onGameModeClicked
        }
    }
}