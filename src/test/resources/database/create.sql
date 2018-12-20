-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
-- -----------------------------------------------------
-- Schema apptite
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `neoIndexer` ;

-- -----------------------------------------------------
-- Schema apptite
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `neoIndexer` DEFAULT CHARACTER SET utf8mb4 ;
USE `neoIndexer` ;
-- -----------------------------------------------------
-- Table `neoIndexer`.`block`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `neoIndexer`.`block` ;

CREATE TABLE IF NOT EXISTS `neoIndexer`.`block` (
  `idBlockPk` INT NOT NULL AUTO_INCREMENT,
  `height` INT NOT NULL,
  `dateTime` DATETIME NOT NULL,
  PRIMARY KEY (`idBlockPk`),
  UNIQUE INDEX `height_UNIQUE` (`height` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `neoIndexer`.`transactions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `neoIndexer`.`transactions` ;

CREATE TABLE IF NOT EXISTS `neoIndexer`.`transactions` (
  `idTransactionPk` INT NOT NULL AUTO_INCREMENT,
  `invocationTransactionHash` VARCHAR(255) NOT NULL,
  `idBlockFk` INT NOT NULL,
  PRIMARY KEY (`idTransactionPk`),
  INDEX `fk_transaction_block_idx` (`idBlockFk` ASC),
  CONSTRAINT `fk_transaction_block`
    FOREIGN KEY (`idBlockFk`)
    REFERENCES `neoIndexer`.`block` (`height`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `neoIndexer`.`master_registration_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `neoIndexer`.`master_registration_history` ;

CREATE TABLE IF NOT EXISTS `neoIndexer`.`master_registration_history` (
  `idMasterRegistrationPk` INT NOT NULL AUTO_INCREMENT,
  `masterAccount` VARCHAR(255) NOT NULL,
  `idBlockFk` INT NOT NULL,
  PRIMARY KEY (`idMasterRegistrationPk`),
  INDEX `fk_master_registration_block_idx` (`idBlockFk` ASC),
  CONSTRAINT `fk_master_registration_block`
    FOREIGN KEY (`idBlockFk`)
    REFERENCES `neoIndexer`.`block` (`height`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `neoIndexer` ;

-- -----------------------------------------------------
-- Table `neoIndexer`.`account_approvals_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `neoIndexer`.`account_approvals_history` ;

CREATE TABLE IF NOT EXISTS `neoIndexer`.`account_approvals_history` (
  `idApprovalPk` INT(11) NOT NULL AUTO_INCREMENT,
  `masterAccount` VARCHAR(255) NOT NULL,
  `regularAccount` VARCHAR(255) NOT NULL,
  `idBlockFk` INT NOT NULL,
  PRIMARY KEY (`idApprovalPk`),
  UNIQUE INDEX `idApprovalPk_UNIQUE` (`idApprovalPk` ASC),
  INDEX `fk_approval_block_idx` (`idBlockFk` ASC),
  CONSTRAINT `fk_approval_block`
    FOREIGN KEY (`idBlockFk`)
    REFERENCES `neoIndexer`.`block` (`height`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `neoIndexer`.`account_registration_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `neoIndexer`.`account_registration_history` ;

CREATE TABLE IF NOT EXISTS `neoIndexer`.`account_registration_history` (
  `idAccountRegistrationHistoryPk` INT(11) NOT NULL AUTO_INCREMENT,
  `accountAddress` VARCHAR(255) NOT NULL,
  `idBlockFk` INT NOT NULL,
  PRIMARY KEY (`idAccountRegistrationHistoryPk`),
  INDEX `fk_account_registration_block_idx` (`idBlockFk` ASC),
  CONSTRAINT `fk_account_registration_block`
    FOREIGN KEY (`idBlockFk`)
    REFERENCES `neoIndexer`.`block` (`height`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



-- -----------------------------------------------------
-- Table `neoIndexer`.`transaction_mint_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `neoIndexer`.`transaction_mint_history` ;

CREATE TABLE IF NOT EXISTS `neoIndexer`.`transaction_mint_history` (
  `idTransactionPk` INT(11) NOT NULL AUTO_INCREMENT,
  `transactionHash` VARCHAR(255) NOT NULL,
  `masterAccount` VARCHAR(255) NOT NULL,
  `amount` INT(11) NOT NULL,
  `recipient` VARCHAR(255) NOT NULL,
  `idBlockFk` INT NOT NULL,
  PRIMARY KEY (`idTransactionPk`),
  UNIQUE INDEX `idTransactionPk_UNIQUE` (`idTransactionPk` ASC),
  UNIQUE INDEX `transactionHash_UNIQUE` (`transactionHash` ASC),
  INDEX `fk_mint_block_idx` (`idBlockFk` ASC),
  CONSTRAINT `fk_mint_block`
    FOREIGN KEY (`idBlockFk`)
    REFERENCES `neoIndexer`.`block` (`height`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `neoIndexer`.`transfer_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `neoIndexer`.`transfer_history` ;

CREATE TABLE IF NOT EXISTS `neoIndexer`.`transfer_history` (
  `idTransferPk` INT(11) NOT NULL AUTO_INCREMENT,
  `transactionHash` VARCHAR(255) NOT NULL,
  `sender` VARCHAR(255) NOT NULL,
  `recipient` VARCHAR(255) NOT NULL,
  `changeAmount` INT(11) NOT NULL,
  `changeTransactionHash` VARCHAR(255) NULL DEFAULT NULL,
  `idBlockFk` INT NOT NULL,
  `amount` INT NOT NULL,
  PRIMARY KEY (`idTransferPk`),
  INDEX `fk_transfer_block_idx` (`idBlockFk` ASC),
  CONSTRAINT `fk_transfer_block`
    FOREIGN KEY (`idBlockFk`)
    REFERENCES `neoIndexer`.`block` (`height`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
