FROM debian AS builder

#Install JAVA
RUN apt update
RUN apt install -y curl wget
RUN curl -O https://download.java.net/openjdk/jdk21/ri/openjdk-21+35_linux-x64_bin.tar.gz
RUN tar xvf openjdk-21+35_linux-x64_bin.tar.gz
RUN mv jdk-21 /opt/jdk-21.0.1
ENV PATH=$PATH:/opt/jdk-21.0.1/bin

#Install Maven
RUN curl -fL https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz -o maven.tar.gz
RUN tar xvf maven.tar.gz
RUN mv apache-maven-3.9.9 /opt/maven
ENV PATH=$PATH:/opt/maven/bin

COPY pom.xml /app/
COPY src /app/src
WORKDIR /app
RUN mvn dependency:go-offline -B
RUN mvn package -DskipTests -B

FROM debian AS runtime
COPY --from=builder /opt/jdk-21.0.1 /opt/jdk-21.0.1
ENV PATH=$PATH:/opt/jdk-21.0.1/bin

COPY --from=builder /app/target/routefinder-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000", "-jar", "application.jar"]
