1) Данное приложение является RESTFUL приложением

2) Используемый стек технологий:
    * Java17
    * SpringBoot
    * PostgresSQL
    * Flyway
    * Lombok
    * Junit5
    * Git

3) Инструкция по запуску:
    * выполнить docker-compose up -d для запуска контейнера с базой данных
    * запустить springBoot приложение (TestTaskClevertecApplication)

4) Руководство по использованию:
    1. Работа с приложением осуществляется с помощью двух эндпоинтов:
        * GET localhost:8080/receipt/generate (параметры: item, card_number)
          пример запроса: localhost:8080/receipt/generate?item=1-5&item=2-10&card_number=card-5678
        * POST localhost:8080/receipt/generate (принимает .txt файл)
          пример файла находится в папке: ./example

    2. В случае передачи некорректных данных пробрасывается BusinessException
    3. При выполнении запроса возвращается JSON с чеком и создаётся .txt файл с чеком в папке ./receipt