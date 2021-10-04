package ar.com.ada.api.mutants.controllers;

import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.mutants.entities.Human;
import ar.com.ada.api.mutants.entities.Mutant;
import ar.com.ada.api.mutants.models.request.SampleModel;
import ar.com.ada.api.mutants.models.response.GenericResponse;
import ar.com.ada.api.mutants.services.MutantService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class MutantController {

    @Autowired
    MutantService mutantService;

    @PostMapping("/mutant")
    public ResponseEntity<?> postMethodName(@RequestBody SampleModel sampleModel) {

        GenericResponse r = new GenericResponse();

        if (mutantService.isMutant(sampleModel.dna)) {

            Mutant mutant = mutantService.registerMutant(sampleModel.name, sampleModel.dna);
            r.isOk = true;
            r.id = mutant.get_id();
            r.message = "Is a mutant";
        } else {
            Human human = mutantService.registerHuman(sampleModel.name, sampleModel.dna);

            r.isOk = true;
            r.id = human.get_id();
            r.message = "Is a human";
        }

        return ResponseEntity.ok(r);
    }

}
