FROM java:8
MAINTAINER info.inspectit@novatec-gmbh.de
ENV INSPECTIT_CMR_ADDR localhost
ENV INSPECTIT_CMR_PORT 9070

COPY gs-spring-boot-0.1.0.jar /gs-spring-boot-0.1.0.jar
COPY agent/inspectit-agent.jar /inspectit-agent.jar
COPY agent/logging-config.xml /logging-config.xml

CMD java -Xbootclasspath/p:/inspectit-agent.jar -javaagent:/inspectit-agent.jar  -Dinspectit.repository=$INSPECTIT_CMR_ADDR:$INSPECTIT_CMR_PORT -Dinspectit.agent.name=testApplication -DPROPERTY_FILEPATH=$PROPERTY_FILEPATH -jar gs-spring-boot-0.1.0.jar
