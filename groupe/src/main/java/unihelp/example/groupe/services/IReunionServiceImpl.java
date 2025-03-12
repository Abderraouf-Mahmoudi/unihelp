package unihelp.example.groupe.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unihelp.example.groupe.entities.Reunion;
import unihelp.example.groupe.repositories.IReunionRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class IReunionServiceImpl implements IReunionService {
    IReunionRepository repo;
    @Override
    public Reunion findById(long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Reunion> findAll() {
        return repo.findAll();
    }

    @Override
    public Reunion save(Reunion reunion) {
        return repo.save(reunion);
    }

    @Override
    public void delete(Reunion reunion) {
    repo.delete(reunion);
    }

    @Override
    public Reunion addReunion(Reunion reunion) {
        return repo.save(reunion);
    }

    @Override
    public Reunion updateReunion(Reunion reunion) {
        return repo.save(reunion);
    }
}
