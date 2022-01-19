#!/usr/bin/env bash

username=$1
password=$2

if [ -z "${username}" ]; then
  echo "请输入接口认证用户名"
  exit 1
fi
if [ -z "${password}" ]; then
  echo "请输入接口认证密码"
  exit 1
fi

hash java >/dev/null 2>&1 || yum install -y java-1.8.0-openjdk
hash killall >/dev/null 2>&1 || yum install -y psmisc
hash git >/dev/null 2>&1 || yum install -y git
hash mvn >/dev/null 2>&1 || yum install -y maven

rm -rf /root/pop3-api && mkdir -p /root/pop3-api
git clone https://github.com/serical/pop3-api /root/pop3-api

killall java
cd /root/pop3-api &&
  mvn -DskipTests=true package &&
  (nohup java -jar target/pop3-api-0.0.1-SNAPSHOT.jar --username="$username" --password="$password" >nohup.out 2>&1 &) &&
  echo "完成pop3邮件接口部署..." &&
  exit
