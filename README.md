# Budjetointisovellus
Projekti kurssille Ohjelmistotekniikan menetelmät. Sovelluksessa käyttäjät voivat luoda itselleen budjetteja ja seurata rahan käyttöä.

## Dokumentaatio
[Käyttöohje](https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/vaatimusm%C3%A4%C3%A4rittely.md)

[Tuntikirjanpito](https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Ensimmäinen Release](https://github.com/OlliJ5/otm-harjoitustyo/releases)


## Komentorivikomennot

### Suoritus
Ohjelman voi suorittaa komennolla
```
mvn compile exec:java -Dexec.mainClass=budjetointisovellus.ui.Main
```

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

### Jarin generointi
Suoritettavan jarin voi generoida komennolla
```
mvn package
```

### JavaDoc
JavaDoc generoidaan komennolla
```
mvn javadoc:javadoc
```




