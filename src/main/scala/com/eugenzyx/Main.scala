package com.eugenzyx

object Program {
  def main(args: Array[String]): Unit = {
    case class Config(interactive: Boolean = false, launch: Boolean = false)

    val parser = new scopt.OptionParser[Config]("scopt") {
      head("pbcbot", "1.0")

      opt[Unit]('i', "interactive") action { (_, c) =>
        c.copy(interactive = true) } text("run interactive mode to test commands instead of the bot itself")

      opt[Unit]('l', "launch") action { (_, c) =>
        c.copy(launch = true) } text("launch the bot")

      help("help") text("print this text")
    }

    parser.parse(args, Config()) match {
      case Some(config) =>
        if (config.interactive) {
          println("Starting interactive mode.")

          InteractiveMode.run
        }

        if (config.launch) {
          println("Starting the bot.")

          Bot.run
        }

      case None => println("Specify mode to run in: interactive or launch.")
    }
  }
}
