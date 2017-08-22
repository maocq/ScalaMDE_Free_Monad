name := "free-monad"

version := "1.0"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "org.typelevel"  %% "cats-core"            % "1.0.0-MF",
  "org.typelevel"  %% "cats-free"            % "1.0.0-MF",
  "org.scalatest"  %% "scalatest"            % "3.0.1" % "test"
)