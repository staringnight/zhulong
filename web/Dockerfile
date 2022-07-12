FROM harbor.dazhi100.com/docker-hub/library/openjdk:17.0.2-oracle

ARG JAR_FILE

VOLUME /dzyx/ /dzyx/

WORKDIR /dzyx

COPY target/${JAR_FILE}  /dzyx/service/

ENV JAVA_OPTS "-server -Xmx2g -Xms512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/dzyx/dump/gateway/"

ENV JAR_FILE_DIR service/${JAR_FILE}

EXPOSE 18010

RUN bash -c 'touch /dzyx/service/${JAR_FILE}'

ENTRYPOINT java ${JAVA_OPTS} --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/sun.net.util=ALL-UNNAMED --add-opens java.base/java.math=ALL-UNNAMED --enable-preview -jar ${JAR_FILE_DIR}



