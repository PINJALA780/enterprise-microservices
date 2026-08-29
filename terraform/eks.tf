module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "~> 21.0"

  name               = var.cluster_name
  kubernetes_version = "1.34"

  endpoint_public_access  = true
  endpoint_private_access = true

  enable_cluster_creator_admin_permissions = true

  vpc_id     = module.vpc.vpc_id
  subnet_ids = module.vpc.private_subnets

  eks_managed_node_groups = {
    main = {
      name = "enterprise-eks-nodes"

      instance_types = ["m7i-flex.large"]
      capacity_type  = "ON_DEMAND"

      min_size     = 1
      max_size     = 2
      desired_size = 1

      labels = {
        Environment = var.environment
        Project     = "enterprise-microservices"
      }
    }
  }
}
