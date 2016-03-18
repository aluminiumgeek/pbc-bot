package com.eugenzyx

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import com.eugenzyx.commands._
import com.eugenzyx.modules.RubyModule

import info.mukel.telegram.bots.{TelegramBot, Polling, Commands, Utils}
import info.mukel.telegram.bots.api.InputFile

object Bot extends TelegramBot(Utils.tokenFromFile("./config/bot.token")) with Polling with Commands {
  on(G.command)       { (sender, args) => Future { replyTo(sender) { G.handler(sender, args) } } }
  on(Help.command)    { (sender, args) => Future { replyTo(sender) { Help.handler(sender, args) } } }
  on(Hey.command)     { (sender, args) => Future { replyTo(sender) { Hey.handler(sender, args) } } }
  on(Man.command)     { (sender, args) => Future { replyTo(sender) { Man.handler(sender, args) } } }
  on(Weather.command) { (sender, args) => Future { replyTo(sender) { Weather.handler(sender, args) } } }
  on(Photo.command)   { (sender, args) =>
    Future {
      Photo.handler(sender, args)((photo, title) =>
        photoCallback(sender, photo, title), message => sendMessage(sender, message))
    }
  }
  on(Picture.command) { (sender, args) =>
    Future {
      Picture.handler(sender, args)((photo, title) =>
        photoCallback(sender, photo, title), message => sendMessage(sender, message))
    }
  }
  on("random") { (sender, args) => Future { replyTo(sender) { RubyModule.execute("random", args) } } }

  def photoCallback(sender: Int, photo: Any, title: Option[String]) = photo match {
    case photo: InputFile => sendPhoto(sender, photo, title)
    case fileId: String   => sendPhotoId(sender, fileId, title)
    case _                => throw new IllegalStateException("Incorrect data")
  }
}
