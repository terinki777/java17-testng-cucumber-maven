package ru.lanit.at.steps.api;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.ru.И;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import ru.lanit.at.steps.web.AbstractWebSteps;
import ru.lanit.at.utils.web.pagecontext.PageManager;

import java.util.UUID;

public class ExampleSteps extends AbstractWebSteps {

    public ExampleSteps(PageManager pageManager) {
        super(pageManager);
    }

    @И("шаг № {int} {string}")
    public void startNewStepGroup(int stepNumber, String stepTitle) {
        String parentStepId = UUID.randomUUID().toString();
        currentParentStepId.set(parentStepId);

        // Создаем родительский шаг, но НЕ завершаем его
        StepResult stepResult = new StepResult()
                .setName("Шаг №" + stepNumber + " " + stepTitle)
                .setStatus(Status.PASSED);

        lifecycle.startStep(parentStepId, stepResult);
    }

    @И("установка URL")
    public void setupUrl() {
        executeNestedStep("Установка URL", () -> {
            // Логика установки URL
        });
    }

    @И("отправка запроса")
    public void sendRequest() {
        executeNestedStep("Отправка запроса", () -> {
            // Логика отправки запроса
        });
    }

    @И("чтение ответа")
    public void readResponse() {
        executeNestedStep("Чтение ответа", () -> {
            // Логика чтения ответа
        });
    }

    @И("проверка данных")
    public void verifyData() {
        executeNestedStep("Проверка данных", () -> {
            // Логика проверки данных
        });
    }

    @After
    public void cleanupStepGroups(Scenario scenario) {
        // Очищаем стек после сценария
        if (stepGroups.get() != null) {
            stepGroups.get().clear();
        }
    }
}
