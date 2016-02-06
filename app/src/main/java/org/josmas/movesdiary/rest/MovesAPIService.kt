package org.josmas.movesdiary.rest

import org.josmas.movesdiary.Credentials
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Query

interface MovesAPIService {

  /**
   * To get an access token from an authorisation code, make a post request to
   * https://api.moves-app.com/oauth/v1/access_token?
   *   grant_type=authorization_code&
   *   code=<code>&
   *   client_id=<client_id>&
   *   client_secret=<client_secret>&
   *   redirect_uri=<redirect_uri>
   */
  @POST("/oauth/v1/access_token?grant_type=authorization_code")
  fun getAccessToken(@Query("code")code: String,
                     @Query("client_id")clientId: String,
                     @Query("client_secret")clientSecret: String,
                     @Query("redirect_uri")redirectUri: String): Call<Credentials>
}