#./gradlew clean --exclude-task test build
#
#docker compose up -d --build

./gradlew clean --exclude-task test :bootBuildImage

docker compose up -d --build