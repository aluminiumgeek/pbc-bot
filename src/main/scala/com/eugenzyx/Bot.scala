package com.eugenzyx

import com.eugenzyx.commands._
import com.eugenzyx.modules.RubyModule

import info.mukel.telegram.bots.{TelegramBot, Polling, Commands, Utils}
import info.mukel.telegram.bots.api.InputFile

object Bot extends TelegramBot(Utils.tokenFromFile("./config/bot.token")) with Polling with Commands {
  on(G.command)       { (sender, args) => replyTo(sender) { G.handler(sender, args) } }
  on(Help.command)    { (sender, args) => replyTo(sender) { Help.handler(sender, args) } }
  on(Hey.command)     { (sender, args) => replyTo(sender) { Hey.handler(sender, args) } }
  on(Man.command)     { (sender, args) => replyTo(sender) { Man.handler(sender, args) } }
  on(Weather.command) { (sender, args) => replyTo(sender) { Weather.handler(sender, args) } }
  on(Echo.command)    { (sender, args) => replyTo(sender) { Echo.handler(sender, args) } }
  on(Photo.command)   { (sender, args) =>
      Photo.handler(sender, args)((photo, title) =>
        photoCallback(sender, photo, title), message => sendMessage(sender, message))
  }
  on(Picture.command) { (sender, args) =>
      Picture.handler(sender, args)((photo, title) =>
        photoCallback(sender, photo, title), message => sendMessage(sender, message))
  }
  on("random") { (sender, args) => replyTo(sender) { RubyModule.execute("random", args) } }

  def photoCallback(sender: Int, photo: Any, title: Option[String]) = photo match {
    case photo: InputFile => sendPhoto(sender, photo, title)
    case fileId: String   => sendPhotoId(sender, fileId, title)
    case _                => throw new IllegalStateException("Incorrect data")
  }
}
