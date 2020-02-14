# 部署脚本

#!/bin/sh
SHELL_FOLDER=$(dirname $(readlink -f "$0"))
nginxpath=/etc/nginx/

echo "拉取git仓库"

git pull

echo "maven 清理"

mvn clean

echo "编译打包"

mvn package -Dmaven.test.skip=true

echo "准备启动备份容器"

sudo cp ${SHELL_FOLDER}/solitaire-start/target/solitaire.jar /home/solitaire_back.jar

docker start solitaire_back

sleep 30s
echo "准备切换nginx 到备份容器"
cd ${nginxpath}conf.d

sudo cp solitaire_back.conftemp solitaire.conf

service nginx reload

echo "切换nginx到备份容器端口完毕"

sleep 2s

echo "关闭主容器"
docker stop solitaire_master

echo "重新启动主容器"
sudo cp ${SHELL_FOLDER}/solitaire-start/target/solitaire.jar /home/solitaire_master.jar

docker start solitaire_master

sleep 30s

echo "切换nginx到主容器"

cd ${nginxpath}conf.d

sudo cp solitaire_master.conftemp solitaire.conf

service nginx reload

echo "切换nginx到主容器端口完毕"

echo "关闭备份容器"

docker stop solitaire_back





#sudo cp solitaire-start/target/solitaire.jar /home/solitaire_back.jar
#sudo cp solitaire-start/target/solitaire.jar /home/solitaire_master.jar




#docker run -d  -v /home/solitaire_back.jar:/home/solitaire.jar --name solitaire_back -p 8081:8080 --link mysql:mysql openjdk:8  java -jar /home/solitaire.jar --spring.profiles.active=prod
#docker run -d  -v /home/solitaire_master.jar:/home/solitaire.jar --name solitaire_master -p 8080:8080 --link mysql:mysql openjdk:8  java -jar /home/solitaire.jar --spring.profiles.active=prod