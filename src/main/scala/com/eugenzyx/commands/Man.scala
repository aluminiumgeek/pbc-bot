package com.eugenzyx.commands

import com.eugenzyx.commands.traits.Command
import com.eugenzyx.modules.RubyModule

object Man extends Command {
  val command = "man"
  val description =
    """An interface to the on-line reference manuals.
      |Usage: /man <command>""".stripMargin

  def handler(sender: Int, args: Seq[String]): String = {
    val entry = args.mkString(" ")

    if (entry.isEmpty) "What manual page do you want?" else getManPage(entry)
  }

  private def getManPage(entry: String): String = {
    if (RubyModule.modules.contains(entry)) {
      RubyModule.modules(entry)
    } else {
      entry match {
        case Help.command => Help.description
        case Hey.command => Hey.description
        case G.command => G.description
        case Photo.command => Photo.description
        case Picture.command => Picture.description
        case Weather.command => Weather.description
        case Man.command => Man.description
        case _ => s"No manual entry for $entry"
      }
    }
  }
}
