#!/usr/bin/env bash

password=$1
if [ -z "${password}" ]; then
  echo "请输入接口认证密码"
  exit 1
fi

yum install -y java-1.8.0-openjdk
yum install -y psmisc

killall java

yum install -y git
yum install -y maven

rm -rf /root/pop3-api && mkdir -p /root/pop3-api
git clone https://github.com/serical/pop3-api /root/pop3-api
cd /root/pop3-api && mvn -DskipTests=true package && (nohup java -jar target/pop3-api-0.0.1-SNAPSHOT.jar --password="$password" &) || exit
