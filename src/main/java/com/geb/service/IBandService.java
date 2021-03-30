package com.geb.service;

import java.util.List;

import com.geb.model.dto.BandDTO;
import com.geb.model.dto.MembersDTO;

public interface IBandService {

    void create(BandDTO band);

    void Update(BandDTO band);

    void delete(Long code);
    
    BandDTO find (Long code);
    
    void associateMembers(Long codeBand, String emailMember, Boolean leader);
    
    void disassociateMembers(Long codeBand, String emailMember);
    
    List<MembersDTO> findMembers(Long codeBand);
    
    List<BandDTO> findAssociatedBandsByUser(String emailMember);
    
}
