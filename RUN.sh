#!/bin/bash
set -e

# Docker Compose
echo "Building and starting Docker containers..."
docker compose up --build -d

# Healthy check
BACKEND_CONTAINER=$(docker compose ps -q backend)
echo "Waiting for backend container to be healthy..."
until [ "$(docker inspect -f '{{.State.Health.Status}}' $BACKEND_CONTAINER)" == "healthy" ]; do
    echo -n "."
    sleep 2
done
echo
echo "Backend is healthy!"

# npm
FRONTEND_DIR="./frontend"
echo "Installing frontend dependencies..."
cd $FRONTEND_DIR
npm install

echo "Starting frontend..."
npm start