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
    private static MiStatus defaultMiStatus;


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

    public static void setDefaultMiStatus(MiStatus miStatus) {
        AppDefaults.defaultMiStatus = miStatus;
    }
}
