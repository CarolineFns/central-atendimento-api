drop database if exists centralatendimento;
create database if not exists centralatendimento;
use centralatendimento;

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(500) DEFAULT NULL,
  `email` varchar(500) DEFAULT NULL,
  `telefone` varchar(100) DEFAULT NULL,
  `cpf` varchar(100) DEFAULT NULL,
  `cep` varchar(10) DEFAULT NULL,
  `logradouro` varchar(100) DEFAULT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `estado` int(5) NOT NULL,
  `municipio` int(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

INSERT INTO `cliente` VALUES 
(1,'Usuario1','usuario1@naoexiste.fns','99998888','0987654321','08450514', "Saladeirista","13", 1, 2),
(2,'Usuario2','usuario2@naoexiste.fns','33442233','5654323455','08440514', "Saladeirista","13", 1, 2),
(3,'Usuario3','usuario3@naoexiste.fns','33442233','2367876543','08450180', "Saladeirista","13", 1, 2),
(4,'Usuario4','usuario4@naoexiste.fns','99997777','4567876543','04567900', "Saladeirista","13", 1, 2);

