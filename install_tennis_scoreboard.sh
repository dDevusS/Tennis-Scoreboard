#!/bin/bash

PROJECT_NAME="Tennis-Scoreboard"
LOCAL_DIR=$(pwd)
MANAGER_FILE="manager_container_tennis_scoreboard.sh"

echo "Cloning the repository https://github.com/dDevusS/$PROJECT_NAME.git"
git clone https://github.com/dDevusS/$PROJECT_NAME.git || {
  echo "Error: filed to clone from https://github.com/dDevusS/$PROJECT_NAME.git.";
  echo "Please, look into README on https://github.com/dDevusS/$PROJECT_NAME.";
  exit 1;
  }

cd ./$PROJECT_NAME

chmod +x ./$MANAGER_FILE

echo "Running installation script..."
if ! sh ./$MANAGER_FILE install; then
    echo "Error: Installation script failed. Exiting."
    exit 1
fi

cd ..

echo "Create $MANAGER_FILE in $LOCAL_DIR"
mv ./$PROJECT_NAME/$MANAGER_FILE ./$MANAGER_FILE || {
  echo "Error: Failed to move $MANAGER_FILE to $LOCAL_DIR";
  exit 1;
  }

echo "Removing the script file and cloned repository."
rm -rf ./$PROJECT_NAME
rm "$0"

sh $MANAGER_FILE --help