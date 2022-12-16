#language:ru

@helpdesk
Функционал: Проверка сервиса Helpdesk

  Сценарий: Редактирование созданного тикета (с прикреплением файла в созданный тикет)

# создание тикета
    Дано открыть url "https://at-sandbox.workbench.lanit.ru/"
    Когда инициализация страницы "Страница Helpdesk"
    Затем кликнуть на элемент "Кнопка New Ticket"
    И переход на страницу "Страница создания тикета"
    И ввести в поле "Title" значение "тест555555555555"
    И выбрать элемент "Django Helpdesk" в выпадающем списке "Queue"
    И ввести в поле "Email" значение "test@mail.ru"
    Затем кликнуть на элемент "Кнопка Submit Ticket"



#   прикрепление файла к тикету
    Затем открыть url "https://at-sandbox.workbench.lanit.ru/"
    Когда инициализация страницы "Страница Helpdesk"
    И кликнуть на элемент "Кнопка Log In"
    Затем переход на страницу "Страница авторизации"
    Тогда ввести в поле "Поле username" значение "admin"
    И ввести в поле "Поле password" значение "adminat"
    И кликнуть на элемент "Кнопка submit"
    Затем переход на страницу "Страница все тикеты"
    Тогда ввести в поле "Строка поиска" значение "тест555555555555"
    И кликнуть на элемент "кнопка Go"
    И кликнуть на элемент "Заголовок первого тикета"
    Затем переход на страницу "Страница просмотра тикета"
    Тогда кликнуть на элемент "Кнопка ATTACH_FILE"
    И ввести в поле "Поле ввода File" ссылку на файл "src\main\resources\test.txt"
    И на странице присутствует текст "test.txt"
    И на странице присутствует текст "test@mail.ru"
    И кликнуть на элемент "Кнопка UPDATE_THIS_TICKET"