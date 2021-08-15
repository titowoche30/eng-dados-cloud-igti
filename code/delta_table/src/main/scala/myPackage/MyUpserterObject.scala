package myPackage

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, element_at, expr, lit, split, to_date, to_timestamp}
import io.delta.tables.DeltaTable
import io.delta._


object MyUpserterObject {
    def main(args: Array[String]): Unit = {
//        -- class myPackage.MyUpserterObject
        val spark = SparkSession.builder()
          .appName("DeltaUpsertApp")
          .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
          .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
          .getOrCreate()

        val sc = spark.sparkContext
        sc.setLogLevel("ERROR")
        sc.hadoopConfiguration.set("fs.s3a.access.key", "")
        sc.hadoopConfiguration.set("fs.s3a.secret.key", "")
        sc.hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")


        val s3_path = "s3a://datalake-claudemir-649165755582/delta-zone/enem/"
        val enemNovo = spark.read.format("delta").load(s3_path)

        val inscricoes = enemNovo.select("NU_INSCRICAO").limit(100)

        val enemNovoAltered = enemNovo
          .where(enemNovo("NU_INSCRICAO") === inscricoes("NU_INSCRICAO"))
          .withColumn("NO_MUNICIPIO_RESIDENCIA", lit("NOVA CIDADE"))
          .withColumn("CO_MUNICIPIO_RESIDENCIA", lit(10000000))

        val enemVelho = DeltaTable.forPath(spark,s3_path)
        println("Upsertando")
        enemVelho.alias("old")
          .merge(enemNovoAltered.alias("new"), "old.NU_INSCRICAO = new.NU_INSCRICAO")
          .whenMatched()
          .updateAll()
          .whenNotMatched()
          .insertAll()
          .execute()

        println("Criando manifesto")
        enemVelho.generate("symlink_format_manifest")
        println("Manifesto gerado")
    }

}
