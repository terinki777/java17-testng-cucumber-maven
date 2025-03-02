package ru.lanit.at.pages;


import com.codeborne.selenide.SelenideElement;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Name(value = "Страница просмотра тикета")
public class TicketPage extends WebPage {

    @Name("Кнопка EDIT")
    private SelenideElement editButton = $x("//a[@class='ticket-edit']");

    @Name("Кнопка EDIT_DETAILS")
    private SelenideElement editDetailsButton = $x("//a[@href='#FurtherEditOptions']/button");

    @Name("Кнопка ATTACH_FILE")
    private SelenideElement attachFileButton = $x("//button[@id='ShowFileUpload']");


    @Name("Кнопка BROWSE")
    private SelenideElement browseButton = $x("//label[@class='btn btn-primary btn-sm btn-file']");


    @Name("Кнопка UPDATE_THIS_TICKET")
    private SelenideElement updateThisTicketButton = $x("//button[@type='submit']");

    @Name("Поле ввода File")
    private SelenideElement fileInput = $("input[type='file']");


}

