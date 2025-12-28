package ru.lanit.at.steps.api;

import io.cucumber.java.ru.И;
import ru.lanit.at.steps.web.AbstractWebSteps;
import ru.lanit.at.utils.web.pagecontext.PageManager;

public class ExampleSteps extends AbstractWebSteps {

    public ExampleSteps(PageManager pageManager) {
        super(pageManager);
    }

    @И("шаг № {int} {string}")
    public void startNewStepGroup(int stepNumber, String stepTitle) {

    }

    @И("^\\* шаг № (\\d+)$")
    public void startStepGroup(int stepNumber) {
        // Этот шаг будет обработан кастомным адаптером
        // Не добавляем сюда логику, чтобы не создавать дубли
    }

    @И("установка URL")
    public void setupUrl() {

    }

    @И("отправка запроса")
    public void sendRequest() {

    }

    @И("чтение ответа")
    public void readResponse() {

    }

    @И("проверка данных")
    public void verifyData() {

    }
}
