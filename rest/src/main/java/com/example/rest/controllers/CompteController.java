package com.example.rest.controllers;

import com.example.rest.entities.Compte;
import com.example.rest.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banque/")
public class CompteController {
    @Autowired
    private CompteRepository compteRepository;

    @GetMapping(value = "/comptes", produces = {"application/json","application/xml"})
    public List<Compte> getAllComptes(){
        return compteRepository.findAll();
    }
    @GetMapping(value = "/comptes/{id}", produces = {"application/json","application/xml"})
    public ResponseEntity<Compte> getCompteById(@PathVariable long id){
        return compteRepository.findById(id)
                .map(compte -> ResponseEntity.ok().body(compte))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/comptes/",consumes = {"application/json","application/xml"},produces = {"application/json","application/xml"})
    public Compte createCompte(@RequestBody Compte compte){
        return compteRepository.save(compte);
    }

    @PutMapping(value = "/comptes/{id}",consumes = {"application/json","application/xml"},produces = {"application/json","application/xml"})
    public ResponseEntity<Compte> updateCompte(@PathVariable long id, @RequestBody Compte comptedetais){
        return compteRepository.findById(id)
                .map(compte ->{
                    compte.setSolde(comptedetais.getSolde());
                    compte.setDateCreation(comptedetais.getDateCreation());
                    compte.setType(comptedetais.getType());
                    Compte UptatedCompte=compteRepository.save(compte);
                    return  ResponseEntity.ok().body(UptatedCompte);

                }).orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> DeleteCompte(@PathVariable long id){
        return compteRepository.findById(id)
                .map(compte -> {
                    compteRepository.delete(compte);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
