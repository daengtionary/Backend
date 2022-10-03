#!/bin/bash

CURRENT_PORT=$(cat /home/ubuntu/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current port of running WAS is ${CURRENT_PORT}."

if [ ${CURRENT_PORT} -eq 8090 ]; then
  TARGET_PORT=8091
elif [ ${CURRENT_PORT} -eq 8091 ]; then
  TARGET_PORT=8090
else
  echo "> No WAS is connected to nginx"
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi

JAR_NAME=$(ls -tr /home/ubuntu/daengtionary/build/libs/*.jar | tail -n 1)
echo "> JAR Name: $JAR_NAME"
chmod +x $JAR_NAME

#REPOSITORY=/home/ubuntu/daengtionary
## shellcheck disable=SC2164
#cd $REPOSITORY
#
## shellcheck disable=SC2010
#JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'app.jar' | tail -n 1)
#JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

nohup java -jar -Dserver.port=${TARGET_PORT} $JAR_NAME > /home/ubuntu/nohup.out 2>&1 &
echo "> Now new WAS runs at ${TARGET_PORT}."
exit 0