package dto.request.client.requests;


import dto.DTO;

public class TerminationInfoDTO implements DTO {
    private final String termName;
    private final String terminationTerm;

    public TerminationInfoDTO(String termName, String terminationTerm) {
        this.termName = termName;
        this.terminationTerm = terminationTerm;
    }

    public String getTermName() {
        return termName;
    }

    public String getTerminationTerm() {
        return terminationTerm;
    }

}
