package ar.com.ada.api.mutants;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.mutants.entities.DNASample;
import ar.com.ada.api.mutants.services.MutantService;

@SpringBootTest
class MutantsApplicationTests {

	@Autowired
	MutantService mutantService;
	
	@Test
	void contextLoads() {
	}

	@Test
	void testearSiEsMutante() {
		//boolean isMutant(String[] dna);
		String[] dna = {"ATGCGA",
						"CAGTGC",
						"TTATGT",
						"AGAAGG",
						"CCCCTA",
						"TCACTG"};

		assertTrue(mutantService.isMutant(dna));
	}

	@Test
	void testearSiEsHumano() {
		//boolean isMutant(String[] dna);
		String[] dna = {"ATGCGA",
						"CAGTGC",
						"TTATTT",
						"AGACGG",
						"GCGTCA",
						"TCACTG"};

		assertFalse(mutantService.isMutant(dna));
	}

	@Test
	void testearImpresion() {
		//boolean isMutant(String[] dna);
		String[] dna = {"ATGCGA",
						"CAGTGC",
						"TTATTT",
						"AGACGG",
						"GCGTCA",
						"TCACTG"};
		DNASample sample = new DNASample(dna);
		sample.printMatrix();
		assertTrue(true);
	}

}
