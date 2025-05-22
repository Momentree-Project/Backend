package com.momentree.global.constant;

public enum FileType {
    PROFILE("profileFileNameStrategy"),
    POST("postFileNameStrategy");

    private final String strategyName;

    FileType(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getStrategyName() {
        return strategyName;
    }
}
