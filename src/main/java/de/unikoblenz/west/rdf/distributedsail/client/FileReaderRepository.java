package de.unikoblenz.west.rdf.distributedsail.client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.rdf.distributedsail.DistributedRepository;

/**
 * reads an RDF format and pushes it to the database.
 * 
 * @author lkastler
 */
public class FileReaderRepository implements Runnable {

	public static final String BASE_URI = "http://example.org";
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final DistributedRepository repo;
	private final RDFParser parser;
	private String fileName = null;
	
	private Thread t = null;
	
	public FileReaderRepository(RDFParser parser, DistributedRepository repo) {
		this.parser = parser;
		this.repo = repo;
	}

	/**
	 * reads a file of RDF triples and push them into the DistributedSail stores via a DistributedRepository.
	 * 
	 * @param fileName - name of the file.
	 * @throws RepositoryException
	 */
	public void execute(String fileName) throws RepositoryException {
		if(t != null) {
			// TODO not a good idea to use RepositoryException!
			throw new RepositoryException("could not start");
		}
		this.fileName = fileName;
		
		t = new Thread(this);
		t.start();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO use an RDF reader here maybe
		try {
			RepositoryConnection con = repo.getConnection();
			parser.setValueFactory(con.getValueFactory());
			
			parser.parse(new FileReader(fileName), BASE_URI);
			
		} 
		catch (FileNotFoundException e) {
			log.error("could not open file: " + fileName, e);
		} catch (IOException e) {
			log.error("could not read file properly: " + fileName, e);
		} catch (RepositoryException e) {
			log.error("could not open repo connection", e);
		} catch (RDFParseException e) {
			log.error("could not parse RDF", e);
		} catch (RDFHandlerException e) {
			log.error("could not handle RDF", e);
		}
		finally {
			t = null;
		}
	}
}
