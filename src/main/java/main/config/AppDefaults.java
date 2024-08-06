package main.config;

import lombok.Getter;
import main.model.*;

public class AppDefaults {
    @Getter
    private static Department defaultDepartment;
    @Getter
    private static MiCondition defaultMiCondition;
    @Getter
    private static Organization defaultOrganization;
    @Getter
    private static MeasCategory defaultMeasCategory;
    @Getter
    private static MiStatus miStatusWorkingTool;
    @Getter
    private static MiStatus miStatusStandard;
    @Getter
    private static MiStatus miStatusIndicator;


    public static void setDefaultMiCondition(MiCondition defaultMiCondition) {
        AppDefaults.defaultMiCondition = defaultMiCondition;
    }

    public static void setDefaultOrganization(Organization organization) {
        AppDefaults.defaultOrganization = organization;
    }

    public static void setDefaultDepartment(Department department) {
        AppDefaults.defaultDepartment = department;
    }

    public static void setDefaultMeasCategory(MeasCategory measCategory) {
        AppDefaults.defaultMeasCategory = measCategory;
    }

    public static void setWorkingToolMiStatus(MiStatus miStatus) {
        AppDefaults.miStatusWorkingTool = miStatus;
    }

    public static void setStandardMiStatus(MiStatus miStatus) {
        AppDefaults.miStatusStandard = miStatus;
    }

    public static void setIndicatorMiStatus(MiStatus miStatus) {
        AppDefaults.miStatusIndicator = miStatus;
    }
}
