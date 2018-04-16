# Budjetointisovellus
Projekti kurssille Ohjelmistotekniikan menetelmät

## Dokumentaatio
[Vaatimusmäärittely](https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/vaatimusm%C3%A4%C3%A4rittely.md)

[Tuntikirjanpito](https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)


## Komentorivikomennot

### Testaus

Testit suoritetaan komennolla 
```
mvn test
```

Testikattavuusraportti suoritetaan komennolla
```
mvn jacoco:report
```
Raporttia voi tarkastella avaamalla selaimella tiedoston target/site/jacoco/index.html

### Checkstyle
Checkstylen tarkistukset saa komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
Raporttia voi tarkastella avaamalla selaimella tiedoston target/site/checkstyle.html
