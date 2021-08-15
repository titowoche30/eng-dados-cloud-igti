name := "spark-delta"

version := "0.1"

scalaVersion := "2.12.10"

val sparkVersion = "3.1.1"
val deltaVersion = "1.0.0"

libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion,
    "org.apache.spark" %% "spark-sql" % sparkVersion,

    "io.delta" %% "delta-core" % deltaVersion,

    "com.amazonaws" % "aws-java-sdk" % "1.12.46",
    "org.apache.hadoop" % "hadoop-aws" % "3.3.1",
    "org.apache.hadoop" % "hadoop-common" % "3.3.1",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.12.2"
)
