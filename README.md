# pbc-bot

## Summary

A Telegram bot for the secret Post-Apocalyptic B. community.

## The Story

This community appeared in the beginning of 2016 in the [OnApp Ltd.](http://www.onapp.com) in the result of combined efforts of the vCloud & Application Server teams members.

## Goals

The main goal is to bring prosperity to all of its members in possible post-apocalyptic future of Earth.

## Contributing

Read general topic on [contributiion to Open Source](https://guides.github.com/activities/contributing-to-open-source/#contributing)

### To add a ruby implementation of a command:

1. Create a ruby file at `PROJECT_ROOT/src/main/scala/com/eugenzyx/modules/ruby/` directory. Refer to `random.rb` as an example.
2. Come up with a command name and make the bot to react to it by adding `on(<command>)` statement to `PROJECT_ROOT/src/main/scala/com/eugenzyx/Bot.scala`

