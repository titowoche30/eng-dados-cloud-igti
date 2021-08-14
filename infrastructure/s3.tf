resource "aws_s3_bucket" "cwoche-bucket" {
  # Parâmetros de configuração do recurso
  bucket = "${var.environment}-${var.base_bucket_name}"
  acl    = "private"

  tags = {
    manager = "terraform"
  }
}
