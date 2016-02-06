# A Moves powered Diary app

## Intro
Finally writing a full app in Kotlin, which basically means, not much to see here,
at least for now.

## Usage
Fork/Clone and import into Android Studio.

## What does it do?
For now, it:

  - Has a button in the main Activity to authenticate through the Moves app
      (which means that you need the Moves app installed).

## TODOs

    [] Moves Auth
      [X] decode result Uri to get the code
        [X] setup Spek as a unit testing framework
        [X] create the Util methods in an Interface to be *mixed in* to the Auth
        activity
      [] check for errors in the result Uri for the auth token (see TODOs)
      [] Make sure the code is always the same? if not, does it expire?
        [] If it does not, store
        [] If it does, but it is valid for a while, store
        [] If we need a new one all the time (?), discard
      [] exchange code for bearer token (which does expire)
        [] setup retrofit to make the calls to the Moves API (https only)
      [] store bearer token
        [] devise some way of refreshing the token when needed
      [] Add a progress dialog while auth is taking place
    [] Auto diary
      [] find location info from Moves
      [] find pictures on the internet based on location (and some other
      filters?)
      [] Integrate Picasso or some other images library
      [] make it look good!
    [] Setup CircleCI


Jos - February 16
