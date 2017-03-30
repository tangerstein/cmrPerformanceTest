FROM java:8
MAINTAINER info.inspectit@novatec-gmbh.de
ENV INSPECTIT_CMR_ADDR localhost
ENV INSPECTIT_CMR_PORT 9070

COPY gs-spring-boot-0.1.0.jar /gs-spring-boot-0.1.0.jar
COPY agent/inspectit-agent.jar /inspectit-agent.jar
COPY agent/logging-config.xml /logging-config.xml

CMD java -Xbootclasspath/p:/inspectit-agent.jar -javaagent:/inspectit-agent.jar  -Dinspectit.repository=$INSPECTIT_CMR_ADDR:$INSPECTIT_CMR_PORT -Dinspectit.agent.name=testApplication -DCONTROLLER_HOST=$CONTROLLER_HOST -DCONTROLLER_PORT=$CONTROLLER_PORT -DINFLUX_PASSWORD=$INFLUX_PASSWORD -DINFLUX_PORT=$INFLUX_PORT -DINFLUX_URL=$INFLUX_URL -DINFLUX_USER=$INFLUX_USER -DINSPECTIT_CMR_ADDR=$INSPECTIT_CMR_ADDR -DINSPECTIT_CMR_PORT=$INSPECTIT_CMR_PORT -jar gs-spring-boot-0.1.0.jar
