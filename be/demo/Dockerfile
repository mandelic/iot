# Container za izgradnju (build) aplikacije
FROM openjdk:17-alpine AS builder

# Kopiranje izvornog koda u container
COPY /gradle gradle
COPY /gradlew .
COPY /build.gradle.kts .
COPY /settings.gradle.kts .
COPY /src src
RUN chmod +x gradlew

# Pokretanje builda
RUN ./gradlew assemble

# Stvaranje containera u kojem ce se vrtiti aplikacija
FROM openjdk:17-alpine

## Ovdje je moguce instalirati alate potrebne za rad aplikacije. Vjerojatno vam nece trebati, no dobro je znati.
## Linux distro koji se koristi je Alpine, stoga se kao package manager koristi apk
#RUN apk install <nesto>

## Kopiranje izvrsnog JAR-a iz build containera u izvrsni container
COPY --from=builder /build/libs/*.jar /app.jar

# Izlaganje porta
EXPOSE 8080

# Naredba kojom se pokrece aplikacija
CMD ["-start"]
#RUN ["chmod", "+x", "/app.jar"]
ENTRYPOINT ["java","-jar","/app.jar"]
