-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: dbs_cdi
-- ------------------------------------------------------
-- Server version	5.7.33-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_bodega`
--

DROP TABLE IF EXISTS `tbl_bodega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_bodega` (
  `bdg_bodegaid` int(11) NOT NULL AUTO_INCREMENT,
  `bdg_nombre` varchar(45) DEFAULT NULL,
  `bdg_direccion` varchar(255) DEFAULT NULL,
  `bdg_telefono` int(11) DEFAULT NULL,
  PRIMARY KEY (`bdg_bodegaid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_bodega`
--

LOCK TABLES `tbl_bodega` WRITE;
/*!40000 ALTER TABLE `tbl_bodega` DISABLE KEYS */;
INSERT INTO `tbl_bodega` VALUES (7,'Calle 77','Calle 77 a  # 12 - 03',2111111);
/*!40000 ALTER TABLE `tbl_bodega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_categoria`
--

DROP TABLE IF EXISTS `tbl_categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_categoria` (
  `cat_categoriaid` int(11) NOT NULL AUTO_INCREMENT,
  `cat_nombre` varchar(45) DEFAULT NULL,
  `cat_descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cat_categoriaid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_categoria`
--

LOCK TABLES `tbl_categoria` WRITE;
/*!40000 ALTER TABLE `tbl_categoria` DISABLE KEYS */;
INSERT INTO `tbl_categoria` VALUES (3,'Celulares','Gama alta'),(4,'Hogar','Cocina'),(5,'Ferreteria','Martillo y puntillas'),(6,'Deportes','deportes'),(7,'Salud y Belleza','Salud y Belleza'),(9,'Tecnología','Artículos electrónicos'),(10,'Mercado','Víveres y demás'),(11,'Consolas','Juegos, controles y mucho mas...'),(12,'Juguetes','Mundo Niña, Mundo Niño, Mundo bebe.');
/*!40000 ALTER TABLE `tbl_categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_factura`
--

DROP TABLE IF EXISTS `tbl_factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_factura` (
  `fac_facturaid` int(11) NOT NULL AUTO_INCREMENT,
  `fk_vendedor` int(11) DEFAULT NULL,
  `fk_cliente` int(11) DEFAULT NULL,
  `fac_fecha` date DEFAULT NULL,
  `fac_totalproductos` int(11) DEFAULT NULL,
  `fac_valortotal` double DEFAULT NULL,
  `fac_impuestos` double DEFAULT NULL,
  `fac_dirrecionentrega` varchar(255) DEFAULT NULL,
  `fac_telefono` varchar(30) DEFAULT NULL,
  `fac_ciudad` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`fac_facturaid`),
  KEY `fk_vendedorf_idx` (`fk_vendedor`),
  KEY `fk_clientef_idx` (`fk_cliente`),
  CONSTRAINT `fk_clientef` FOREIGN KEY (`fk_cliente`) REFERENCES `tbl_usuario` (`usu_usuarioid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_vendedorf` FOREIGN KEY (`fk_vendedor`) REFERENCES `tbl_usuario` (`usu_usuarioid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_factura`
--

LOCK TABLES `tbl_factura` WRITE;
/*!40000 ALTER TABLE `tbl_factura` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_factura_producto`
--

