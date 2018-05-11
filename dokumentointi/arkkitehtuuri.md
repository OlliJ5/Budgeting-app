# Arkkitehtuurikuvaus

## Rakenne
Ohjelma koostuu kolmesta kerroksesta, joita ovat ui, domain sekä dao. 

Ohjelman pakkausrakenne:

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/pakkausrakenne.png" width="180">

Ui-pakkaus sisältää JavaFX:llä tehdyn käyttöliittymän, domain vastaa sovelluslogiikasta ja dao vastaa tietojen tallentamisesta tietokantaan ja sieltä tiedon hakemisesta

## Käyttöliittymä
Käyttöliittymä sisältää neljä erillistä näkymää:
* Sisäänkirjautuminen
* Uuden käyttäjän luonti
* Uuden budjetin luonti
* Päänäkymä, jossa voi tarkastella budjetteja ja lisätä kuluja

Jokainen on toteutettu omana Scene-oliona, joita sijoitetaan yksi kerrallaan sovelluksen Stage-olioon. Käyttöliittymä on rakennettu JavaFX:llä luokassa budjetointisovellus.ui.App. Käyttöliittymä kutsuu BudgetService-olion metodeja sopivasti käyttäjän tekojen perusteella. 

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

Testauksessa käytetään osin SqlDao-luokkien sijaan FakeDao-luokkia, jotka nekin toteuttavat Dao-rajapinnat.

### Tietokanta

Tietokanta on toteutettu SQLiten avulla ja sovellus tallentaa tiedot tiedostoon budget.db. Tietokannassa on kolme taulua, jotka ovat User, Budget ja Expense.

Taulujen luomiseen käytetään seuraavia lauseita:

User: CREATE TABLE User(id integer PRIMARY KEY, username varchar(200), name varchar(200), password varchar(60));

User-taulussa kaikilla riveillä on pääavain, käyttäjätunnus, nimi ja salasana(joka on hashattu)

Budget: CREATE TABLE Budget(id integer PRIMARY KEY, user_username varchar(200), name varchar(200), amount float, FOREIGN KEY (user_username) REFERENCES User(username));

Budget-taulussa kaikilla riveillä on pääavain sekä viiteavain, joka liittyy tiettyyn käyttäjätunnuksee ja nimi ja määrä

Expense: CREATE TABLE Expense(id integer PRIMARY KEY, budget_id integer, name varchar(200), price float, FOREIGN KEY (budget_id) REFERENCES Budget(id));

Expense-taulussa kaikilla riveillä on pääavain sekä viiteavain, joka liittyy tiettyyn budjettiin ja nimi ja määrä 


## Päätoiminnallisuudet

## Kirjautuminen
Kun käyttäjä painaa 'Kirjaudu sisään' ja on syöttänyt syötekenttiin oikeat tiedot etenee sovelluksen kontrolli seuraavasti:

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/loginDiagram.png" width="800">

Kirjautumisnapin tapahtumakäsittelijä kutsuu budgetServicen metodia login, jonka parametreiksi tulee käyttäjätunnus ja salasana. budgetService selvittää userDaon avulla käyttäjätunnuksen ja salasanan paikkaansapitävyyden. Jos ne pitävät paikkaansa hakee sovellus vielä käyttöönsä tietokannasta User-olion ja vaihtaa näkymän loggedInSceneksi eli sovelluksen päänäkymäksi.
