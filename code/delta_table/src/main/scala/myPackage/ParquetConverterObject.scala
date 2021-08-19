package myPackage

import org.apache.spark.sql.SparkSession

object ParquetConverterObject {
    def csvToParquet(spark:SparkSession, fileName: String, bucketName: String = "datalake-claudemir-649165755582") = {
        spark.read
          .option("inferSchema","true")
          .option("header","true")
          .option("sep","|")
          .csv(s"s3://$bucketName/raw-data/$fileName.csv")
          .write
          .mode("overwrite")
          .partitionBy("NU_ANO")
          .parquet(s"s3://$bucketName/staging-data/$fileName")
    }

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder()
          .appName("ParquetConverterApp")
          .getOrCreate()

        val sc = spark.sparkContext
//        sc.setLogLevel("ERROR")
        // sc.hadoopConfiguration.set("fs.s3a.access.key", "")
        // sc.hadoopConfiguration.set("fs.s3a.secret.key", "")
        // sc.hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")

        val fileNames = Seq("matricula_co",
                            "matricula_norte",
                            "matricula_sudeste",
                            "matricula_nordeste",
                            "matricula_sul")

        fileNames.foreach(fileName => csvToParquet(spark, fileName))
//        csvToParquet(spark,"matricula_co")
    }
}
