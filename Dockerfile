# Используем базовый образ Tomcat
FROM tomcat:latest
LABEL authors="ddevuss"

# Копируем ваш WAR-файл в каталог webapps Tomcat
COPY ./build/libs/Tennis-Scoreboard.war /usr/local/tomcat/webapps/

# Указываем порт, на котором будет работать Tomcat
EXPOSE 9090

# Команда запуска Tomcat при старте контейнера
CMD ["catalina.sh", "run"]
