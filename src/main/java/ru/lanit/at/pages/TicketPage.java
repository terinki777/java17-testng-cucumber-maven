package ru.lanit.at.pages;


import com.codeborne.selenide.SelenideElement;
import ru.lanit.at.utils.web.annotations.Name;
import ru.lanit.at.utils.web.pagecontext.WebPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Name(value = "TicketPage")
public class TicketPage extends WebPage {

    @Name("EDIT")
    private SelenideElement editButton = $x("//a[@class='ticket-edit']");

    @Name("EDIT_DETAILS")
    private SelenideElement editDetailsButton = $x("//a[@href='#FurtherEditOptions']/button");

    @Name("ATTACH_FILE")
    private SelenideElement attachFileButton = $x("//button[@id='ShowFileUpload']");

    //    @Name("BROWSE")
//    private SelenideElement browseButton = $x("//input[@name='attachment']");
    @Name("BROWSE")
    private SelenideElement browseButton = $x("//label[@class='btn btn-primary btn-sm btn-file']");


    @Name("UPDATE_THIS_TICKET")
    private SelenideElement updateThisTicketButton = $x("//button[@type='submit']");

//    @Name("File")
//    private SelenideElement fileInput = $x("//span[@id='selectedfilename0']");

//    @Name("File")
//    private SelenideElement fileInput = $x("//input[@type='file']");

    @Name("File")
    private SelenideElement fileInput = $("input[type='file']");


}

