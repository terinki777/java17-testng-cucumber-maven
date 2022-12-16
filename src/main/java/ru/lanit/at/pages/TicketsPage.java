package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;

import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

@Name(value = "Страница все тикеты")
public class TicketsPage extends WebPage {

    @Name("Строка поиска")
    private SelenideElement searchField = $x("//*[@id='search_query']");

    @Name("кнопка Go")
    private SelenideElement searchQueryButton = $x("//button[@class='btn btn-primary']");

    @Name("Кнопка Save Query")
    private SelenideElement saveQueryButton = $x("//div[@id='headingTwo']//button");

    @Name("Query Name")
    private SelenideElement queryNameField = $x("//*[@id='id_title']");

    @Name("Кнопка SAVE QUERY")
    private SelenideElement saveQueryButton2 = $x("//input[@value='Save Query']");

    @Name("Query List")
    private SelenideElement queryList = $x("//*[@name='saved_query']");

    @Name("Кнопка Run Query")
    private SelenideElement runQueryButton = $x("//*[@value='Run Query']");

    @Name("Блок Save Query")
    private SelenideElement saveQueryExpand = $x("//*[@class='fas fa-save']");

    @Name("Use Saved Query")
    private SelenideElement useQueryExpand = $x("descendant-or-self::*[@class='btn btn-link collapsed btn-sm'][2]");

    @Name("Заголовок первого тикета")
    private SelenideElement ticketTitle = $x("//*[@class='tickettitle']");

    @Name("Сохраненный запрос")
    private SelenideElement savedQuery = $x("//input[@id='id_query']");
}

