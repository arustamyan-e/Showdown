package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable


@Serializable
sealed class GameState {

    /**
     * Initial State for clients
     */
    @Serializable
    object NotStarted : GameState()

    @Serializable
    class Started(val clientGameConfig: ClientGameConfig) : GameState()

    @Serializable
    class MembersUpdate(val members: List<Member>) : GameState()

    @Serializable
    class ShowVotes(val results:List<Result>) : GameState()

}

enum class EnGameState{
    NOTSTARTED,STARTED,MEMBERSUDPATE,SHOWVOTES
}

open class MyGameState(val enGameState: EnGameState)
data class MyMembersUpdate(val members: String) : MyGameState(EnGameState.MEMBERSUDPATE)