package ru.lanit.at.steps.web;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lanit.at.utils.ContextHolder;
import ru.lanit.at.utils.web.pagecontext.Environment;
import ru.lanit.at.utils.web.pagecontext.PageManager;
import ru.lanit.at.utils.web.pagecontext.WebPage;
import ru.lanit.at.utils.web.properties.Configurations;

import java.util.Map;
import java.util.Stack;
import java.util.UUID;


public abstract class AbstractWebSteps {
    protected final Configurations configurations;
    protected Logger LOGGER = LogManager.getLogger(this.getClass());
    protected PageManager pageManager;

    public ThreadLocal<Stack<String>> stepGroups =
            ThreadLocal.withInitial(Stack::new);
    public AllureLifecycle lifecycle = Allure.getLifecycle();
    public ThreadLocal<String> currentParentStepId = new ThreadLocal<>();

    public AbstractWebSteps(PageManager pageManager) {
        this.pageManager = pageManager;
        configurations = ConfigFactory.create(Configurations.class, System.getProperties(),
                System.getenv());
    }

    protected Map<String, Object> getStorage() {
        return ContextHolder.asMap();
    }

    protected void saveValueInStorage(String key, Object value) {
        getStorage().put(key, value);
    }

    protected WebPage getPage(String name) {
        WebPage page = Environment.getPage(name);
        pageManager.setCurrentPage(page);
        return page;
    }

    protected <T extends WebPage> T getPage(Class<T> c) {
        WebPage page = Environment.getPage(c);
        pageManager.setCurrentPage(page);
        return (T) page;
    }

    protected void executeNestedStep(String stepName, Runnable action) {
        String parentId = currentParentStepId.get();

        if (parentId != null) {
            // Создаем вложенный шаг внутри родительского
            String nestedStepId = UUID.randomUUID().toString();
            StepResult nestedStep = new StepResult()
                    .setName(stepName)
                    .setStatus(Status.PASSED);

            lifecycle.startStep(parentId, nestedStepId, nestedStep);

            try {
                action.run();
                lifecycle.updateStep(nestedStepId, s -> s.setStatus(Status.PASSED));
            } catch (Throwable e) {
                lifecycle.updateStep(nestedStepId, s -> s.setStatus(Status.FAILED));
                throw e;
            } finally {
                lifecycle.stopStep(nestedStepId);
            }
        } else {
            action.run();
        }
    }
}
