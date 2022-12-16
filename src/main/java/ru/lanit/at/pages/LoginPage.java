package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;


@Name(value = "Страница авторизации")
public class LoginPage extends WebPage {

    @Name("Поле username")
    private SelenideElement usernameField = $x("//input[@id='username']");

    @Name("Поле password")
    private SelenideElement passwordField = $x("//input[@id='password']");

    @Name("Кнопка submit")
    private SelenideElement loginButton = $x("//input[@value='Login']");

}
