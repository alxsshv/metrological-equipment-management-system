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


}
