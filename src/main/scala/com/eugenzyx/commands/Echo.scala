package com.eugenzyx.commands

import com.eugenzyx.commands.traits.Command

object Echo extends Command {
  val command = "echo"
  val description = """Returns input text.
    |Usage: /echo <text>
    |See also: hey""".stripMargin

  def handler(sender: Int, args: Seq[String]): String = args.mkString(" ")
}
