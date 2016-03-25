package com.eugenzyx.commands

import scala.concurrent.Future
import scala.util.Random

import com.eugenzyx.utils.Config
import com.eugenzyx.commands.traits._

import info.mukel.telegram.bots.api.InputFile
import info.mukel.telegram.bots.api.Message

import java.io.File
import java.net.URL

import net.ruippeixotog.scalascraper.browser.Browser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

import org.jsoup.nodes.Element

import sys.process._

object Turbofolklorize extends Command {
  val command = "turbofolklorize"
  val description =
    """Get a random song from serbzone.com
      |Usage: /turbofolklorize""".stripMargin

  def handler(sender: Int)(foundCallback: InputFile => Future[Message]): Future[Message] = {
    val browser = new Browser
    val doc = browser.get("http://serbzone.com/")

    val links: List[String] = doc >> elementList("a") >> attr("href")("a")
    val songs: List[String] = links.filter(_.endsWith(".mp3"))

    val song = Random.shuffle(songs).head

    foundCallback(download(song))
  }

  def download(url: String): InputFile = {
    val safeUrl = url.replaceAll(" ", "%20")
    val tempDir = Config("audioTempDirectory")

    val filePath = s"${ tempDir }/${ url.split("/").last }"
    val file = new File(filePath)

    if (!file.exists) new URL(safeUrl) #> new File(filePath) !!

    InputFile(filePath)
  }
}
