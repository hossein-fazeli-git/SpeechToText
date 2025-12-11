docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker rmi $(docker images -a -q)
#docker volume prune
docker login docker.io -u  hosseindockerhub -p 551360Azadi#
docker pull hosseindockerhub/speech_to_text:latest
docker pull hosseindockerhub/speech_to_text_nginx:latest
docker compose up





