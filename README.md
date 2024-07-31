Этот `README.md` описывает основные аспекты проекта, включая запуск, порты и примеры запросов.

# Transfer Money API

## Описание

Transfer Money API позволяет осуществлять переводы между картами. Этот проект включает в себя REST-сервис и фронтенд-приложение.

## Запуск проекта

### Требования

- Docker
- Docker Compose

### Команда запуска

Для запуска проекта используйте следующую команду в терминале:

```bash
docker-compose up --build
```

Порты
REST-сервис: http://localhost:8080
Фронтенд: http://localhost:3000
Примеры запросов
1. Перевод денег с карты на карту
   Запрос:
````http
POST /transfer HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "cardFromNumber": "1234567890123456",
  "cardFromValidTill": "12/25",
  "cardFromCVV": "123",
  "cardToNumber": "6543210987654321",
  "amount": {
    "value": 1000,
    "currency": "USD"
  }
}
````
Ответ (успех):
````json
{
  "operationId": "abc123"
}
````

2. Подтверждение операции
   Запрос:
````http
POST /confirmOperation HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "operationId": "abc123",
  "code": "verification_code"
}
````




