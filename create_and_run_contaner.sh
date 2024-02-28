#!/bin/bash

PROJECT_NAME="Tennis-Scoreboard"
PROJECT_VERSION="1.0"
DOCKER_IMAGE="ddevuss/tennis-scoreboard:$PROJECT_VERSION"

# Function to check if Docker image exists
check_docker_image() {
    local image_exists=$(docker images -q $DOCKER_IMAGE)
    if [ -n "$image_exists" ]; then
        echo "Docker image \"$DOCKER_IMAGE\" already exists."
    else
        echo "Docker image \"$DOCKER_IMAGE\" does not exist. Building the image..."
        docker build -t $DOCKER_IMAGE ./$PROJECT_NAME/ || { echo "Error: Failed to build Docker image."; exit 1; }
    fi
}

# Function to run Docker container
run_docker_container() {
    local port=$1
    local container_name="tennis_scoreboard_container"

    # Check if a container with the given name already exists
    if docker ps -a --format '{{.Names}}' | grep -Eq "^$container_name$"; then
        echo "Container \"$container_name\" already exists."
        echo "Restarting the container on port $port..."
        docker container stop $container_name
        docker container rm $container_name
    fi

    echo "Running Docker container on port $port..."
    docker run -d -p $port:8080 --name $container_name $DOCKER_IMAGE || { echo "Error: Failed to start Docker container."; exit 1; }
}

# Check if Docker image exists
check_docker_image

# Run Docker container
if [ $# -eq 2 ] && [ $1 = "--port" ]; then
    run_docker_container $2
else
    echo "Port not specified. Running on default port (8080)..."
    run_docker_container 8080
fi