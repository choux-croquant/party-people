FROM openjdk:11-jdk

WORKDIR /opt/springboot

ADD build/libs/ssafy-web-project-1.0-SNAPSHOT.jar /opt/springboot/app.jar

ENTRYPOINT ["java","-jar","app.jar"]

VOLUME ["/opt/springboot"]
