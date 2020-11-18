FROM java:8

LABEL maintainer = ye0108@naver.com

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/thymeleafweb-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} thymeleafweb.jar

ENTRYPOINT ["java","-jar","/thymeleafweb.jar"]
