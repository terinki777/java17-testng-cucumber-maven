package ru.lanit.at.utils.allure;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomAllureCucumber implements ConcurrentEventListener {

    private final ThreadLocal<Map<String, Object>> storage = ThreadLocal.withInitial(HashMap::new);
    private final AllureLifecycle lifecycle = Allure.getLifecycle();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }

    private void handleTestCaseStarted(TestCaseStarted event) {
        storage.get().clear();
        storage.get().put("testCaseStarted", true);
    }

    private void handleTestStepStarted(TestStepStarted event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep)) {
            return;
        }

        PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
        String stepText = testStep.getStep().getText().trim();

        System.out.println("Step started: " + stepText);

        if (stepText.matches("^\\* шаг №\\d+$")) {
            // Это начало новой группы
            // ЗАКРЫВАЕМ предыдущую группу, если она существует
            closeCurrentGroup();

            // Создаем новую группу
            String groupId = UUID.randomUUID().toString();
            storage.get().put("currentGroupId", groupId);
            storage.get().put("isGroupStep", true);

            StepResult stepResult = new StepResult()
                    .setName(stepText.replace("* ", ""))
                    .setStatus(Status.PASSED);

            lifecycle.startStep(groupId, stepResult);
        } else if (storage.get().containsKey("currentGroupId")) {
            // Это вложенный шаг внутри текущей группы
            String parentId = (String) storage.get().get("currentGroupId");
            String stepId = UUID.randomUUID().toString();

            StepResult stepResult = new StepResult()
                    .setName(stepText)
                    .setStatus(Status.PASSED);

            lifecycle.startStep(parentId, stepId, stepResult);
            storage.get().put("currentStepId", stepId);
        } else {
            // Это обычный шаг (не в группе)
            String stepId = UUID.randomUUID().toString();
            storage.get().put("currentStepId", stepId);

            StepResult stepResult = new StepResult()
                    .setName(stepText)
                    .setStatus(Status.PASSED);

            lifecycle.startStep(stepId, stepResult);
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
        Map<String, Object> store = storage.get();

        if (store.containsKey("currentStepId")) {
            // Завершаем текущий шаг (вложенный или обычный)
            String stepId = (String) store.get("currentStepId");
            Status status = convertStatus(event.getResult().getStatus());
            lifecycle.updateStep(stepId, s -> s.setStatus(status));
            lifecycle.stopStep(stepId);
            store.remove("currentStepId");
        }

        // Если это был групповой шаг, помечаем что его нужно закрыть
        // Но не закрываем сразу - группа будет закрыта при начале новой группы или в конце теста
        if (store.containsKey("isGroupStep")) {
            store.remove("isGroupStep");
            // НЕ закрываем группу здесь! Только снимаем флаг
        }
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        Map<String, Object> store = storage.get();

        // Завершаем последнюю группу если она осталась открытой
        closeCurrentGroup();

        storage.get().clear();
        storage.remove();
    }

    /**
     * Закрывает текущую активную группу, если она существует
     */
    private void closeCurrentGroup() {
        Map<String, Object> store = storage.get();
        if (store.containsKey("currentGroupId")) {
            String groupId = (String) store.get("currentGroupId");
            Status status = Status.PASSED; // По умолчанию PASSED

            // Если есть результат теста, используем его статус
            // Но в этом методе мы не знаем о результате, так что используем PASSED

            lifecycle.updateStep(groupId, s -> s.setStatus(status));
            lifecycle.stopStep(groupId);
            store.remove("currentGroupId");
        }
    }

    private Status convertStatus(io.cucumber.plugin.event.Status cucumberStatus) {
        switch (cucumberStatus) {
            case PASSED:
                return Status.PASSED;
            case FAILED:
                return Status.FAILED;
            case SKIPPED:
                return Status.SKIPPED;
            case PENDING:
                return Status.SKIPPED;
            case UNDEFINED:
                return Status.BROKEN;
            case AMBIGUOUS:
                return Status.BROKEN;
            default:
                return Status.BROKEN;
        }
    }
}