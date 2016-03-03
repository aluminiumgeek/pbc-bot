package com.eugenzyx

import com.eugenzyx.commands._
import info.mukel.telegram.bots._

object Bot extends TelegramBot(Utils.tokenFromFile("./bot.token")) with Polling with Commands {
  on(G.command)       { (sender, args) => replyTo(sender) { G.handler(sender, args) } }
  on(Help.command)    { (sender, args) => replyTo(sender) { Help.handler(sender, args) } }
  on(Hey.command)     { (sender, args) => replyTo(sender) { Help.handler(sender, args) } }
  on(Man.command)     { (sender, args) => replyTo(sender) { Man.handler(sender, args) } }
  on(Weather.command) { (sender, args) => replyTo(sender) { Weather.handler(sender, args) } }
}
