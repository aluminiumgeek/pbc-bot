package com.eugenzyx.commands

import com.eugenzyx.commands.traits.Command

object Help extends Command {
  val command = "help"
  val description = "Show help message."

  def handler(sender: Int, args: Seq[String]): String = "Click on the slash icon to see a brief description of the available commands."
}
