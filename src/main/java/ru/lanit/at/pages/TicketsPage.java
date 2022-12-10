package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Name(value = "TicketsPage")
public class TicketsPage extends WebPage {

    @Name("Тикет")
    private SelenideElement ticketLink = $x("//table[@id='ticketTable']//a");


    @Name("Save Query")
    private SelenideElement saveQueryButton = $x("//div[@id='headingTwo']//button");

    @Name("Query Name")
    private SelenideElement queryNameField = $(By.id("id_title"));

    @Name("SAVE QUERY")
    private SelenideElement saveQueryButton2 = $x("//input[@type='submit' and @value='Save Query']");
}

