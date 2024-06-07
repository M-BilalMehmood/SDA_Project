package buisness.services;

import buisness.models.*;
import datalayer.repositories.CaseRepository;

import java.util.List;

public class CaseService {
    private CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public Case createCase(Incident incident, CaseOfficer caseOfficer, Citizen witness, Investigation investigation) {
        Case crimeCase = new Case(incident, caseOfficer, witness, investigation);
        caseRepository.save(crimeCase);
        return crimeCase;
    }

    public void updateCase(Case crimeCase) {
        caseRepository.update(crimeCase);
    }

    public Case findCaseById(int caseId) {
        return caseRepository.findById(caseId);
    }

    public List<Case> findAllCases() {
        return caseRepository.findAll();
    }

    public void closeCase(int caseId, String finalRemarks) {
        Case crimeCase = caseRepository.findById(caseId);
        if (crimeCase != null) {
            crimeCase.setStatus(CaseStatus.CLOSED);
            crimeCase.setFinalRemarks(finalRemarks);
            caseRepository.update(crimeCase);
        }
    }

}