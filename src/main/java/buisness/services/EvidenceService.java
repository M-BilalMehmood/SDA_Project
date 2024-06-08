package buisness.services;

import buisness.models.Incident;
import buisness.models.Evidence;
import datalayer.repositories.EvidenceRepository;

// EvidenceService.java
public class EvidenceService {
    private EvidenceRepository evidenceRepository;


    public EvidenceService(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    public Evidence uploadEvidence(String description, String filePath, Incident incident) {
        Evidence evidence = new Evidence(description, filePath, incident);
        evidenceRepository.save(evidence);
        return evidence;
    }

    // ... other methods for retrieving, updating, deleting evidence
}