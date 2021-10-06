package ar.com.ada.api.mutants.controllers;

import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.mutants.entities.Human;
import ar.com.ada.api.mutants.entities.Mutant;
import ar.com.ada.api.mutants.models.request.SampleModel;
import ar.com.ada.api.mutants.models.response.GenericResponse;
import ar.com.ada.api.mutants.services.MutantService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.ea.async.Async;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class MutantController {

    @Autowired
    MutantService mutantService;

    static { // EA Asynca, al menos ejecutarse una vez, para inicializar el Async/Await
        Async.init();
    }

    @PostMapping("/mutant")
    public ResponseEntity<?> postMethodName(@RequestBody SampleModel sampleModel)
            throws InterruptedException, ExecutionException {

        GenericResponse r = new GenericResponse();

        // Aca como hay un calculo intensivo(CPU Intensive) lo hacemos asincronico
        // usando un completable future(medio obsoleto, y primer aproach en JAVA)
        // en este caso, otro se encarga de completar esa operacion y nosotros nos
        // quedamos esperando
        CompletableFuture<Boolean> isMutantResult = CompletableFuture
                .supplyAsync(() -> mutantService.isMutant(sampleModel.dna));

        // mientras no este terminado ese futuro
        while (!isMutantResult.isDone()) {
            Thread.sleep(100); // a mimir 100 milisegundos. ESTO NO LO HAGAN EN UN CONTROLLER. Esto solo en
                               // APLICACIONS de consola, servicios, etc
        }

        // el get me devuelve si ese futuro, termino siendo True o False.
        if (isMutantResult.get()) {

            // Aca utilizando la clase Future
            Future<Mutant> mutantRegResult = mutantService.registerMutantAsync(sampleModel.name, sampleModel.dna);

            // mientras no este terminado ese futuro
            while (!mutantRegResult.isDone()) {
                Thread.sleep(100); // a mimir 100 milisegundos. ESTO NO LO HAGAN EN UN CONTROLLER. Esto solo en
                                   // APLICACIONS de consola, servicios, etc
            }

            r.isOk = true;
            r.id = mutantRegResult.get().get_id();
            r.message = "Is a mutant";
        } else {
            CompletableFuture<Human> humanRegResult =  CompletableFuture
            .supplyAsync(() -> mutantService.registerHuman(sampleModel.name, sampleModel.dna));

            //llamo al metodo "espera"(await)
            Human human = Async.await(humanRegResult);

            //C# (.net)

            //crear metodo async: public async Task<Human> registerHumanAsync(string name, string[] dna) { blha blah blha }
            //al llamarlo se llama asi:
            // Human human = await registerHumanAsyn("wolverine", sampleDna);



            r.isOk = true;
            r.id = human.get_id();
            r.message = "Is a human";
        }

        return ResponseEntity.ok(r);
    }

}
