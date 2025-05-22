package br.ufscar.dc.dsw.model;

import java.time.LocalDateTime;

public class Session {

    private Long id;
    private Long projectId;
    private Long testerId;
    private Long strategyId;
    private int minutesDuration;
    private String description;
    private SessionStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public enum SessionStatus {
        CREATED,
        IN_PROGRESS,
        FINISHED
    }

    // Construtores
    public Session() {
        this.status = SessionStatus.CREATED;
        this.creationDate = LocalDateTime.now();
    }

    public Session(Long projectId, Long testerId, Long strategyId, int minutesDuration) {
        this();
        this.projectId = projectId;
        this.testerId = testerId;
        this.strategyId = strategyId;
        this.minutesDuration = minutesDuration;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTesterId() {
        return testerId;
    }

    public void setTesterId(Long testerId) {
        this.testerId = testerId;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public int getMinutesDuration() {
        return minutesDuration;
    }

    public void setMinutesDuration(int minutesDuration) {
        this.minutesDuration = minutesDuration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", testerId=" + testerId +
                ", strategyId=" + strategyId +
                ", minutesDuration=" + minutesDuration +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
