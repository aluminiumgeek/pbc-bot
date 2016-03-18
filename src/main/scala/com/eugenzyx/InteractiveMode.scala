package com.eugenzyx

import com.eugenzyx.commands._
import com.eugenzyx.modules.RubyModule

object InteractiveMode {
  def run: Unit = {
    val input = readLine().split(" ")
    val command = input.head
    val args = input.tail

    val output = command match {
      case G.command       => G.handler(0, args)
      case Help.command    => Help.handler(0, args)
      case Hey.command     => Hey.handler(0, args)
      case Man.command     => Man.handler(0, args)
      case Weather.command => Weather.handler(0, args)
      case Echo.command    => Echo.handler(0, args)
      case _ =>               RubyModule.execute(command, args)
    }

    println(output)
    run
  }
}
