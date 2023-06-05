FROM openjdk:8
WORKDIR /home
ADD JMX_Exporter/prometheus-jmx-config.yaml prometheus-jmx-config.yaml
ADD JMX_Exporter/jmx_prometheus_javaagent-0.16.1.jar jmx_prometheus_javaagent-0.16.1.jar
COPY ./build/libs/SpringBlog-2.8.2.jar app.jar
COPY ./application.yml application.yml
ENTRYPOINT ["java", "-javaagent:jmx_prometheus_javaagent-0.16.1.jar=8088:prometheus-jmx-config.yaml", "-Xmx1024m", "-jar", "app.jar", "--spring.config.location=application.yml"]
EXPOSE 8051 8088