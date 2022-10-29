FROM adoptopenjdk/openjdk11:alpine

RUN mkdir -p /home/serviceTodolist

COPY . /home/serviceTodolist

WORKDIR /home/serviceTodolist

EXPOSE 8400

CMD ["./gradlew", "bootRun"]