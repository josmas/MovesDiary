package org.josmas.movesdiary

import android.app.Application

class App : Application(){

  companion object {
    lateinit var instance: App //TODO (jos) can write a Delegate to make this 'write_once'.
  }

  override fun onCreate() {
    super.onCreate()
    instance = this
  }

}