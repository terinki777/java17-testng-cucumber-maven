#language:ru
@example3
@ExternalId=with_all_annotations
@DisplayName=Test_with_all_annotations
@WorkItemIds=3
@Title=Title_in_the_autotest_card
@Description=Test_with_all_annotations
@Labels=Tag1,Tag2
Функционал: Поиск гугл

  Сценарий: Открытие страницы google.com, ввод значения в поиск

    * шаг № "1"
    * открыть url "https://www.google.ru/"
    * инициализация страницы "Google"
    * ввести в поле "поле поиска" значение "Погода в Москве"

    * на странице отсутствует текст "погода в ижевске"

    * шаг № "2"
    * на странице имеется элемент "кнопка поиска"
    * кликнуть на элемент "кнопка поиска"
    * переход на страницу "Google страница результатов"