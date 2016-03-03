package com.eugenzyx.commands.traits

trait Command {
  val command: String

  val description: String

  def handler(sender: Int, args: Seq[String]): String
}
