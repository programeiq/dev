FROM eclipse-temurin:21-jre-jammy

# さっきIntelliJで作った「全部入りJAR」を掴んで、Dockerの中に server.jar としてコピー！
COPY target/demo_new-1.0-SNAPSHOT-jar-with-dependencies.jar /server.jar

# 画面を一切作らず、裏側のサーバー機能だけで動け！という絶対の命令（Headless）
CMD ["java", "-Djava.awt.headless=true", "-jar", "/server.jar"]