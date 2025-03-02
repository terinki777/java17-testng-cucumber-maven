package ru.lanit.at.pages;


import com.codeborne.selenide.SelenideElement;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

@Name(value = "Страница создания тикета")
public class CreateTicketPage extends WebPage {

    @Name("Title")
    private SelenideElement titleField = $x("//input[@name=\"title\"]");

    @Name("Queue")
    private SelenideElement queueButton = $x("//select[@name=\"queue\"]");

    @Name("Выбор значения в выпадающем списке")
    private SelenideElement optionButton = $x("//select[@name=\"queue\"]//option[@value=\"1\"]");

    @Name("Кнопка Submit Ticket")
    private SelenideElement submitTicketButton = $x("//button[@class=\"btn btn-primary btn-lg btn-block\"]");

    @Name("Email")
    private SelenideElement emailField = $x("//input[@type=\"email\"]");
}
