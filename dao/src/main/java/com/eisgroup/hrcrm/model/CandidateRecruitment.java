package com.eisgroup.hrcrm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "Candidate_Recruitment")
public class CandidateRecruitment extends BaseObject {

    @Column
    private Long candidateId;

    @Column
    private Long recruitmentId;

    public CandidateRecruitment(long candidateId, long recruitmentId) {
        this.candidateId = candidateId;
        this.recruitmentId = recruitmentId;
    }

    public CandidateRecruitment(long id, long candidateId, long recruitmentId) {
        setId(id);
        this.candidateId = candidateId;
        this.recruitmentId = recruitmentId;
    }

    public CandidateRecruitment() {

    }

    //--------------Setters and Getters section--------------------

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        CandidateRecruitment that = (CandidateRecruitment) o;

        if (!candidateId.equals(that.candidateId))
            return false;
        return recruitmentId.equals(that.recruitmentId);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + candidateId.hashCode();
        result = 31 * result + recruitmentId.hashCode();
        return result;
    }
}
