#!/usr/bin/env bash

username=$1
password=$2
basePath=/usr/local/pop3-api
app=pop3-api-0.0.1-SNAPSHOT.jar
shellPath=/usr/local/pop3-api.sh

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

rm -rf $basePath && mkdir -p $basePath
git clone https://github.com/serical/pop3-api $basePath

killall java
mvn -DskipTests=true -f $basePath/pom.xml clean package
echo "完成pop3邮件接口打包..."

# 生成管理shell
cat >$shellPath <<EOF
#!/usr/bin/env bash

cmd=\$1                   # 获取执行脚本的时候带的参数
pid=\$(pgrep -f "${app}") # 抓取对应的java进程

startup() {
  out=\$(nohup java -jar $basePath/target/$app --username="$username" --password="$password" >$basePath/target/nohup.out 2>&1 &)
  echo "\$out"
}

if [ ! "\$cmd" ]; then
  echo "Please specify args 'start|restart|stop'"
  exit
fi

if [ "\$cmd" == 'start' ]; then
  if [ ! "\$pid" ]; then
    startup
  else
    echo "$app is running! pid=\$pid"
  fi
fi

if [ "\$cmd" == 'restart' ]; then
  if [ "\$pid" ]; then
    echo "\$pid will be killed after 3 seconds!"
    sleep 3
    kill -9 "\$pid"
  fi
  startup
fi

if [ "\$cmd" == 'stop' ]; then
  if [ "\$pid" ]; then
    echo "\$pid will be killed after 3 seconds!"
    sleep 3
    kill -9 "\$pid"
  fi
  echo "$app is stopped"
fi
EOF
chmod +x $shellPath

# 生成服务配置文件
cat >/etc/systemd/system/pop3-api.service <<EOF
[Unit]
Description=Pop3邮件服务接口
After=syslog.target network.target nss-lookup.target

[Service]
User=root
Group=root
Type=forking
ExecStart=$shellPath start
ExecReload=$shellPath restart
ExecStop=$shellPath stop
PrivateTmp=true

[Install]
WantedBy=multi-user.target
EOF

# 设置自启动,启动服务
systemctl enable pop3-api.service
systemctl restart pop3-api.service
