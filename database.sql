-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema prontuario
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema prontuario
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `prontuario` DEFAULT CHARACTER SET utf8 ;
USE `prontuario` ;

-- -----------------------------------------------------
-- Table `prontuario`.`pacientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prontuario`.`pacientes` (
  `idpacientes` BIGINT(5) NOT NULL AUTO_INCREMENT,
  `cpf` VARCHAR(14) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idpacientes`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `prontuario`.`exames`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `prontuario`.`exames` (
  `idexames` BIGINT(5) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(255) NOT NULL,
  `data_exame` DATE NOT NULL,
  `pacientes_idpacientes` BIGINT(5) NOT NULL,
  PRIMARY KEY (`idexames`),
  INDEX `fk_exames_pacientes_idx` (`pacientes_idpacientes` ASC) VISIBLE,
  CONSTRAINT `fk_exames_pacientes`
    FOREIGN KEY (`pacientes_idpacientes`)
    REFERENCES `prontuario`.`pacientes` (`idpacientes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;