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