package kmst;

import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

public class NumkPaths {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BasicConfigurator.configure();
		Properties systemProperties = System.getProperties();
		systemProperties.setProperty("http.proxyHost","172.16.2.30");
		systemProperties.setProperty("http.proxyPort","8080");
		Scanner sc = new Scanner(System.in);
		System.out.println("input 1st entity\n");
		String start=sc.nextLine();
		System.out.println("input 2nd entity");
		String end =sc.nextLine();
		System.out.println("input k number of paths");
		int k = sc.nextInt();
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("prefix dbpedia: <http://dbpedia.org/resource/>\n");
		queryStr.append("prefix dbp: <http://dbpedia.org/page/>\n");
		queryStr.append("prefix :<urn:ex>\n");
		queryStr.append("select ?start ?u ?v ?end \nwhere{ \n{?start (<>|!<>)* ?u . \n ?u (:relatedTo) ?v . \nfilter(!isBlank(?u))\n}\nUNION\n{\n?v (<>|!<>)* ?end .\nfilter(!isBlank(?v))\n}\n }\nlimit "+k+" \nvalues (?start ?end){( :"+start+" :"+end+")}") ;
		Query q = queryStr.asQuery();
		System.out.println(q.toString());
		QueryExecution qx=QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql/", q);
		ResultSet rs=qx.execSelect();
		ResultSetFormatter.out(rs);
		while(rs.hasNext()) {
			QuerySolution sol=rs.nextSolution();
			System.out.println(sol);
		}
	}
}
