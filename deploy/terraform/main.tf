resource "aws_vpc" "expenses_vpc_1" {

  cidr_block           = "10.0.0.0/16"
  enable_dns_hostnames = true
  enable_dns_support   = true

  tags = {
    "Name" = "expenses_vpc_1"
  }

}

resource "aws_subnet" "expenses_subnet" {

  vpc_id                  = aws_vpc.expenses_vpc_1.id
  cidr_block              = "10.0.1.0/24"
  availability_zone       = "us-east-1a"
  map_public_ip_on_launch = true

  tags = {
    Name = "expenses_api_subnet_public"
  }

}

resource "aws_internet_gateway" "expenses_igw" {

  vpc_id = aws_vpc.expenses_vpc_1.id

  tags = {
    "Name" = "expenses_api_igw"
  }

}

resource "aws_route_table" "expenses_rtb" {

  vpc_id = aws_vpc.expenses_vpc_1.id


  tags = {
    "Name" = "expenses_api_rtb"
  }
}

resource "aws_route" "expenses_route" {

  route_table_id = aws_route_table.expenses_rtb.id

  destination_cidr_block = "0.0.0.0/0"
  gateway_id             = aws_internet_gateway.expenses_igw.id
}

resource "aws_route_table_association" "expenses_rtb_ass" {

  route_table_id = aws_route_table.expenses_rtb.id
  subnet_id      = aws_subnet.expenses_subnet.id
}

resource "aws_instance" "expenses_instance_ec2" {

  instance_type = "t2.micro"
  key_name      = aws_key_pair.expenses_key.id
  vpc_security_group_ids = [aws_security_group.expenses_sg.id]
  subnet_id     = aws_subnet.expenses_subnet.id

  ami = data.aws_ami.expenses_ami.id

  user_data = file("userdata.tpl")

  root_block_device {

    volume_size = 8
  }

  tags = {

    "Name" = "expenses_api_ec2"
  }
}