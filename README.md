# pbc-bot

## Summary

A Telegram bot for the secret Post-Apocalyptic B. community.

## The Story

This community appeared in the beginning of 2016 in the [OnApp Ltd.](http://www.onapp.com) in the result of combined efforts of the vCloud & Application Server teams members.

## Goals

The main goal is to bring prosperity to all of its members in possible post-apocalyptic future of Earth.

## Contributing

First, read general topic on [contribution to Open Source](https://guides.github.com/activities/contributing-to-open-source/#contributing).

### If you want to add a ruby implementation of a command:

1. Come up with a command name and description and add them to a Map (`val modules: Map[String, String]`) at `PROJECT_ROOT/src/main/scala/com/eugenzyx/modules/RubyModule.scala:9`
2. Create a ruby file that matches the command name at `PROJECT_ROOT/src/main/scala/com/eugenzyx/modules/ruby/<command>.rb` directory.
3. Add `on(<command>)` statement to `PROJECT_ROOT/src/main/scala/com/eugenzyx/Bot.scala`.

Refer to `random.rb` as an example.

## Available Commands

- g - I'm Feeling Lucky!
- help - Show help message.
- hey - Hey!
- echo - Echoes your text.
- weather - Get current weather in a city.
- man - An interface to the on-line reference manuals.
- pic - Get a picture that matches a pattern.
- photo - Get a photo that matches a pattern.
- random - Get a random number from the specified range.
- turbofolklorize - Get a random song from [serbzone.com](serbzone.com).
