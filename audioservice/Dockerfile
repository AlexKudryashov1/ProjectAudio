FROM eclipse-temurin:17-jdk-jammy  # Базовый образ Java

WORKDIR /app                       # Рабочая директория в контейнере

# Копируем JAR-файл из target/ (собранный Maven/Gradle)
COPY target/audio-service-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]  # Запуск приложения