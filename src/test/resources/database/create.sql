-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema neoIndexer
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema neoIndexer
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `neoIndexer` DEFAULT CHARACTER SET utf8 ;
USE `neoIndexer` ;

-- -----------------------------------------------------
-- Table `neoIndexer`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `neoIndexer`.`admin` (
  `idAdminPk` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idAdminPk`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `neoIndexer`.`block`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `neoIndexer`.`block` (
  `idBlockPk` INT NOT NULL AUTO_INCREMENT,
  `height` INT NOT NULL,
  `hash` INT NOT NULL,
  `creationDate` INT NOT NULL,
  `sizeInBytes` INT NOT NULL,
  PRIMARY KEY (`idBlockPk`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `neoIndexer`.`transaction_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `neoIndexer`.`transaction_type` (
  `idTransactionTypePk` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idTransactionTypePk`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `neoIndexer`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `neoIndexer`.`transaction` (
  `idTransactionPk` INT NOT NULL AUTO_INCREMENT,
  `hash` VARCHAR(255) NOT NULL,
  `idTypeFk` INT NOT NULL,
  `from` VARCHAR(255) NOT NULL,
  `idBlockFk` INT NOT NULL,
  PRIMARY KEY (`idTransactionPk`),
  INDEX `idBlockFk_idx` (`idBlockFk` ASC),
  INDEX `idTypeFk_idx` (`idTypeFk` ASC),
  CONSTRAINT `idBlockFk`
  FOREIGN KEY (`idBlockFk`)
  REFERENCES `neoIndexer`.`block` (`idBlockPk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idTypeFk`
  FOREIGN KEY (`idTypeFk`)
  REFERENCES `neoIndexer`.`transaction_type` (`idTransactionTypePk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `neoIndexer`.`asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `neoIndexer`.`asset` (
  `idAssetPk` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  PRIMARY KEY (`idAssetPk`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `neoIndexer`.`transaction_input`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `neoIndexer`.`transaction_input` (
  `idTransactionInputPk` INT NOT NULL AUTO_INCREMENT,
  `previousIdTransactionFk` INT NULL,
  `idTransactionFk` INT NOT NULL,
  `amount` INT NOT NULL,
  `spent` TINYINT(1) NOT NULL,
  `to` VARCHAR(255) NOT NULL,
  `idAssetFk` INT NULL,
  PRIMARY KEY (`idTransactionInputPk`),
  INDEX `previousIdTransactionFk_idx` (`previousIdTransactionFk` ASC),
  INDEX `idTransactionFk_idx` (`idTransactionFk` ASC),
  INDEX `fk_idAssetFk_idx` (`idAssetFk` ASC),
  CONSTRAINT `fk_previousIdTransactionFk`
  FOREIGN KEY (`previousIdTransactionFk`)
  REFERENCES `neoIndexer`.`transaction` (`idTransactionPk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_idTransactionFk`
  FOREIGN KEY (`idTransactionFk`)
  REFERENCES `neoIndexer`.`transaction` (`idTransactionPk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_idAssetFk`
  FOREIGN KEY (`idAssetFk`)
  REFERENCES `neoIndexer`.`asset` (`idAssetPk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
