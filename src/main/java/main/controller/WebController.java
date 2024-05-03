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
    @GetMapping("/employees/form")
    public String getEmployeeFormView(){
        return "employee/form";
    }
    @GetMapping("/employees")
    public String getEmployeeListView(){
        return "employee/list";
    }
    @GetMapping("/employees/{id}")
    public String getEmployeeView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "employee/card";
    }
    @GetMapping("/employees/form/{id}")
    public String getEditEmployeeForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "employee/edit";
    }

    //Вывод шаблонов для типов средств измерений
    @GetMapping("/mits/form")
    public String getMiTypeFormView(){
        return "miType/form";
    }
    @GetMapping("/mits")
    public String getMiTypeListView(){
        return "miType/list";
    }
    @GetMapping("/mits/{id}")
    public String getMiTypeView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "miType/card";
    }
    @GetMapping("/mits/form/{id}")
    public String getEditMiTypeForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "miType/edit";
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


}
