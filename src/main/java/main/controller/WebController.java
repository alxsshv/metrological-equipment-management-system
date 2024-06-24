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

    // Вывод шаблонов для поверителей
    @GetMapping("/employee/form")
    public String getEmployeeForm(){
        return "employee/form";
    }
    @GetMapping("/employee")
    public String getEmployeeListView(){
        return "employee/list";
    }
    @GetMapping("/employee/{id}")
    public String getEmployeeView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "employee/card";
    }
    @GetMapping("/employee/form/{id}")
    public String getEditEmployeeForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "employee/edit";
    }

    //Вывод шаблонов для типов средств измерений
    @GetMapping("/mit/form")
    public String getMiTypeForm(){
        return "miType/form";
    }
    @GetMapping("/mit")
    public String getMiTypeListView(){
        return "miType/list";
    }
    @GetMapping("/mit/{id}")
    public String getMiTypeView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "miType/card";
    }
    @GetMapping("/mit/form/{id}")
    public String getEditMiTypeForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "miType/edit";
    }

    //Вывод шаблонов для средств измерений
    @GetMapping("/mi/form")
    public String getMeasurementInstrumentFormView(){
        return "measurementInstrument/form";
    }
    @GetMapping("/mi")
    public String getMeasurementInstrumentListView(){
        return "measurementInstrument/list";
    }
    @GetMapping("/mi/{id}")
    public String getMeasurementInstrumentView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "measurementInstrument/card";
    }
    @GetMapping("/mi/form/{id}")
    public String getEditMeasurementInstrumentForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "measurementInstrument/edit";
    }

    //Вывод шиблонов для СИ, применяемых в качестве эталонов
    @GetMapping("/standard/form")
    public String getStandardForm(){
        return "miStandard/form";
    }
    @GetMapping("/standard")
    public String getStandardListView(){
        return "miStandard/list";
    }
    @GetMapping("/standard/{id}")
    public String getStandardCard(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "miStandard/card";
    }
    @GetMapping("/standard/form/{id}")
    public String getStandardEditionForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "miStandard/edit";
    }

    //Вывод шаблонов для организаций
    @GetMapping("/organization/form")
    public String getOrganizationForm(){
        return "organization/form";
    }
    @GetMapping("/organization")
    public String getOrganizationListView(){
        return "organization/list";
    }
    @GetMapping("/organization/{id}")
    public String getOrganizationView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "organization/card";
    }
    @GetMapping("/organization/form/{id}")
    public String getOrganizationEditForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "organization/edit";
    }

    //Вывод шаблонов для отчетов в АРШИН и ФСА
    @GetMapping("/report/verification/form")
    public String getReportForm(){
        return "report/form";
    }
    @GetMapping("/report/verification")
    public String getVerificationReportListView(){
        return "report/list";
    }
    @GetMapping("/report/verification/{id}")
    public String getVerificationReportView(){
        return "report/card";
    }
    @GetMapping("/report/verification/form/{id}")
    public String getVerificationReportEditForm(){
        return "report/edit";
    }

    //Вывод шаблонов записей о поверке
    @GetMapping("/report/verification/record/form/{id}")
    public String getRecordEditForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",Long.parseLong(id));
        return "report/record/edit";
    }

    //Вывод шаблонов для настроек
    @GetMapping("/settings/form")
    public String getSettingsForm(){
        return "settings/form";
    }
}
