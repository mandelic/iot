## Sound of Silence

Jednostavna Spring Boot aplikacija za povezivanje na ThingsBoard platformu. Omogućava stvaranje i pohranjivanje internih korisnika aplikacije, objekata prostorije te dodjeljivanje prava korisnicima za pristup određenim prostorijama. Također je omogućena funkcija brisanja postojećih prostorija.
Preko ThingsBoard platforme omogućeno je stvaranje novih asseta, dohvaćanje svih deviceva koji su u relaciji s određenim assetom, kreiranje novih deviceva u relaciji s određenim assetom te dohvaćanje telemetrijskih podataka za određeni uređaj. 

# UPUTE ZA POKRETANJE:

1. Testiranje povezanosti na bazu podataka.   
   username: postgres.rmgzchxabieynavmukqm  
   password: thesoundofsilence  
   url: jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?prepareThreshold=0  
2. Lokalno pokretanje Spring Boot aplikacije preko IDE-a
3. Osnovni podaci za login:
   - ADMIN - username: admin, password: admin
   - USER - username: pero.peric@gmail.com, password: user
4. Aplikacija je također deployana te je pristup njenom API-ju moguć i preko poveznice: https://iot-1-uo7o.onrender.com
   - u tom slučaju nije potrebno lokalno pokretati aplikaciju
5. Spremljeni backend pozivi također su dostupni u Postman kolekciji:
   https://app.getpostman.com/join-team?invite_code=1ada1bed50bb1abc8451033b4b710183&target_code=401212c70a91028d2e17d739e98e002c
