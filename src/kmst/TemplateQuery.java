package kmst;

import java.util.Properties;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.log4j.BasicConfigurator;


public class TemplateQuery {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Properties systemProperties = System.getProperties();
		systemProperties.setProperty("http.proxyHost","172.16.2.30");
		systemProperties.setProperty("http.proxyPort","8080");
        ParameterizedSparqlString qs = new ParameterizedSparqlString(""
                + "prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX dbo:     <http://dbpedia.org/ontology/>"
                + "\n"
                + "select distinct ?resource ?abstract where {\n"
                + "  ?resource rdfs:label 'Ibuprofen'@en.\n"
                + "  ?resource dbo:abstract ?abstract.\n"
                + "  FILTER (lang(?abstract) = 'en')}");


        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());

        ResultSet results = exec.execSelect();
        ResultSetFormatter.out(results);
        while (results.hasNext()) {

            System.out.println(results.next().get("abstract").toString());
        }

        
    }
}
