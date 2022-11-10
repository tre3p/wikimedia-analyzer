# Wikimedia Analyzer

![alt text](https://i.imgur.com/oU8lisK.png)

## Idea

The main idea of this project was to learn how to work with Grafana, Prometheus and custom metrics from the Spring Boot application. Wikimedia was chosen because I wanted to work with some real data, see different graphs etc.

## How this works:

There are two modules: a producer and a consumer. Producer reads the data from the stream, and then transfers the data to Kafka. After that, consumer sequentially reads this data, performs some analysis, and then sends the metrics to Prometheus, and from there the data goes to Grafana. That's how I did it, but if you want to play with this project, feel free to make your own implementation. You can do a fork, and I will definitely look at your implementation. With the code you have now in the application, you can build some graphs:

![alt text](https://i.imgur.com/HTirPCa.png)

## How to launch:

There is no Docker on producer and consumer right now, since I was running everything in the IDE. But there are 2 docker-compose that can help you:
1) docker-compose-metrics.yml - contains Prometheus and Grafana.
2) docker-compose-kafka-yml - contains Zookeeper and Kafka.

If you run the application together with docker-compose for Kafka, everything should work out of the box.

