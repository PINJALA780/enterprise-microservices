variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "ap-south-1"
}

variable "environment" {
  description = "Environment"
  type        = string
  default     = "dev"
}

variable "cluster_name" {
  description = "EKS cluster name"
  type        = string
  default     = "enterprise-eks"
}
