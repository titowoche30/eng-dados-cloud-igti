package myPackage

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, element_at, expr, split, to_date, to_timestamp}
import io.delta._

object MyWriterObject {
    def main(args: Array[String]): Unit = {
//        -- class myPackage.MyWriterObject
        val spark = SparkSession.builder()
          .appName("DeltaWriterApp")
          .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
          .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
          .master("local[*]")
          .getOrCreate()

        val sc = spark.sparkContext
        sc.setLogLevel("ERROR")
        sc.hadoopConfiguration.set("fs.s3a.access.key", "")
        sc.hadoopConfiguration.set("fs.s3a.secret.key", "")
        sc.hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")

        val s3_path = "s3a://datalake-claudemir-649165755582/consumer-zone/MICRODADOS_ENEM_2019/"
        val data = spark.read.parquet(s3_path)

        data.write
          .mode("overwrite")
          .format("delta")
          .save("s3a://datalake-claudemir-649165755582/delta-zone/enem")
    }

}
