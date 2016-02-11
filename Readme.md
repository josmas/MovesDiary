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
      [X] exchange code for bearer token (which does expire)
        [X] setup retrofit to make the calls to the Moves API (https only)
      [] add retrofit routes for other API endpoints (Places or Storyline)
        [] add routes
        [] add data classes
        [] do I need to store this data, or cache if for a while?
      [] store stuff
        [X] I tried doing this with Realm but it needs custom serialisers, so I am
           going to use Anko + SQLite (see branch -realm-)
        [X] Anko + SQLlite
          [] figure out how migrations work
          [X] store auth token -- Not needed
          [X] store credentials (bearer token and refresh)
          [] store API data from calls
      [] Add a progress dialog while auth is taking place
      [] Figure out the flow of Auth + token + what happens next
        [X] When App starts, only show Auth button if needed
          [X] Check DB for access_token
          [X] Get profile with a bad token and fix error handling
            [] and think about how to handle when a token is expired
            [] also store the profile, as it should not change.
          [] Validate access_token? Can I do this with the expires_in?
          [] Add sing out to Menu
      [] devise some way of refreshing the token when needed
    [] Auto diary
      [] find location info from Moves
      [] find pictures on the internet based on location (and some other
      filters?)
      [] Integrate Picasso or some other images library
      [] make it look good!
    [] Sign out funcitonality
    [X] Setup CircleCI


Jos - February 16
