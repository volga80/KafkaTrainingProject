# KafkaTrainingProject
Репозиторий содержит в себе два проекта: MetricsProducer and MetricsConsumer, которые обмениваются данными через Apache Kafka.
MetricsProducer собирает данные о работе своего приложения и отправляет их в Kafka topic. 
MetricsConsumer получает данные и сохраняет их в базе данных для последующего анализа.

## Содержание
- [Технологии](#технологии)
- [Тестирование](#тестирование)
  
## Технологии
- Spring Boot
- Apache Kafka
- Hibernete
- Postgres
- JUnit Testing
- Springdoc

## Использование
Для запуска данных проектов Вам необходимо запустить Zookeeper и Kafka, если вы используете Docker, то создайте в каждом проекте
файл docker-compose.yaml и создайте в нем свои настройки для запуска, при этом измените файлы application.yaml в каждом их проектов.
Запускать проекты необходимо независимо друг от друга.
Для MetricsConsumer создайте базу данных в Postgres и измените настройки для подключения к базе.
Всю необходимую документацию по методам контроллера Вы можете получить на localhost:8180/swagger-ui.html и localhost:8280/swagger-ui.html после запуска приложения.
Приложение использовалось с помощью Postman: localhost:8180/metrics - сбор и отправка данных в Kafka, localhost:8280/metrics - получение всех метрик из Postgres.

## Тестирование
В проектах использовано JUnit тестирование для основных контроллеров и сервисов.

## Дополнительно
Проекты не являются окончательной версией, принимаются предложения по доработке и устренению недочетов
