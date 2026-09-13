# PDD Application (Fullstack) 🚗💨

Приложение для подготовки и прохождения тестов ПДД. Проект включает серверную часть на Spring Boot с встроенной базой данных H2 и кроссплатформенный клиент на Flutter.

---

## 🛠 Технологический стек

* **Backend:** Java 17+, Spring Boot 3.3.3, Spring Data JPA, Spring Security, H2 Database (в памяти), Maven Wrapper
* **Frontend:** Flutter (Dart), поддержка Android, iOS, Windows Desktop и Web (Chrome/Edge)

---

## 🚀 Быстрый запуск

### 1. Предварительные требования

Перед началом работы убедитесь, что у вас установлены:
* **JDK 17** или новее
* **Flutter SDK**
* **Режим разработчика в Windows** (требуется для сборки Flutter):
  ```powershell
  start ms-settings:developers

### запуск сервера:
1) cd pdd_server
2) .\mvnw.cmd spring-boot:run

### запуск клиента:
1) flutter pub get
2) flutter run -d chrome

### Создание пользователя (POST):
Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method Post -ContentType "application/json" -Body '{"name":"Пользователь","iin":"123456789012","phone":"+77001112233","passwordHash":"secret"}'

### Получение списка пользователей (GET):
Invoke-RestMethod -Uri "http://localhost:8080/api/users" -Method Get