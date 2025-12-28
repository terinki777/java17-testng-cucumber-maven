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
import java.util.Stack;

public class CustomAllureCucumber implements ConcurrentEventListener {

    private final ThreadLocal<Map<String, Object>> storage = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<Stack<String>> nestedStepsStack = ThreadLocal.withInitial(Stack::new);
    private final AllureLifecycle lifecycle = Allure.getLifecycle();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        // Регистрируем только события шагов, НЕ регистрируем TestCaseStarted/Finished
        // чтобы не конфликтовать со стандартным созданием тестов
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }

    private void handleTestStepStarted(TestStepStarted event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep)) {
            return;
        }

        PickleStepTestStep testStep = (PickleStepTestStep) event.getTestStep();
        String stepText = testStep.getStep().getText().trim();

        if (stepText.startsWith("шаг №")) {
            // Это начало новой группы
            // Закрываем предыдущую группу если она существует
            closeCurrentGroup();

            // Очищаем стек вложенных шагов предыдущей группы
            Stack<String> stack = nestedStepsStack.get();
            while (!stack.isEmpty()) {
                String stepId = stack.pop();
                lifecycle.updateStep(stepId, s -> s.setStatus(Status.PASSED));
                lifecycle.stopStep(stepId);
            }

            // Создаем новую группу
            String groupId = UUID.randomUUID().toString();
            storage.get().put("currentGroupId", groupId);

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

            // Сохраняем ID шага в стек для последующего завершения
            nestedStepsStack.get().push(stepId);
        } else {
            // Это обычный шаг (не в группе)
            String stepId = UUID.randomUUID().toString();

            StepResult stepResult = new StepResult()
                    .setName(stepText)
                    .setStatus(Status.PASSED);

            lifecycle.startStep(stepId, stepResult);
            nestedStepsStack.get().push(stepId);
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
        Stack<String> stack = nestedStepsStack.get();

        // Завершаем последний шаг в стеке (вложенный или обычный)
        if (!stack.isEmpty()) {
            String stepId = stack.pop();
            Status status = convertStatus(event.getResult().getStatus());
            lifecycle.updateStep(stepId, s -> s.setStatus(status));
            lifecycle.stopStep(stepId);
        }
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        // Завершаем последнюю группу если она осталась открытой
        closeCurrentGroup();

        // Завершаем все оставшиеся шаги в стеке
        Stack<String> stack = nestedStepsStack.get();
        while (!stack.isEmpty()) {
            String stepId = stack.pop();
            lifecycle.updateStep(stepId, s -> s.setStatus(Status.BROKEN));
            lifecycle.stopStep(stepId);
        }

        // Очищаем ThreadLocal
        storage.get().clear();
        storage.remove();
        nestedStepsStack.remove();
    }

    /**
     * Закрывает текущую активную группу, если она существует
     */
    private void closeCurrentGroup() {
        Map<String, Object> store = storage.get();
        if (store.containsKey("currentGroupId")) {
            String groupId = (String) store.get("currentGroupId");

            lifecycle.updateStep(groupId, s -> s.setStatus(Status.PASSED));
            lifecycle.stopStep(groupId);
            store.remove("currentGroupId");
        }
    }

    private Status convertStatus(io.cucumber.plugin.event.Status cucumberStatus) {
        return switch (cucumberStatus) {
            case PASSED -> Status.PASSED;
            case FAILED -> Status.FAILED;
            case SKIPPED -> Status.SKIPPED;
            case PENDING -> Status.SKIPPED;
            case UNDEFINED -> Status.BROKEN;
            case AMBIGUOUS -> Status.BROKEN;
            default -> Status.BROKEN;
        };
    }
}