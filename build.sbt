name := "BankingSystem"

version := "0.2"
scalaVersion := "2.13.13"

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "8.0.33",
  "com.typesafe.slick" %% "slick" % "3.5.0",
  "org.slf4j" % "slf4j-simple" % "2.0.13"
)

libraryDependencies += "com.mysql" % "mysql-connector-j" % "8.4.0"
