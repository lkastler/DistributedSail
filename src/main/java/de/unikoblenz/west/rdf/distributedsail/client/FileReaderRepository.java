package de.unikoblenz.west.rdf.distributedsail.client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.openrdf.model.Resource;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.rdf.distributedsail.DistributedRepository;

/**
 * reads an RDF format and pushes it to the database.
 * 
 * @author lkastler
 */
public class FileReaderRepository implements Runnable {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final DistributedRepository repo;
	private String fileName = null;
	
	private Thread t = null;
	
	public FileReaderRepository(DistributedRepository repo) {
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
			con.add(new FileReader(fileName), null, RDFFormat.TURTLE, new Resource[0]);
			
		} catch (RepositoryException e) {
			log.error("could not access repository", e);
		} catch (RDFParseException e) {
			log.error("could not parse RDF", e);
		} catch (FileNotFoundException e) {
			log.error("could not find file: " + fileName, e);
		} catch (IOException e) {
			log.error("could not access file: " + fileName, e);
		} 
		finally {
			t = null;
		}
	}
}