DROP TABLE IF EXISTS `tbl_factura_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_factura_producto` (
  `fpt_facturaproductoid` int(11) NOT NULL AUTO_INCREMENT,
  `fk_factura` int(11) DEFAULT NULL,
  `fk_producto` int(11) DEFAULT NULL,
  `fpt_valorunidad` double DEFAULT NULL,
  `fpt_cantidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`fpt_facturaproductoid`),
  KEY `fk_facturaf_idx` (`fk_factura`),
  KEY `fk_producto_idx` (`fk_producto`),
  CONSTRAINT `fk_facturaf` FOREIGN KEY (`fk_factura`) REFERENCES `tbl_factura` (`fac_facturaid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto` FOREIGN KEY (`fk_producto`) REFERENCES `tbl_producto` (`pdt_productoid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_factura_producto`
--

LOCK TABLES `tbl_factura_producto` WRITE;
/*!40000 ALTER TABLE `tbl_factura_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_factura_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_foto`
--

DROP TABLE IF EXISTS `tbl_foto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_foto` (
  `fot_fotoid` int(11) NOT NULL AUTO_INCREMENT,
  `fot_nombre` varchar(45) DEFAULT NULL,
  `fot_descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fot_fotoid`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_foto`
--

LOCK TABLES `tbl_foto` WRITE;
/*!40000 ALTER TABLE `tbl_foto` DISABLE KEYS */;
INSERT INTO `tbl_foto` VALUES (1,'65465','23645'),(2,'20210828093557449.jpg','slider3.jpg'),(3,'20210828094632850.jpg','slider1.jpg'),(4,'20210828095456335.jpg','slider2.jpg'),(5,'20210828095509443.jpg','slider2.jpg'),(6,'20210828095519446.jpg','usuarios.jpg'),(7,'20210828100516718.jfif','8806092141292-001-750Wx750H.jfif'),(8,'20210828100522456.jfif','8806092141292-002-1400Wx1400H.jfif'),(9,'20210828100529967.jfif','8806092141292-003-750Wx750H.jfif'),(10,'20210828100538696.jfif','8806092141292-004-750Wx750H.jfif'),(11,'20210828100710907.jfif','8806092141292-001-750Wx750H.jfif'),(12,'20210828100714152.jfif','8806092141292-002-1400Wx1400H.jfif'),(13,'20210828100719747.jfif','8806092141292-003-750Wx750H.jfif'),(14,'20210828100725170.jfif','8806092141292-004-750Wx750H.jfif'),(15,'20210828100838841.jfif','840023212574-001-750Wx750H.jfif'),(16,'20210828100841883.jfif','840023212574-002-750Wx750H.jfif'),(17,'20210828100848747.jfif','840023212574-003-750Wx750H.jfif'),(18,'20210828100853618.jfif','840023212574-004-750Wx750H.jfif'),(19,'20210828100859561.jfif','840023212574-008-750Wx750H.jfif'),(20,'20210828101215750.jfif','7706112015446-001-750Wx750H.jfif'),(21,'20210828101219440.jfif','7706112015446-002-750Wx750H.jfif'),(22,'20210828101648592.jfif','7706112010410-002-750Wx750H.jfif'),(23,'20210828101651509.jfif','7706112010410-003-750Wx750H.jfif'),(24,'20210828101654046.jfif','7706112010410-004-750Wx750H.jfif'),(25,'20210828101656859.jfif','7706112010410-005-750Wx750H.jfif'),(26,'20210828101700223.jfif','7706112010410-006-750Wx750H.jfif'),(27,'20210828101702803.jfif','7706112010410-007-750Wx750H.jfif'),(28,'20210828101959276.jfif','889842651348-001-750Wx750H.jfif'),(29,'20210828102002969.jfif','889842651348-002-750Wx750H.jfif'),(30,'20210828102006389.jfif','889842651348-004-750Wx750H.jfif'),(31,'20210828102009358.jfif','889842651348-006-750Wx750H.jfif'),(32,'20210828102146227.jfif','711719538660-001-750Wx750H.jfif'),(33,'20210828102149596.jfif','711719538660-003-750Wx750H.jfif'),(34,'20210828102152391.jfif','711719538660-006-750Wx750H.jfif'),(35,'20210828103956504.jfif','673419304504-001-750Wx750H.jfif'),(36,'20210828103959455.jfif','673419304504-002-750Wx750H.jfif'),(37,'20210828104002174.jfif','673419304504-003-750Wx750H.jfif'),(38,'20210828104006079.jfif','673419304504-004-750Wx750H.jfif'),(39,'20210828104121147.jfif','673419302869-001-750Wx750H.jfif'),(40,'20210828104124539.jfif','673419302869-002-750Wx750H.jfif');
/*!40000 ALTER TABLE `tbl_foto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_ordencompra`
--

DROP TABLE IF EXISTS `tbl_ordencompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_ordencompra` (
  `odc_ordencompraid` int(11) NOT NULL AUTO_INCREMENT,
  `odc_ordennumero` varchar(45) DEFAULT NULL,
  `odc_catidad` int(11) DEFAULT NULL,
  `odc_valorcompra` double DEFAULT NULL,
  `odc_novedad` varchar(255) DEFAULT NULL,
  `odc_fecha_ingreso` date DEFAULT NULL,
  `fk_operario` int(11) DEFAULT NULL,
  PRIMARY KEY (`odc_ordencompraid`),
  KEY `fk_operario_idx` (`fk_operario`),
  CONSTRAINT `fk_operario` FOREIGN KEY (`fk_operario`) REFERENCES `tbl_usuario` (`usu_usuarioid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_ordencompra`
--

LOCK TABLES `tbl_ordencompra` WRITE;
/*!40000 ALTER TABLE `tbl_ordencompra` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_ordencompra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_ordencompra_producto`
--

DROP TABLE IF EXISTS `tbl_ordencompra_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_ordencompra_producto` (
  `orp_ordencompraproductoid` int(11) NOT NULL AUTO_INCREMENT,
  `orp_cantidad` int(11) DEFAULT NULL,
  `orp_valorcompra` double DEFAULT NULL,
  `fk_producto` int(11) DEFAULT NULL,
  `fk_proveedor` int(11) DEFAULT NULL,
  `fk_bodega` int(11) DEFAULT NULL,
  `fk_ordencompra` int(11) DEFAULT NULL,
  PRIMARY KEY (`orp_ordencompraproductoid`),
  KEY `fk_producto_idx` (`fk_producto`),
  KEY `fk_proveedoro_idx` (`fk_proveedor`),
  KEY `fk_bodegao_idx` (`fk_bodega`),
  KEY `fk_ordencomprao_idx` (`fk_ordencompra`),
  CONSTRAINT `fk_bodegao` FOREIGN KEY (`fk_bodega`) REFERENCES `tbl_bodega` (`bdg_bodegaid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ordencomprao` FOREIGN KEY (`fk_ordencompra`) REFERENCES `tbl_ordencompra` (`odc_ordencompraid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productoo` FOREIGN KEY (`fk_producto`) REFERENCES `tbl_producto` (`pdt_productoid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_proveedoro` FOREIGN KEY (`fk_proveedor`) REFERENCES `tbl_usuario` (`usu_usuarioid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_ordencompra_producto`
--

LOCK TABLES `tbl_ordencompra_producto` WRITE;
/*!40000 ALTER TABLE `tbl_ordencompra_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_ordencompra_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_producto`
--

DROP TABLE IF EXISTS `tbl_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_producto` (
  `pdt_productoid` int(11) NOT NULL AUTO_INCREMENT,
  `pdt_nombre` varchar(255) DEFAULT NULL,
  `pdt_descripcion` varchar(255) DEFAULT NULL,
  `pdt_valorventa` double DEFAULT NULL,
  `fk_categoria` int(11) DEFAULT NULL,
  `pdt_total` int(11) DEFAULT NULL,
  PRIMARY KEY (`pdt_productoid`),
  KEY `fk_categoriap_idx` (`fk_categoria`),
  CONSTRAINT `fk_categoriap` FOREIGN KEY (`fk_categoria`) REFERENCES `tbl_categoria` (`cat_categoriaid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_producto`
--

LOCK TABLES `tbl_producto` WRITE;
/*!40000 ALTER TABLE `tbl_producto` DISABLE KEYS */;
INSERT INTO `tbl_producto` VALUES (3,'Galaxy A02','Más espacio para disfrutar Con la pantalla Infinity-V Display de 6.5” y su tecnología HD+, verás tu contenido más nítido, brillante y claro.',430000,3,50),(4,'Celular MOTOROLA G30 128GB Lil','Una experiencia increíblemente fluida con tu MOTO G30.| Frecuencia de actualización de 90 Hz en una pantalla ultra ancha Max Vision HD+. para disfrutar tus juegos mucho más reales y un desplazamiento perfecto. Si buscas capturar fotos de alta resolución',679900,3,10),(5,'Armario para TV 52\" MADERKIT Ceniza','El ARMARIO PARA TV MADERKIT es un closet para toda la familia y su material es en aglomerado, cuenta con: tubo para colgar ropa, soporte para TV, 2 cajones y espacio suficiente en el interior para ordenar zapatos, cobijas y más.',430000,4,5),(6,'Armario MADERKIT Nova 200 Cm Wengue','Organiza tu habitación con este ARMARIO MADERKIT Nova de 200 cm es un closet para toda la familia y su material es en aglomerado, cuenta con 2 tubos para colgar (superiores).',879900,4,2),(7,'Consola XBOX Series S + 1 Control Inalámbrico','Solo digital , Todo de nueva generación: Pásate a lo digital con la Xbox Series S y crea una biblioteca de juegos digitales. Tus juegos, partidas guardadas y copias de seguridad están a salvo en la nube',1500000,11,6),(8,'Consola PS4 Megapack 15 1 Tera + 1 Control + 3 Juegos + Suscripción 3 Meses a PlayStation Plus','La consola PlayStation 4, ahora más liviana y delgada, tiene un disco duro de 1TB para que disfrutes los mejores juegos, tu música favorita y mucho más. El paquete PlayStation MEGA PACK ofrece juegos increíbles, conectados y dinámicos',1600000,11,6),(10,'LEGO Speed Champions Ferrari F40 Competizione','¡Construye, compite y decora con el coche de juguete LEGO® Speed Champions Ferrari F40 Competizione (75890)! Transfórmalo también en el clásico turismo F40: el último coche que supervisó Enzo Ferrari.',77000,12,89),(11,'LEGO Disney Frozen Ii Aventura En Carreta De Elsa','Regala la oportunidad de recrear los mágicos momentos de Frozen II, la película de Disney, con la Aventura en Carreta de Elsa LEGO® | Disney (41166)! Este set de construcción de juguetes',105000,12,89);
/*!40000 ALTER TABLE `tbl_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_producto_has_tbl_foto`
--

DROP TABLE IF EXISTS `tbl_producto_has_tbl_foto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_producto_has_tbl_foto` (
  `fk_productoid` int(11) DEFAULT NULL,
  `fk_fotoid` int(11) DEFAULT NULL,
  KEY `fk_pro_idx` (`fk_productoid`),
  KEY `fk_fto_idx` (`fk_fotoid`),
  CONSTRAINT `fk_fto` FOREIGN KEY (`fk_fotoid`) REFERENCES `tbl_foto` (`fot_fotoid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pro` FOREIGN KEY (`fk_productoid`) REFERENCES `tbl_producto` (`pdt_productoid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_producto_has_tbl_foto`
--

LOCK TABLES `tbl_producto_has_tbl_foto` WRITE;
/*!40000 ALTER TABLE `tbl_producto_has_tbl_foto` DISABLE KEYS */;
INSERT INTO `tbl_producto_has_tbl_foto` VALUES (3,11),(3,12),(3,13),(3,14),(4,15),(4,16),(4,17),(4,18),(4,19),(5,20),(5,21),(6,22),(6,23),(6,24),(6,25),(6,26),(6,27),(7,28),(7,29),(7,30),(7,31),(8,32),(8,33),(8,34),(10,35),(10,36),(10,37),(10,38),(11,39),(11,40);
/*!40000 ALTER TABLE `tbl_producto_has_tbl_foto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_rol`
--

DROP TABLE IF EXISTS `tbl_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_rol` (
  `rol_rolid` int(11) NOT NULL AUTO_INCREMENT,
  `rol_nombre` varchar(45) DEFAULT NULL,
  `rol_descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rol_rolid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_rol`
--

LOCK TABLES `tbl_rol` WRITE;
/*!40000 ALTER TABLE `tbl_rol` DISABLE KEYS */;
INSERT INTO `tbl_rol` VALUES (1,'Administrador','administrador'),(2,'Cliente','cliente'),(3,'Vendedor','vendedor'),(4,'Operario','operario'),(5,'Proveedor','proveedor');
/*!40000 ALTER TABLE `tbl_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_usuario`
--

DROP TABLE IF EXISTS `tbl_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_usuario` (
  `usu_usuarioid` int(11) NOT NULL AUTO_INCREMENT,
  `usu_tipodocumento` varchar(45) DEFAULT NULL,
  `usu_numerodocumento` bigint(12) DEFAULT NULL,
  `usu_nombres` varchar(45) DEFAULT NULL,
  `usu_apellidos` varchar(45) DEFAULT NULL,
  `usu_correo` varchar(45) DEFAULT NULL,
  `usu_clave` varchar(45) DEFAULT NULL,
  `usu_telefono` varchar(45) DEFAULT NULL,
  `usu_direccion` varchar(45) DEFAULT NULL,
  `usu_estado` tinyint(4) DEFAULT '1',
  `usu_foto` varchar(255) DEFAULT 'default.png',
  PRIMARY KEY (`usu_usuarioid`),
  UNIQUE KEY `usu_correo_UNIQUE` (`usu_correo`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_usuario`
--

LOCK TABLES `tbl_usuario` WRITE;
/*!40000 ALTER TABLE `tbl_usuario` DISABLE KEYS */;
INSERT INTO `tbl_usuario` VALUES (1,'Tarjeta de identidad',1010105885,'JELIT','PAOLA','manrique.paola14@gmail.com','12345','2112525','CALLE 65 # 12 -3',1,'default.png'),(2,'Cedula',1004149400,'NICOLAS','CHAVARRO','niickolasalarcon27@gmail.com','12345','2114556','DIG 145 # 56 -5',1,'20210821111026964.jpg'),(3,'Cedula',1001056683,'EDGAR','ROMERO','maurocreew17@gmail.com','12345','2114748','AV. 5 # 45 -89',0,'default.png'),(4,'Cedula',1001309611,'JUAN DAVID','SARMIENTO ROCHA','judasaro117@gmail.com','judasaro117','2114878','CARRERA 8 # 24 - 96',1,'default.png'),(5,'Cedula',11111,'Jose Luis','Sarta Alvarez','josarta@misena.edu.co','12345','3107898945','calle 127 # 12 -3',1,'20210821110807265.png'),(6,'Cedula',22222,'User NDos','User ADos','userDos@misena.edu.co','12345',NULL,NULL,1,'default.png'),(7,'Cedula',3333,'User NTres','User ATres','userTres@misena.edu.co','12345',NULL,'0',1,'default.png'),(8,'Cedula',44444,'User NCuatro','User ACuatro','usercuatro@misena.edu.co','12345',NULL,NULL,1,'default.png'),(10,'Cedula',55555,'User NCinco','User ACinco','userCinco@misena.edu.co','12345',NULL,NULL,0,'default.png'),(12,'Cedula',666666,'User N6','User A6','user6@misena.edu.co','12345',NULL,NULL,1,'default.png'),(13,'Cedula',999999,'User N9','User N9','user9@misena.edu.co','12345',NULL,NULL,0,'default.png');
/*!40000 ALTER TABLE `tbl_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_usuario_has_tbl_rol`
--

DROP TABLE IF EXISTS `tbl_usuario_has_tbl_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_usuario_has_tbl_rol` (
  `fk_usuarioid` int(11) DEFAULT NULL,
  `fk_rolid` int(11) DEFAULT NULL,
  KEY `fk_usu_idx` (`fk_usuarioid`),
  KEY `fk_rol_idx` (`fk_rolid`),
  CONSTRAINT `fk_rol` FOREIGN KEY (`fk_rolid`) REFERENCES `tbl_rol` (`rol_rolid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usu` FOREIGN KEY (`fk_usuarioid`) REFERENCES `tbl_usuario` (`usu_usuarioid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_usuario_has_tbl_rol`
--

LOCK TABLES `tbl_usuario_has_tbl_rol` WRITE;
/*!40000 ALTER TABLE `tbl_usuario_has_tbl_rol` DISABLE KEYS */;
INSERT INTO `tbl_usuario_has_tbl_rol` VALUES (5,2),(5,3),(5,4),(5,1),(5,5),(2,2);
/*!40000 ALTER TABLE `tbl_usuario_has_tbl_rol` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-28 10:43:10
