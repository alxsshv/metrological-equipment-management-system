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

    //Шаблоны для пользователей
    @GetMapping("/user")
    public String getUsersListView(){
        return "users/list";
    }
    @GetMapping("/user/{id}")
    public String getUserView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "users/card";
    }
    @GetMapping("/user/form")
    public String getUserForm(){
        return "users/form";
    }
    @GetMapping("/user/form/{id}")
    public String getEditUserView(@RequestParam("id") String id, Model model){
        model.addAttribute("id",id);
        return "users/edit";
    }

    @GetMapping("/user/wait")
    public String getWaitingCheckUsersList(){
        return "users/wait_list";
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
    @GetMapping("/mi/protocol/{id}")
    public String getMeasurementInstrumentProtocolsList(@RequestParam("id") String id, Model model){
        model.addAttribute("id", id);
        return "measurementInstrument/protocols";
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
    @GetMapping("/report/verification/ready/arshin")
    public String getVerificationListForArshinPublication(){
        return "report/arshin/listForArshin";
    }
    @GetMapping("/report/verification/ready/fsa")
    public String getVerificationListForFSAPublication(){
        return "report/fsa/listForFsa";
    }
    @GetMapping("/report/file/fsa")
    public String getReportFilesListForFSA(){
        return "report/fsa/filelist";
    }
    @GetMapping("/report/file/arshin")
    public String getReportFilesListForArshin(){
        return "report/arshin/filelist";
    }


    //Вывод шаблонов записей о поверке
    @GetMapping("/report/verification/record/form/{id}")
    public String getRecordEditForm(@RequestParam("id") String id, Model model){
        model.addAttribute("id",Long.parseLong(id));
        return "report/record/edit";
    }

    @GetMapping("/report/verification/record/counter/date/employee")
    public String getAmountVerificationForEveryDateByEmployeeView(@RequestParam("employeeId") String employeeId, Model model){
        model.addAttribute("employeeId",Long.parseLong(employeeId));
        return "report/record/reportByEmployee";
    }


    //Вывод шаблонов для администрирования
    @GetMapping("/settings")
    public String getSystemAdminView(){
        return "settings/system_admin";
    }
    @GetMapping("/settings/entity/form")
    public String getSettingsForm(){
        return "settings/entity/form";
    }

    //Вывод шаблонов для выполнения операций поверки
    @GetMapping("/verification")
    public String getVerifcationView(){
        return "verification/verification";
    }

    //Вывод шаблонов для добавления и удаления видов измерений
    @GetMapping("meas-categories/form")
    public String getMeasCategoryForm(){
        return "measCategory/form";
    }

    //Регистрация
    @GetMapping("/registration")
    public String registration() {
        return "security/registration";
    }
    @GetMapping("/login")
    public String login() {
        return "security/login";
    }

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "security/accessDenied";
    }

    //Журналы поверки
    @GetMapping("/journal/verification")
    public String getJournalsListView(){
        return "journal/verification/list";
  }

    @GetMapping("/journal/verification/form")
    public String getJournalForm(){
        return "journal/verification/form";
    }

    @GetMapping("/journal/verification/{id}")
    public String getJournalCard() {
        return "journal/verification/card";
    }

    @GetMapping("/journal/verification/add/{id}")
    public String getProtocolAddingForm(@RequestParam("id") long id, Model model){
        model.addAttribute("id",id);
        return "journal/verification/protocol/form";
    }

    @GetMapping("/file_not_found")
    public String getFileNotFoundView(){
        return "fileNotFound";
    }

}
