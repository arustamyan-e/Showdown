package showdown.web.ui.game

import de.jensklingenberg.showdown.model.Member
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.TextAlign
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.textAlign
import materialui.components.circularprogress.circularProgress
import materialui.components.circularprogress.enums.CircularProgressVariant
import materialui.components.divider.divider
import materialui.components.list.list
import materialui.components.listitemtext.listItemText
import materialui.components.typography.enums.TypographyColor
import materialui.components.typography.enums.TypographyVariant
import materialui.components.typography.typography
import react.RBuilder
import react.dom.h2
import showdown.web.wrapper.material.icons.Box
import showdown.web.wrapper.material.icons.CancelIcon
import showdown.web.wrapper.material.icons.CheckCircleIcon
import showdown.web.wrapper.material.icons.GroupIcon
import styled.css
import styled.styledDiv
import kotlin.math.round


fun getSpectators(members: List<Member>): List<Member> {
    return members.filter { it.isSpectator }
}

fun getPlayers(members: List<Member>): List<Member> {
    return members.filter { !it.isSpectator }
}

/**
 * Shows the list of players
 */
fun RBuilder.playersList(members: List<Member>) {

    val players = getPlayers(members)
    val spectators = getSpectators(members)

    h2 {
        GroupIcon {}

        +"Players (${players.size}) Voted:"

        Box {
            attrs {
                this.display = "inline-flex"
                this.position = "relative"
            }
            circularProgress {
                attrs {
                    variant = CircularProgressVariant.static
                    val progressPercent = (players.filter { it.voted }.size.toDouble() / players.size.toDouble()) * 100

                    value = round(progressPercent)
                }
            }
            Box {
                attrs {
                    this.display = "flex"
                    this.alignItems = "center"
                    this.top = 0
                    left = 0
                    right = 0
                    bottom = 0
                    justifyContent = "center"
                    this.position = "absolute"
                }
                typography {
                    attrs {
                        variant = TypographyVariant.caption
                        component = "div"
                        color = TypographyColor.textSecondary
                    }
                    val progressPercent = (players.filter { it.voted }.size.toDouble() / players.size.toDouble()) * 100

                    +"${round(progressPercent)}%"
                }

            }

        }
    }

    list {

        players.forEach {
            listItemText {

                styledDiv {
                    css {
                        this.textAlign = TextAlign.center
                    }
                    +("Player: " + it.playerName + " Voted:")

                    if (it.voted) {
                        styledDiv {
                            css {
                                this.color = if (it.voted) {
                                    Color.green
                                } else {
                                    Color.red
                                }
                                this.display = Display.inlineBlock
                            }
                            CheckCircleIcon {}
                        }
                    } else {
                        styledDiv {
                            css {
                                this.color = Color.red
                                this.display = Display.inlineBlock
                            }
                            CancelIcon {}
                        }
                    }
                    if (!it.isConnected) {
                        +" Connection lost"
                    }
                }

            }

            divider {}
        }


    }

    if (spectators.isNotEmpty()) {
        h2 {
            GroupIcon {}
            +"Spectators (${spectators.size})"
        }

        list {

            spectators.forEach {
                listItemText {

                    styledDiv {
                        css {
                            this.textAlign = TextAlign.center
                        }
                        +("" + it.playerName + " ")

                        if (!it.isConnected) {
                            +" Connection lost"
                        }
                    }

                }

                divider {}
            }


        }
    }

}