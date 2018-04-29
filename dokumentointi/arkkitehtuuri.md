# Arkkitehtuurikuvaus

## Rakenne
Ohjelma koostuu kolmesta kerroksesta, joita ovat ui, domain sekä dao. 

Ohjelman pakkausrakenne:

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/pakkausrakenne.png" width="180">

Ui-pakkaus sisältää JavaFX:llä tehdyn käyttöliittymän, domain vastaa sovelluslogiikasta ja dao vastaa tietojen tallentamisesta tietokantaan ja sieltä tiedon hakemisesta

## Sovelluslogiikka

Sovelluksessa on luokat User, Budget ja Expense, jotka luovat sovelluksen loogisen datamallin. User kuvaa käyttäjää, Budget käyttäjiin liittyviä budjetteja ja Expense bujetteihin kuuluvia kuluja.

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/luokkakaavio1.png" width="600">

Sovelluslogiikasta vastaa BudgetService luokka, josta on luotu olio käyttöliittymän käytettäväksi. Luokka tarjoaa käyttöliittymälle metodeja käyttöliittymän toiminnoille. Tässä muutama esimerkki:

* boolean createUser(String username, String name, String password)
* boolean login(String username, String password)
* boolean createBudget(Budget budget, User user)
* boolean deleteBudget(Budget budget, User user)

BudgetService pääsee käsiksi käyttäjiin, budjetteihin ja kuluihin rajapintojen UserDao, BudgetDao sekä ExpenseDao toteuttavien luokkien kautta, jotka sijaitsevat pakkauksessa budjetointisovellus.dao.

Koko ohjelmaa kuvaava pakkaus/luokkakaavio:

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/pakettikaavioOTM.png" width="800">

## Tietojen pysyväistallennus

Budjetointisovellus.dao-pakkauksessa sijaitsevat luokat SqlUserDao, SqlBudgetDao sekä SqlExpenseDao huolehtivat tietojen pysyväistallennuksesta tietokantaan. Luokat noudattavat Data Access Object -suunnittelumallia ja ne voidaan tarvittaessa korvata uusilla toteutuksilla, jos datan talletustapaa päätetään vaihtaa. Luokat on eristetty rajapintojen TodoDao ja UserDao taakse ja sovelluslogiikka ei käytä luokkia suoraan.

## Päätoiminnallisuudet

## Kirjautuminen

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/loginDiagram.png" width="800">
