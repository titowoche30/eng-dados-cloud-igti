resource "aws_glue_catalog_database" "stream" {
  name = "streamingdb"
}

resource "aws_glue_crawler" "stream" {
  database_name = aws_glue_catalog_database.stream.name
  name          = "firehose_stream_s3_crawler"
  role          = aws_iam_role.glue_role.arn

  s3_target {
    path = "s3://${aws_s3_bucket.stream.bucket}/firehose/"
  }

  configuration = <<EOF
{
   "Version": 1.0,
   "Grouping": {
      "TableGroupingPolicy": "CombineCompatibleSchemas" }
}
EOF

  tags = {
    manager = "terraform"
  }
}

resource "aws_glue_catalog_database" "desafio-modulo-1" {
  name = "modulo1db"
}

resource "aws_glue_crawler" "crawler-desafio" {
  database_name = aws_glue_catalog_database.desafio-modulo-1.name
  name          = "crawler_desafio_s3"
  role          = aws_iam_role.glue_role.arn

  s3_target {
    path = "s3://datalake-claudemir-649165755582/staging-data/"
  }

  tags = {
    manager = "terraform"
  }
}