
import de.jensklingenberg.showdown.model.*
import showdown.web.game.GameRepository
import showdown.web.network.GameApiClient
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class BasicJsTest {
    @Test
    fun testDecode() {
        val client = GameApiClient()
        val gameRepository = GameRepository(client)
        gameRepository.joinRoom("abc","")
        assertEquals("abc",gameRepository.getPlayerName())
    }

    @Test
    fun testDecode2() {
        val testString = "{\"data\":{\"body\":{\"members\":\"HUHU\"},\"enGameState\":\"MEMBERSUDPATE\"},\"message\":\"\",\"resourceType\":\"GameState\"}"
       val type= getWebsocketType(testString)
        assertEquals(WebSocketResourceType.GameState,type)
    }

    @Test
    fun testDecode3() {
        val testString = "{\"data\":{\"body\":{\"members\":\"HUHU\"},\"enGameState\":\"MEMBERSUDPATE\"},\"message\":\"\",\"resourceType\":\"GameState\"}"
        val resource = JSON.parse<WebsocketResource<MyGameState>>(testString)
       // assertTrue { resource.data is MyGameState }
//val tt =           JSON.parse<MyGameState>(resource.data.toString())
//resource.data!!.body as MyMembersUpdate
        console.log(resource.data!!.body)
      //  assertTrue { resource.data!!.enGameState.toString() == EnGameState.MEMBERSUDPATE.name }
        //assertSame(WebSocketResourceType.GameState,resource.resourceType)
    }

}
