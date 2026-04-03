package br.edu.utfpr.td.tsi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import br.edu.utfpr.td.tsi.model.Students;

public class JsonFileRecorder {
    private Logger logger = LoggerFactory.getLogger(Scraper.class);

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public void gravarArquivo(List<Students> alunos) {

		String json = gson.toJson(alunos);
		try {
			FileWriter writer = new FileWriter("saida.json");
			gson.toJson(alunos, writer);
			writer.close();
		} catch (JsonIOException | IOException e) {
			logger.atLevel(Level.DEBUG).setCause(e).setMessage(json).log();
		}
	}
}
