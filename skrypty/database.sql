-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ccApp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ccApp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ccApp` DEFAULT CHARACTER SET utf8 ;
USE `ccApp` ;

-- -----------------------------------------------------
-- Table `ccApp`.`posilek`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ccApp`.`posilek` (
  `id_posilek` INT(11) NOT NULL AUTO_INCREMENT,
  `nazwa` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_posilek`),
  UNIQUE INDEX `id_posilek_UNIQUE` (`id_posilek` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ccApp`.`produkty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ccApp`.`produkty` (
  `id_produkty` INT(11) NOT NULL AUTO_INCREMENT,
  `nazwa` VARCHAR(45) NOT NULL,
  `jednostka` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`id_produkty`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ccApp`.`przepisy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ccApp`.`przepisy` (
  `id_przepisy` INT(11) NOT NULL AUTO_INCREMENT,
  `nazwa` VARCHAR(45) NOT NULL,
  `sposob_przygotowania` VARCHAR(2000) NOT NULL,
  
  PRIMARY KEY (`id_przepisy`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ccApp`.`dzien`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ccApp`.`dzien` (
  `id_dzien` INT(11) NOT NULL AUTO_INCREMENT,
  `numer_dnia` INT(4) NOT NULL,
  `posilek_id_posilek` INT(11),
  `przepisy_id_przepisy` INT(11),
  PRIMARY KEY (`id_dzien`),
  UNIQUE INDEX `id_dzien_UNIQUE` (`id_dzien` ASC),
  INDEX `fk_dzien_posilek_idx` (`posilek_id_posilek` ASC),
  INDEX `fk_dzien_przepisy1_idx` (`przepisy_id_przepisy` ASC),
  CONSTRAINT `fk_dzien_posilek`
    FOREIGN KEY (`posilek_id_posilek`)
    REFERENCES `ccApp`.`posilek` (`id_posilek`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dzien_przepisy1`
    FOREIGN KEY (`przepisy_id_przepisy`)
    REFERENCES `ccApp`.`przepisy` (`id_przepisy`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `ccApp`.`przepisy_produkty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ccApp`.`przepisy_produkty` (
  `id_przepisy_produkty` INT NOT NULL AUTO_INCREMENT,
  `przepisy_id_przepisy` INT(11) NOT NULL,
  `produkty_id_produkty` INT(11) NOT NULL,
  `ilosc` INT(11) NOT NULL,
  PRIMARY KEY (`id_przepisy_produkty`),
  INDEX `fk_przepisy_produkty_przepisy1_idx` (`przepisy_id_przepisy` ASC),
  INDEX `fk_przepisy_produkty_produkty1_idx` (`produkty_id_produkty` ASC),
  CONSTRAINT `fk_przepisy_produkty_przepisy1`
    FOREIGN KEY (`przepisy_id_przepisy`)
    REFERENCES `ccApp`.`przepisy` (`id_przepisy`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_przepisy_produkty_produkty1`
    FOREIGN KEY (`produkty_id_produkty`)
    REFERENCES `ccApp`.`produkty` (`id_produkty`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ccApp`.`lista_zakupow`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ccApp`.`lista_zakupow` (
  `id_lista_zakupow` INT(11) NOT NULL AUTO_INCREMENT,
  `ilosc` INT(11) NOT NULL,
  `przepisy_produkty_id_przepisy_produkty` INT NOT NULL,
  PRIMARY KEY (`id_lista_zakupow`),
  INDEX `fk_lista_zakupow_przepisy_produkty1_idx` (`przepisy_produkty_id_przepisy_produkty` ASC),
  CONSTRAINT `fk_lista_zakupow_przepisy_produkty1`
    FOREIGN KEY (`przepisy_produkty_id_przepisy_produkty`)
    REFERENCES `ccApp`.`przepisy_produkty` (`id_przepisy_produkty`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `ccApp`.`uzytkownicy` (
  `id_uzytkownik` INT(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`id_uzytkownik`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- Tabela pośrednia przechowująca powiązania między użytkownikami a przepisami
CREATE TABLE IF NOT EXISTS `ccApp`.`uzytkownicy_przepisy` (
  `id_uzytkownik_przepis` INT(11) NOT NULL AUTO_INCREMENT,
  `id_uzytkownik` INT(11) NOT NULL,
  `id_przepis` INT(11) NOT NULL,
  PRIMARY KEY (`id_uzytkownik_przepis`),
  INDEX `fk_uzytkownicy_przepisy_uzytkownicy1_idx` (`id_uzytkownik` ASC),
  INDEX `fk_uzytkownicy_przepisy_przepisy1_idx` (`id_przepis` ASC),
  CONSTRAINT `fk_uzytkownicy_przepisy_uzytkownicy1`
    FOREIGN KEY (`id_uzytkownik`)
    REFERENCES `ccApp`.`uzytkownicy` (`id_uzytkownik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_uzytkownicy_przepisy_przepisy1`
    FOREIGN KEY (`id_przepis`)
    REFERENCES `ccApp`.`przepisy` (`id_przepisy`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
