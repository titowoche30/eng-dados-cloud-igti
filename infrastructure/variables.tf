variable "base_bucket_name" {
  default = "cwoche-bucket-workflow-tf"
}

variable "environment" {
  default = "dev"
}

variable "aws_region" {
  default = "us-east-1"
}

variable "lambda_function_name" {
  default = "EMRTrigger"
}