package cc.cryptek.tappismeetodid.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmartBpmnService {

    private final RuntimeService runtimeService;

    /**
     * Starts a BPMN process by its process definition key (name)
     * @param processKey The process definition key as defined in the BPMN file
     * @return Process instance ID
     */
    @Transactional
    public String startProcess(String processKey) {
        return startProcess(processKey, Collections.emptyMap());
    }

    /**
     * Starts a BPMN process with variables
     * @param processKey The process definition key as defined in the BPMN file
     * @param variables Map of process variables
     * @return Process instance ID
     */
    @Transactional
    public String startProcess(String processKey, Map<String, Object> variables) {
        log.info("Starting process {} with variables: {}", processKey, variables);
        ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey(processKey)
                .variables(variables)
                .start();
        log.info("Started process instance: {}", processInstance.getId());
        return processInstance.getId();
    }

    /**
     * Sets variables for an existing process instance
     * @param processInstanceId The ID of the process instance
     * @param variables Map of variables to set
     */
    @Transactional
    public void setVariables(String processInstanceId, Map<String, Object> variables) {
        log.info("Setting variables for process instance {}: {}", processInstanceId, variables);
        runtimeService.setVariables(processInstanceId, variables);
    }

    /**
     * Gets a variable from a process instance
     * @param processInstanceId The ID of the process instance
     * @param variableName The name of the variable to retrieve
     * @return Optional containing the variable value if it exists
     */
    @Transactional(readOnly = true)
    public Optional<Object> getVariable(String processInstanceId, String variableName) {
        return Optional.ofNullable(runtimeService.getVariable(processInstanceId, variableName));
    }

    /**
     * Gets all variables for a process instance
     * @param processInstanceId The ID of the process instance
     * @return Map of all process variables
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getVariables(String processInstanceId) {
        return runtimeService.getVariables(processInstanceId);
    }

    /**
     * Checks if a process instance is active
     * @param processInstanceId The ID of the process instance
     * @return true if the process instance exists and is active
     */
    @Transactional(readOnly = true)
    public boolean isProcessActive(String processInstanceId) {
        return runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .active()
                .singleResult() != null;
    }

    /**
     * Terminates a process instance
     * @param processInstanceId The ID of the process instance
     * @param reason The reason for termination (optional)
     */
    @Transactional
    public void terminateProcess(String processInstanceId, String reason) {
        log.info("Terminating process instance {} with reason: {}", processInstanceId, reason);
        runtimeService.deleteProcessInstance(processInstanceId, reason);
    }

    /**
     * Suspends a process instance
     * @param processInstanceId The ID of the process instance
     */
    @Transactional
    public void suspendProcess(String processInstanceId) {
        log.info("Suspending process instance {}", processInstanceId);
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    /**
     * Activates a suspended process instance
     * @param processInstanceId The ID of the process instance
     */
    @Transactional
    public void activateProcess(String processInstanceId) {
        log.info("Activating process instance {}", processInstanceId);
        runtimeService.activateProcessInstanceById(processInstanceId);
    }
}
