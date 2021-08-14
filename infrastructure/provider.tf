provider "aws" {
  region                  = var.aws_region
  shared_credentials_file = "/home/titowoche30/.aws/terraform/credentials"
}

# Centralizar o arquivo de controle de estado do terraform
terraform {
  backend "s3" {
    bucket = "tf-state-cwoche"
    key   = "state/igti/module1/terraform.tfstate"
    region = "us-east-1"
  } 
}