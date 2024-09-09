package main.service.interfaces;

import main.model.VerificationReport;

import java.util.List;

public interface VerificationReportStatusService {
    void setSentToArshinStatus(VerificationReport report);
    void setPublicToArshinStatus(VerificationReport report);
    void setSentToFsaStatus(VerificationReport report);
    void setSentToArshinStatusForReports(List<VerificationReport> readyToSendReports);
    void setSentToFsaStatusForReports(List<VerificationReport> reports);

}
