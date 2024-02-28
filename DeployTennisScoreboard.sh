#!/bin/bash

# Переменные с именем вашего проекта и версией
PROJECT_NAME="Tennis-Scoreboard"
PROJECT_VERSION="1.0"

# Переменная с именем Docker образа
DOCKER_IMAGE="ddevuss/tennis-scoreboard:$PROJECT_VERSION"

# Клонирование репозитория из GitHub
git clone https://github.com/dDevusS/$PROJECT_NAME.git

# Сборка Docker образа
docker build -t $DOCKER_IMAGE ./$PROJECT_NAME/

# Удаление клонированного репозитория
rm -r ./$PROJECT_NAME

# Запуск Docker контейнера
docker run -d -p 9090:8080 $DOCKER_IMAGE
