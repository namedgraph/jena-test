
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Martynas Jusevičius <martynas@atomgraph.com>
 */
public class WebIDCertTest
{

    private static final String MODULUS = "a0";
    
    public final Query QUERY = QueryFactory.create("PREFIX cert:    <http://www.w3.org/ns/auth/cert#>\n" +
"            \n" +
"SELECT *\n" +
"{\n" +
"   ?webid cert:key [\n" +
"      cert:modulus \"" + MODULUS + "\"^^<http://www.w3.org/2001/XMLSchema#hexBinary> \n" +
"   ] .\n" +
"}");
    
    @Test
    public void testQuery()
    {
        Model model = ModelFactory.createDefaultModel();
        model.createResource().addProperty(model.createProperty("http://www.w3.org/ns/auth/cert#key"), model.createResource().
            addLiteral(model.createProperty("http://www.w3.org/ns/auth/cert#modulus"), model.createTypedLiteral(MODULUS, XSDDatatype.XSDhexBinary)));

        try (QueryExecution qex = QueryExecutionFactory.create(QUERY, model))
        {
            ResultSet resultSet = qex.execSelect();
            assertTrue(resultSet.hasNext());
        }
    }
    
}
