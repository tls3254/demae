#!/bin/bash

BUILD_JAR=$(ls /home/ec2-user/action/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
DEPLOY_PATH=/home/ec2-user/action/

echo ">>> build 파일명: $JAR_NAME" >> $DEPLOY_PATH/deploy.log

echo ">>> build 파일 복사" >> $DEPLOY_PATH/deploy.log
cp $BUILD_JAR $DEPLOY_PATH

echo ">>> 현재 실행중인 애플리케이션 pid 확인" >> $DEPLOY_PATH/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]; then
  echo ">>> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $DEPLOY_PATH/deploy.log
else
  echo ">>> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID

  # 종료를 기다리는 시간을 더 늘림
  TIMEOUT=60
  while [ -e /proc/$CURRENT_PID ] && [ $TIMEOUT -gt 0 ]; do
    sleep 1
    TIMEOUT=$((TIMEOUT - 1))
  done

  if [ $TIMEOUT -eq 0 ]; then
    echo ">>> 애플리케이션이 종료되지 않아 강제 종료합니다." >> $DEPLOY_PATH/deploy.log
    kill -9 $CURRENT_PID
  else
    echo ">>> 애플리케이션이 성공적으로 종료되었습니다." >> $DEPLOY_PATH/deploy.log
  fi
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo ">>> DEPLOY_JAR 배포" >> $DEPLOY_PATH/deploy.log
nohup java -jar $DEPLOY_JAR >> $DEPLOY_PATH/deploy.log 2>> $DEPLOY_PATH/deploy_err.log &
