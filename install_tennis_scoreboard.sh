#!/bin/bash

PROJECT_NAME="Tennis-Scoreboard"
LOCAL_DIR=$(pwd)
MANAGER_FILE="manage_container_tennis_scoreboard.sh"

git clone https://github.com/dDevusS/$PROJECT_NAME.git || { echo "Error: filed to clone from https://github.com/dDevusS/$PROJECT_NAME.git"; exit 1; }

chmod +x ./$PROJECT_NAME/$MANAGER_FILE

sh ./$PROJECT_NAME/$MANAGER_FILE install

mv ./$PROJECT_NAME/$MANAGER_FILE ./$MANAGER_FILE || { echo "Error: Failed to move $MANAGER_FILE to $LOCAL_DIR"; exit 1; }

rm -r ./$PROJECT_NAME

echo "Create $MANAGER_FILE in $LOCAL_DIR"