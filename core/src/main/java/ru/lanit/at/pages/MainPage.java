package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

@Name(value = "Страница Helpdesk")
public class MainPage extends WebPage {

    @Name("поле поиска")
    private SelenideElement searchField = $x("//input[@id='search_query']");

    @Name("кнопка Go")
    private SelenideElement searchButton = $x("//button/i[contains(@class,'fa-search')]");

    @Name("Кнопка Log In")
    private SelenideElement loginButton = $x("//a[@href='/login/?next=/']");

    @Name("Кнопка New Ticket")
    private SelenideElement newTicketButton = $x("//a[@href=\"/tickets/submit/\"]");




}

