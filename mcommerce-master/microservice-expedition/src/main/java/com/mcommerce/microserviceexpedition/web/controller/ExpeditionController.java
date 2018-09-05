package com.mcommerce.microserviceexpedition.web.controller;

import com.mcommerce.microserviceexpedition.dao.ExpeditionDao;
import com.mcommerce.microserviceexpedition.model.Expedition;
import com.mcommerce.microserviceexpedition.web.exceptions.ExpeditionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class ExpeditionController {
    @Autowired
    private ExpeditionDao expeditionDao;

    @PostMapping(value = "/Expedition")
    public ResponseEntity<Void> addExpedition(@RequestBody Expedition expedition){
        Expedition expeditionAdded = expeditionDao.save(expedition);
        if(expeditionAdded == null){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(expeditionAdded.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/Expedition/{id}")
    public Optional<Expedition> getExpedition(@PathVariable int id){
        Optional<Expedition> expedition = expeditionDao.findById(id);
        if(!expedition.isPresent()){
            throw new ExpeditionNotFoundException("Aucune expedition trouvée à cette id : "+id);
        }
        return expeditionDao.findById(id);
    }

    //Récuperer une Expedition par son id
    @GetMapping(value = "/suivi/{idCommande}")
    public Optional<Expedition> etatExpedition(@PathVariable int idCommande){

        Optional<Expedition> expedition = expeditionDao.findByIdCommandeLike(idCommande);

        if(!expedition.isPresent()) throw new ExpeditionNotFoundException("Cette expedition n'existe pas");

        else return expedition;

    }

    @PutMapping(value = "/Expedition")
    public ResponseEntity<Void> updateExpedition(@RequestBody Expedition expedition){
        Expedition expiditionUpdated = expeditionDao.save(expedition);
        if(expiditionUpdated == null){
            throw new ExpeditionNotFoundException("Aucune expedition trouvée à cette id : "+expedition.getId());
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(expiditionUpdated.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
