package myTestPackage

import org.apache.spark.sql.SparkSession
import io.delta._


object myTestObject {
    def sparkTest(): Unit = {
        val spark = SparkSession.builder()
          .appName("myTestApp")
          .master("local[*]")
          .getOrCreate()
        val sc = spark.sparkContext

        sc.setLogLevel("ERROR")
        sc.hadoopConfiguration.set("fs.s3a.access.key", "")
        sc.hadoopConfiguration.set("fs.s3a.secret.key", "")
        sc.hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")

        val s3_path = "s3a://datalake-claudemir-649165755582/consumer-zone/MICRODADOS_ENEM_2019/"
        val data = spark.read.parquet(s3_path)
        data.show(10)

    }

    def main(args: Array[String]): Unit = {
        sparkTest()
    }


}
