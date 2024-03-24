package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @GetMapping("/")
    public String getFirstView(){
        return "index";
    }

    // Работа с поверителями
    @GetMapping("/employees/form")
    public String getEmployeeFormView(){
        return "employee/employeeForm";
    }
    @GetMapping("/employees")
    public String getEmployeeListView(){
        return "employee/employeeList";
    }
    @GetMapping("/employees/{id}")
    public String getEmployeeView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "employee/employeeCard";
    }
    @GetMapping("/employees/form/{id}")
    public String getEditEmployeeForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "employee/employeeEdit";
    }

    //Вывод шаблонов для типов средств измерений
    @GetMapping("/mits/form")
    public String getMiTypeFormView(){
        return "miType/miTypeForm";
    }
    @GetMapping("/mits")
    public String getMiTypeListView(){
        return "miType/miTypeList";
    }
    @GetMapping("/mits/{id}")
    public String getMiTypeView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "miType/miTypeCard";
    }
    @GetMapping("/mits/form/{id}")
    public String getEditMiTypeForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "miType/miTypeEdit";
    }

    //Вывод шаблонов для средств измерений
    @GetMapping("/mis/form")
    public String getMeasurementInstrumentFormView(){
        return "measurementInstrument/form";
    }
    @GetMapping("/mis")
    public String getMeasurementInstrumentListView(){
        return "measurementInstrument/list";
    }
    @GetMapping("/mis/{id}")
    public String getMeasurementInstrumentView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "measurementInstrument/card";
    }
    @GetMapping("/mis/form/{id}")
    public String getEditMeasurementInstrumentForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "measurementInstrument/edit";
    }

    //Вывод шиблонов для организаций
    @GetMapping("/organizations/form")
    public String getOrganizationFormView(){
        return "organization/form";
    }
    @GetMapping("/organizations")
    public String getOrganizationListView(){
        return "organization/list";
    }
    @GetMapping("/organizations/{id}")
    public String getOrganizationView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "organization/card";
    }
    @GetMapping("/organizations/form/{id}")
    public String getOrganizationEditForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "organization/edit";
    }

    // Вывод шаблонов для заявок на регистрацию поверки
    @GetMapping("/issues/form")
    public String getAddVerificationIssueView(){
        return "verificationIssue/verificationIssueForm";
    }
    @GetMapping("/issues")
    public String getVerificationIssuesListView(){
        return "verificationIssue/verificationIssueList";
    }
    @GetMapping("/issues/{id}")
    public String getVerificationIssueView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "verificationIssue/verificationIssueCard";
    }
    @GetMapping("/issues/record/{id}")
    public String getEditVerificationRecordView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "verificationIssue/verificationRecordEdit";
    }

    //Работа с отчетами о поверке
    // Работа с заявками на регистрацию поверки
    @GetMapping("/reports/form")
    public String getVerificationReportForm(){
        return "verification/verificationReportForm";
    }
    @GetMapping("/reports")
    public String getReportListView(){
        return "verification/verificationReportList";
    }
    @GetMapping("/reports/{id}")
    public String getVerificationReportView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "verification/verificationReportCard";
    }
    @GetMapping("/reports/record/{id}")
    public String getVerificationRecordEditor(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "verification/recordUpdateForm";
    }

}
