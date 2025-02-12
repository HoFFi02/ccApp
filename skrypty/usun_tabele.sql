-- Usuń zależności FOREIGN KEY
ALTER TABLE `ccApp`.`dzien` DROP FOREIGN KEY `fk_dzien_posilek`;
ALTER TABLE `ccApp`.`dzien` DROP FOREIGN KEY `fk_dzien_przepisy1`;
ALTER TABLE `ccApp`.`przepisy_produkty` DROP FOREIGN KEY `fk_przepisy_produkty_przepisy1`;
ALTER TABLE `ccApp`.`przepisy_produkty` DROP FOREIGN KEY `fk_przepisy_produkty_produkty1`;
ALTER TABLE `ccApp`.`lista_zakupow` DROP FOREIGN KEY `fk_lista_zakupow_przepisy_produkty1`;

-- Usuń wszystkie tabele
DROP TABLE IF EXISTS `ccApp`.`uzytkownicy_przepisy`;
DROP TABLE IF EXISTS `ccApp`.`lista_zakupow`;
DROP TABLE IF EXISTS `ccApp`.`przepisy_produkty`;
DROP TABLE IF EXISTS `ccApp`.`dzien`;
DROP TABLE IF EXISTS `ccApp`.`przepisy`;
DROP TABLE IF EXISTS `ccApp`.`produkty`;
DROP TABLE IF EXISTS `ccApp`.`posilek`;
DROP TABLE IF EXISTS `ccApp`.`uzytkownicy`;
