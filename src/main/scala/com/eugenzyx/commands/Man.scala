package com.eugenzyx.commands

import com.eugenzyx.commands.traits.Command

object Man extends Command {
  val command = "man"
  val description = "An interface to the on-line reference manuals.\nUsage: /man <command>"

  def handler(sender: Int, args: Seq[String]): String = {
    val entry = args.mkString(" ")

    if (entry.isEmpty) {
      "What manual page do you want?"
    } else {
      entry match {
        case Help.command => Help.description
        case Hey.command => Hey.description
        case G.command => G.description
        case Photo.command => Photo.description
        case Weather.command => Weather.description
        case Man.command => Man.description
        case "random" => "Get random number within specified range.\nUsage: /random <left bound> <right bound>"
        case _ => s"No manual entry for $entry"
      }
    }
  }
}
