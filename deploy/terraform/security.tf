resource "aws_security_group" "expenses_sg" {

  name = "expenses_sg"

  description = "expenses api security group"

  vpc_id = aws_vpc.expenses_vpc_1.id
}

resource "aws_security_group_rule" "sgr_public_out" {

  from_port = 0
  protocol  = "-1"
  to_port   = 0
  type      = "egress"
  cidr_blocks = ["0.0.0.0/0"]

  security_group_id = aws_security_group.expenses_sg.id

}

resource "aws_security_group_rule" "srg_ssh_in" {
  from_port = 22
  protocol  = "tcp"
  to_port   = 22
  type      = "ingress"
  cidr_blocks = ["0.0.0.0/0"]

  security_group_id = aws_security_group.expenses_sg.id

}

resource "aws_security_group_rule" "srg_http_in" {
  from_port         = 80
  protocol          = "tcp"
  security_group_id = aws_security_group.expenses_sg.id
  to_port           = 80
  type              = "ingress"
  cidr_blocks = ["0.0.0.0/0"]
}

resource "aws_key_pair" "expenses_key" {

  key_name = "expenses_api_key"
  public_key = file("~/.ssh/expenses_api_key.pub")
}
