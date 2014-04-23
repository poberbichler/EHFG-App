#!/bin/bash
readonly PARENT=~/git-repository/app-parent
readonly TARGET=~/git-repository/tomcat-ehfg/

cd $PARENT
mvn clean package -DskipTests
mv "app-web/target/app-web-0.0.1-SNAPSHOT.war" "$TARGET/app-web.war"
cd $TARGET
git add app-web.war
git commit -m "deploy version"
git push