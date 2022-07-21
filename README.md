# QCloud
****
## Проект по удалённому доступу к ПК

## Сервер

### Актуальная версия: 4.1.0.4

## Клиент

### Актуальная версия: 4.0.9

### Road Map
- [ ] Новый интерфейс
- [ ] Детализация ошибок
> Новый способ аутентификации клиента
- [x] Добавлена система языковых пакетов (пока только Русский)
- [x] Глобальная перестройка JavaFX
- [x] Добавлено сепарирование Операционных систем
- [x] Новый протокол общения с сервером
- [x] Нативные приложения под каждую платформу
- [x] Обновлены зависимости

## Карта проекта
```mermaid
graph LR
QCloud(QCloud) -- Server souce code --> Server(QuasarCloudServer)
QCloud -- Launcher souce code--> Launcher(QuasarCloud)
```
