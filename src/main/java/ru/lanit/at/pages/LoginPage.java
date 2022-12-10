package ru.lanit.at.pages;

import com.codeborne.selenide.SelenideElement;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$x;


@Name(value = "LoginPage")
public class LoginPage extends WebPage {

    @Name("username")
    private SelenideElement usernameField = $x("//input[@id='username']");

    @Name("password")
    private SelenideElement passwordField = $x("//input[@id='password']");

    @Name("submit")
    private SelenideElement loginButton = $x("//input[@value='Login']");

}
