package com.geb.service;

import com.geb.model.dto.BandDTO;

public interface IBandService {

    void create(BandDTO band);

    void Update(BandDTO band);

    void delete(Long code);
    
    BandDTO find (Long code);
    
    void associateMembers(Long codeBand, String emailMember, Boolean leader);
}
