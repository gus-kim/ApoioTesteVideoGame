package br.ufscar.dc.dsw.model;

public class DetailSession {
    private String projectName;
    private String strategyName;
    private String testerName;

    public DetailSession() {

    }

    public String getStrategyName() {
        return strategyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getTesterName() {
        return testerName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public void setTesterName(String testerName) {
        this.testerName = testerName;
    }
}
