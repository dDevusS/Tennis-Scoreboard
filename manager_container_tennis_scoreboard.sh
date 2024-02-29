#!/bin/bash

PROJECT_NAME="Tennis-Scoreboard"
PROJECT_VERSION="1.0"
DOCKER_IMAGE="ddevuss/tennis-scoreboard:$PROJECT_VERSION"
CONTAINER_NAME="tennis_scoreboard_container"
README_PATH="https://github.com/dDevusS/Tennis-Scoreboard"

# Function for checking existing image and creating if not exist.
check_docker_image() {
    local image_exists=$(docker images -q $DOCKER_IMAGE)
    if [ -n "$image_exists" ]; then
        echo "Docker image \"$DOCKER_IMAGE\" already exists."
    else
        echo "Docker image \"$DOCKER_IMAGE\" does not exist. Building the image..."
        docker build -t $DOCKER_IMAGE . || {
          echo "Error: Failed to build Docker image. Please, look into README on $README_PATH";
          exit 1;
          }
    fi
}

# Function for running Docker container.
run_docker_container() {
    local port=$1

    # Checking existing of the container and creating it, if not exist, then running the container.
    if docker ps -a --format '{{.Names}}' | grep -Eq "^$CONTAINER_NAME$"; then
        echo "Container \"$CONTAINER_NAME\" already exists. Restarting the container on port $port..."
        docker container stop $CONTAINER_NAME
        docker container rm $CONTAINER_NAME
    fi

    echo "Running Docker container on port $port..."
    docker run -d -p $port:8080 --name $CONTAINER_NAME $DOCKER_IMAGE || {
      echo "Error: Failed to start Docker container.";
      exit 1;
      }
}

# Function for stopping and deleting the container.
stop_docker_container() {
    if docker ps -a --format '{{.Names}}' | grep -Eq "^$CONTAINER_NAME$"; then
        echo "Stopping and removing Docker container \"$CONTAINER_NAME\"..."
        docker container stop $CONTAINER_NAME
        docker container rm $CONTAINER_NAME
    else
        echo "Container \"$CONTAINER_NAME\" does not exist."
    fi
}

# Function for getting help.
show_help() {
  local image_exists=$(docker images -q $DOCKER_IMAGE)
        if [ -z "$image_exists" ]; then
            echo "Usage: sh $0 {install|run [--port PORT_NUMBER]|stop}"
        else
            echo "Usage: sh $0 {run [--port PORT_NUMBER]|stop}"
        fi
    echo "Options:"
    local image_exists=$(docker images -q $DOCKER_IMAGE)
        if [ -z "$image_exists" ]; then
            echo "  install        Build Docker image from local cloned repository"
        fi
    echo "  run            Run Docker container on default port (8080)"
    echo "  --port         Specify port number"
    echo "  stop           Stop and remove Docker container"
}

# Checking arguments
if [ $# -eq 1 ]; then
    case $1 in
        "install")
            check_docker_image
            ;;
        "run")
            run_docker_container 8080
            ;;
        "stop")
            stop_docker_container
            ;;
        "--help")
            show_help
            ;;
        *)
            echo "Invalid argument. Use '--help' for usage instructions."
            exit 1
            ;;
    esac
    elif [ $# -eq 3 ] && [ $1 = "run" ] && [ $2 = "--port" ]; then
        port=$3
        if [ "$port" -eq "$port" ] 2>/dev/null; then
            run_docker_container $port
        else
            echo "Invalid port number."
            exit 1
        fi
    else
        echo "Invalid number of arguments. Use '--help' for usage instructions."
        exit 1
    fi