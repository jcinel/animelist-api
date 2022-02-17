package com.api.animelist.services;

import com.api.animelist.models.ListaAnimesKey;
import com.api.animelist.models.ListaAnimesModel;
import com.api.animelist.repositories.ListaAnimesRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ListaAnimesService {

    final ListaAnimesRepository listaAnimesRepository;

    public ListaAnimesService(ListaAnimesRepository listaAnimesRepository) { this.listaAnimesRepository = listaAnimesRepository; }

    @Transactional
    public Object save(ListaAnimesModel listaAnimesModel) { return listaAnimesRepository.save(listaAnimesModel); }

    public List<ListaAnimesModel> findAll() {
        return listaAnimesRepository.findAll();
    }

    public Optional<ListaAnimesModel> findById(ListaAnimesKey id) {
        return listaAnimesRepository.findById(id);
    }

    @Transactional
    public void delete(ListaAnimesModel listaAnimesModel) { listaAnimesRepository.delete(listaAnimesModel); }
}
