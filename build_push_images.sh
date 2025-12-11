#!/bin/bash
# Exit immediately if a command exits with a non-zero status
set -e

DOCKER_USERNAME=$1
DOCKER_PASSWORD=$2

# 2. Log in to Docker Hub (or GitHub Container Registry)
echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
echo "Logged in to Docker registry"
docker build  -t  hosseindockerhub/speech_to_text .
docker push hosseindockerhub/speech_to_text
cd ../
cd SpeechToText/src/main/nginx
docker build  -t  hosseindockerhub/speech_to_text_nginx .
docker push hosseindockerhub/speech_to_text_nginx

docker logout



