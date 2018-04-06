package com.scb.edmi.catalog.model

import java.io.File

import com.typesafe.config.ConfigFactory
import liquibase.integration.commandline.Main
import org.joda.time.DateTime

import scala.collection.mutable.ArrayBuffer

case class LiquibaseOptions(configFile: String = null, command: String = null, rollbackDate: Option[String] = None)

object Liquibase extends App {

  val parser = new scopt.OptionParser[LiquibaseOptions]("scopt") {
    opt[String]('f', "configFile") required() action { (x, c) =>
      c.copy(configFile = x)
    } text ("path of config file")
    opt[String]('c', "command") required() action { (x, c) =>
      c.copy(command = x)
    } text ("Liquibase command (update/rollbackToDate)")
    opt[String]('d', "date") optional() action { (x, c) =>
      c.copy(rollbackDate = Some(x))
    } text ("Rollback to date (for rollback action)")
  }

  parser.parse(args, LiquibaseOptions()) map { options =>
    val config = ConfigFactory.parseFile(new File(options.configFile)).getConfig("catalog.repo.db")
    var liquibaseArgs = ArrayBuffer("--driver", config.getString("driver"),
      "--changeLogFile", "db/db-changelog-master.xml",
      "--url", config.getString("url"),
      "--username", config.getString("user"),
      "--password", config.getString("password"))

    options.command match {
      case "update" => update
      case "rollback" => rollback
    }

    def update: Unit = {
      liquibaseArgs += "update"
      execute()
    }

    def rollback: Unit = {
      liquibaseArgs += "rollbackToDate"
      liquibaseArgs += options.rollbackDate.getOrElse(DateTime.now.minusDays(1).toString("yyyy-MM-dd'T'HH:mm:ss"))
      execute()
    }

    def execute() = {
      Main.run(liquibaseArgs.toArray)
    }
  }
}
