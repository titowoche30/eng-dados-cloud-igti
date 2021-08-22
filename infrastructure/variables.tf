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

variable "key_pair_name" {
  default = "terraform"
}

variable "airflow_subnet_id" {
  default = "subnet-85c6e7a4"
}

variable "vpc_id" {
  default = "vpc-4885f235"
}