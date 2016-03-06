package com.eugenzyx.commands

import com.eugenzyx.commands.traits.Command

object Hey extends Command {
  val command = "hey"
  val description = "Hey!"

  def handler(sender: Int, args: Seq[String]): String = "Hey!"
}
