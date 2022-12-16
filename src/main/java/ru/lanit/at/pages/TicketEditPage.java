package ru.lanit.at.pages;


import com.codeborne.selenide.SelenideElement;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;

@Name(value = "Страница редактирования тикета")
public class TicketEditPage extends WebPage {

    @Name("Поле описание")
    private SelenideElement descriptionField = $x("//textarea[@id='id_description']");

    @Name("Кнопка Сохранить")
    private SelenideElement saveButton = $x("//input[@type='submit']");

    @Name("Title")
    private SelenideElement titleField = $x("//*[@name='title']");

}

