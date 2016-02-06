package org.josmas.movesdiary

interface MovesAuth {
  companion object {
    private val CODE_LENGTH = 64
  }

  /**
   * It takes an URI such as:
   * https://moves-api-demo.herokuapp.com/auth/moves/callback?code=CLby10w70vqruAe7HIE63rRrhoapE66tzofpZAu9lUToW73d3nB40qW79DA9QwV5&state=186329833
   * https://moves-api-demo.herokuapp.com/auth/moves/callback?code=k1c4hXBsYw54P8Jz4wBNUq49f09Fvhzvm78z948E3cS_BBPN5PXq0dJ1q4OB3fNy&state=186342375
   * https://moves-api-demo.herokuapp.com/auth/moves/callback?code=24sG8zlMaC1syvvHXlhKqxd5BGN55pf4w8rPcB7cWQ0Z5u1mqnbqf9VdjjKjw7kT&state=186348430
   * and returns the code (which will be eventually exchanged for a bearer token for the Moves API)
   */
  fun decodeMovesCode(uriCode: String): String {
    //TODO (jos) add some more checks here. Can I check the length of the resulting code?
    return uriCode.substringAfter("code=").substringBefore("&state")
  }
}