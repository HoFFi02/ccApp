-- Wyłącz sprawdzanie kluczy obcych i unikalności
SET foreign_key_checks = 0;

-- Usuń dane z tabeli 'lista_zakupow'
TRUNCATE TABLE ccApp.lista_zakupow;

-- Usuń dane z tabeli 'przepisy_produkty'
TRUNCATE TABLE ccApp.przepisy_produkty;

-- Usuń dane z tabeli 'dzien'
TRUNCATE TABLE ccApp.dzien;

-- Usuń dane z tabeli 'przepisy'
TRUNCATE TABLE ccApp.przepisy;

-- Usuń dane z tabeli 'produkty'
TRUNCATE TABLE ccApp.produkty;

-- Usuń dane z tabeli 'posilek'
TRUNCATE TABLE ccApp.posilek;

-- Włącz ponownie sprawdzanie kluczy obcych i unikalności
SET foreign_key_checks = 1;
