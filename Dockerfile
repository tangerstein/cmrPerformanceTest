FROM java:8
MAINTAINER info.inspectit@novatec-gmbh.de
ENV INSPECTIT_CMR_ADDR localhost
ENV INSPECTIT_CMR_PORT 9070
ENV WORKER_SLEEP 500
ENV DEPTH 100
COPY gs-spring-boot-0.1.0.jar /gs-spring-boot-0.1.0.jar
COPY agent/inspectit-agent.jar /inspectit-agent.jar
COPY agent/logging-config.xml /logging-config.xml

CMD java -Xbootclasspath/p:/inspectit-agent.jar -javaagent:/inspectit-agent.jar  -Dinspectit.repository=$INSPECTIT_CMR_ADDR:$INSPECTIT_CMR_PORT -Dinspectit.agent.name=testApplication -DworkerSleep=$WORKER_SLEEP -Ddepth=$DEPTH -DnumberOfWorkers=$NUMBER_OF_WORKERS -jar /gs-spring-boot-0.1.0.jar
