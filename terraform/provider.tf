provider "aws" {
  region = var.aws_region

  default_tags {
    tags = {
      Project     = "enterprise-microservices"
      Environment = var.environment
      ManagedBy   = "Terraform"
    }
  }
}
