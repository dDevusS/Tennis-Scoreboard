# Используем базовый образ с JDK и Gradle
FROM gradle:latest AS builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем исходный код в контейнер
COPY . .

# Собираем приложение с помощью Gradle
RUN gradle build

# Используем базовый образ Tomcat для деплоя приложения
FROM tomcat:latest
LABEL authors="ddevuss"

# Копируем WAR-файл из предыдущего этапа сборки в каталог webapps Tomcat
COPY --from=builder /app/build/libs/Tennis-Scoreboard.war /usr/local/tomcat/webapps/

# Указываем порт, на котором будет работать Tomcat
EXPOSE 9090

# Команда запуска Tomcat при старте контейнера
CMD ["catalina.sh", "run"]

