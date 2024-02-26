SELECT p.nazwa AS nazwa_produktu, lz.ilosc, p.jednostka
FROM ccApp.lista_zakupow lz
JOIN ccApp.przepisy_produkty pp ON lz.przepisy_produkty_id_przepisy_produkty = pp.id_przepisy_produkty
JOIN ccApp.produkty p ON pp.produkty_id_produkty = p.id_produkty;
