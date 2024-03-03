package main.controller;


import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;


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


    // Работа с заявками на регистрацию поверки
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
