package org.tms.finalproject.service.database;

import org.springframework.stereotype.Service;
import org.tms.finalproject.entity.Massage;
import org.tms.finalproject.repository.MassageRepository;

@Service
public class MassageServiceImpl implements MassageService {

    private MassageRepository massageRepository;

    public MassageServiceImpl(MassageRepository massageRepository) {
        this.massageRepository = massageRepository;
    }

    @Override
    public void createMassage(Massage massage) {
        if (massage == null) {
            throw new IllegalArgumentException("Massage is null");
        }
        massageRepository.save(massage);
    }
}
