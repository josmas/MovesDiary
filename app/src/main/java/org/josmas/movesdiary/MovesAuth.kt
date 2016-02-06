package org.josmas.movesdiary

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.josmas.movesdiary.rest.MovesAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class Credentials (val access_token: String, val token_type: String, val expires_in: Int,
                        val refresh_token: String, val user_id: String)

interface MovesAuth: AnkoLogger {
  companion object {
    val CLIENT_ID = "0Z5UOm7tpViK5Ls242Padd4d4xS1AQ1j"
    val REDIRECT_URI = "https://api.moves-app.com/auth/moves/callback"
    val CLIENT_SECRET = "dRSw6mn05cnGBaUxOOOFEodeeJkctx1DpQ6d9G7FJ8ZL9hMd93gqWHW69Q3nNwH5"

    private val CODE_LENGTH = 64

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.moves-app.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    val authService: MovesAPIService  = retrofit.create(MovesAPIService::class.java)
  }

  /**
   * It takes an URI such as:
   * https://moves-api-demo.herokuapp.com/auth/moves/callback?code=CLby10w70vqruAe7HIE63rRrhoapE66tzofpZAu9lUToW73d3nB40qW79DA9QwV5&state=186329833
   * https://moves-api-demo.herokuapp.com/auth/moves/callback?code=k1c4hXBsYw54P8Jz4wBNUq49f09Fvhzvm78z948E3cS_BBPN5PXq0dJ1q4OB3fNy&state=186342375
   * https://moves-api-demo.herokuapp.com/auth/moves/callback?code=24sG8zlMaC1syvvHXlhKqxd5BGN55pf4w8rPcB7cWQ0Z5u1mqnbqf9VdjjKjw7kT&state=186348430
   * and returns the code (which will be eventually exchanged for a bearer token for the Moves API).
   *
   * If the authorisation is denied or fails, the result will be in the form:
   * <redirect_uri>?error=<errorcode>
   */
  fun decodeMovesCode(uriCode: String): String {
    //TODO (jos) add some more checks here. Can I check the length of the resulting code?
    //TODO (jos) check for <?error> in the case of failed auth (see comments above).
    return uriCode.substringAfter("code=").substringBefore("&state")
  }

  fun requestToken(code: String): Unit {

    val getTokenCall: Call<Credentials> = authService.getAccessToken(code, CLIENT_ID,
        CLIENT_SECRET, REDIRECT_URI)
    getTokenCall.enqueue(object: Callback<Credentials> {
      override fun onResponse(tokenCall: Call<Credentials>?, response: Response<Credentials>?) {
        if (response != null){
          info(response.body())
          val body = response.body()
          val accessCode = body.access_token;
          info(accessCode)
          // TODO (jos) I need to store the credentials I get back, including the auth_token:code
        }
        //TODO (jos) else --> Feedback that something is wrong.
        //TODO (jos) the Auth progress bar stops here?
      }

      override fun onFailure(tokenCall: Call<Credentials>?, exception: Throwable?) {
        info(exception.toString())
        info(exception.toString())
        //TODO (jos) stop the Auth progress bar here too.
        throw UnsupportedOperationException(exception)
      }
    })
  }
}