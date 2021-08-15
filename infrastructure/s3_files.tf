resource "aws_s3_bucket_object" "delta_job" {
    bucket = aws_s3_bucket.cwoche-bucket.id
    key = "emr-code/scala/delta_spark_jobs.jar"
    acl = "private"
    source = "../code/delta_table/out/artifacts/spark-delta.jar"
    etag = filemd5("../code/delta_table/out/artifacts/spark-delta.jar")
}