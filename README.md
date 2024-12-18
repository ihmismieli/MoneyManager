# Money Manager

Money Manager on sovellus, joka auttaa sinua seuraamaan henkilökohtaisia tuloja ja menoja.
Sovellukseen voi ladata CSV-tiedostoja, joiden pohjalta voit tarkastella menoja kategorioittain ja hakea tietoa transaktioista halutulta aikaväliltä.

Alhaalla Back End -kurssin sovelluksen deployment ohjeet

# Deployment ohjeet

Ohjeet spring boot back endin julkaisuun matalalla tasolla. 

Spring Boot projekti on defaulttina ns. Stand-alone eli se muodostaa jar-tiedoston, jossa on kaikki
myös joissain tapauksissa H2 kanta, mutta H2 kantaa ei voi käyttää tuotannossa. 

#Creation of jar file
./mvnw clean

#Maven toimii kommenolla clean siten, että se tekee uudestaan .java tiedostoista .class tiedostoja

 ./mvnw package -DskipTests
 
Tällä komennolla saadaan luotua ns. Executable jar, joka voidaan suorittaa paikallisesti PC:llä tai pilvitietokoneella. 
Testien skippaaminen ei ole aina tarkoituksenmukaista, koska silloin CI/CD pipelinessä jää testit ajamatta. 
Skippaus tehtiin, koska maven ei suostunut tekemään jar-tiedostoa muuten. Maven package komento luo jar-tiedoston, joka on vähän kuin zip-tiedosto, joka on “exe”. 

#jar to cloud computer

Kun jar tiedostoa viedään pilveen on tärkeää olla esimerkiksi oikeassa hakemistossa eli target hakemistossa tai olla tietoinen missä target hakemisto on.

scp target/helloworld.jar ownuseraccountnametypehere@softala.haaga-helia.fi:

Tämän voi myös tehdä winscp kautta.

#Executing jar file
Seuraavaksi otetaan etäyhteys softala.haaga-helia.fi nimiseen virtual private serviceen eli pilveen. 

ssh omakayttajatunnus@softala.haaga-helia.fi

Ajetaan “executable” jar-tiedosto kokeeksi komentoriviltä. Tämän pitää toimia ensin paikallisella PC:llä ja jos se toimii siinä, niin voidaan kokeilla myös pilvessä.

java -jar helloworld.jar


# Ssh tunneli
Eli opiskelijan omilla tunnuksilla näin. Tämä välivaihe ei ole pakollinen. Tehdään sen vuoksi,
että todetaan saadaanko tietokanta toimimaan pilvessä, kun sovellus on vielä PC:llä. 

$ ssh -L 3306:localhost:3306 bhi086@softala.haaga-helia.fi

Root tunnukset eli pääkäyttäjän tunnukset
Jotta saadaan luotua esimerkiksi uusia servicejä täytyy olla ns. Sudo oikeudet, joita voi lisätä visudo komennolla. 
Tässä ohjeessa oletetaan, että käyttäjälle on jo laitettu sudo oikeudet eli että hän voi suorittaa komentoja 
pääkäyttäjänä ja pystyy vaihtamaan itsensä pääkäyttäjäksi. 

bhi086@softala:~$ sudo su
[sudo] password for bhi086:
root@softala:/home/bhi086# ls
BOOT-INF  META-INF  bookstoreheidi-SNAPSHOT.jar  moneymanager-0.0.1-SNAPSHOT.jar  org
 
# Servicen luominen

#Luodaan service-tiedosto
root@softala:/home/bhi086# cd /etc/systemd
root@softala:/etc/systemd# cd system
root@softala:/etc/systemd/system# ls

Cat-komento tulostaa minkä tahansa tekstitiedoston näytölle
Service-tiedoston sisällä pitää olla editoituna oikea jarrin sijainti,
voit editoida tiedostoa helpoiten nano nimisellä editorilla: 

root@softala:/etc/systemd/system# nano heidiappmoneymanager.service

Editoi tiedostoon seuraava sisältö:
[Unit]
Description=Manage Java service
[Service]
WorkingDirectory=/opt/prod
ExecStart=/usr/bin/java -Xms128m -Xmx256m -jar /home/bhi086/moneymanager-0.0.1-SNAPSHOT.jar
User=jvmapps
Type=simple
Restart=on-failure
RestartSec=10 
[Install]
WantedBy=multi-user.target

Palvelun uudelleen käynnistys 
root@softala:/etc/systemd/system# service heidiappmoneymanager restart
root@softala:/etc/systemd/system# service heidiappmoneymanager status

#Tarkista onko service päällä

systemctl status heidiappmoneymanager


# Softalan tietokannan hallinta
 
bhi086@softala:~$ sudo su
[sudo] password for bhi086:
root@softala:/home/bhi086# mysql -u root -p
Enter password:
mysql> create database mm;

Anna oikeudet käyttäjälle bhi086
mysql> GRANT ALL PRIVILEGES ON moneymanager.* TO 'bhi086'@'localhost';
  
Kirjautuminen softalan tietokantaan
bhi086@softala:~$ mysql -u bhi086 -p
exit pääsee pois
 
softala tietokannan käyttö
bhi086@softala:~$ mysql -u root -p
 
Muista tehdä restart (pitää tehdä root käyttäjänä)

 root@softala:/etc/systemd/system# service heidiappmoneymanager restart
journalctl -u heidiappmoneymanager -f

# gitignore luominen
 
Luo aina application.properties .gitignore, kun haluat piilottaa application properties olevat salasanat
heidi@RVLT577 MINGW64 ~/OneDrive/desktop/Syksy 2024 kevät 2025/BackEnd/MoneyManager/moneymanager (main)
  notepad .gitignore
 
Lisää notepadiin loppuun:  application.properties
