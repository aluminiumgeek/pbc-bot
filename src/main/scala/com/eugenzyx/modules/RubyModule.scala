package com.eugenzyx.modules

import sys.process._
import java.io.File

object RubyModule {
  val modulesPath: String = "./src/main/scala/com/eugenzyx/modules/ruby/"
  val modules: Map[String, String] =
    Map[String, String](
      "random" -> "Get a random number from the specified range."
    )

  def execute(module: String, args: Seq[String]): String = {
    val moduleFile =  new File(modulesPath, s"$module.rb")

    if (moduleFile.exists) {
      val modulePath = moduleFile.getPath()
      val command = s"ruby $modulePath ${ args.mkString(" ") }"

      val output = command !!

      output.stripLineEnd
    } else {
      s"Module not found: $module"
    }
  }
}
