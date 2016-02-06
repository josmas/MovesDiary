package org.josmas.movesdiary

import org.jetbrains.spek.api.Spek
import kotlin.test.assertEquals

// Create a class that uses the methods from MovesAuth
class MovesAuthForTest: MovesAuth

class MovesAuthSpecs: Spek() {
  companion object {
    private val uriCode = "https://moves-api-demo.herokuapp.com/auth/moves/callback?" +
        "code=CLby10w70vqruAe7HIE63rRrhoapE66tzofpZAu9lUToW73d3nB40qW79DA9QwV5&state=186329833"
    private val expectedCode = "CLby10w70vqruAe7HIE63rRrhoapE66tzofpZAu9lUToW73d3nB40qW79DA9QwV5"
  }

  init {
    given("a uri with a code and a status") {
      val authTest = MovesAuthForTest()
      on("trying to parse the code") {
        val code = authTest.decodeMovesCode(uriCode)
        it("should result in the expected code") {
          assertEquals(expectedCode, code)
        }
      }
    }
  }
}
