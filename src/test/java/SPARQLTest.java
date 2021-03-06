
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;
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
public class SPARQLTest
{

    private static final String MODULUS = "a0a15887e44bab6c36862c54771324604d55bda36d2c015cbad2dc6528b5c33e01103cfbeb9c8b8a22e7d74c66fc950f0f82616412fc8c5028e9384651d4f40c7c1d40c9b861191894537d719a5098c1eea5521d2273613cdd91563205fa3e25f99ecacc04824b07449087499a6a53a81a6b1d838406a97a0f7d691257fd4cd10662afbdebdbddacb9631a255930470e866949b5e8684b38d82141263f5c6c029f716a026c31f139b3f1164cfc1865283256f75cad0ee23318ca2323bc37704124f4d17f77763314c2ed92a71b2df5a960be83acb83285e1eda6bfb5af6f3f1342487ef961cc96b3680f544626f88c611fbcb2e03c06b261b838f72ecac59831";
    
    private final Model stringModel = ModelFactory.createDefaultModel().
            createResource("https://localhost:4443/admin/acl/public-keys/09d7f482-6bc8-4bef-887f-6d63bc4ebc2a/#this").
            addLiteral(ResourceFactory.createProperty("http://www.w3.org/ns/auth/cert#modulus"), ResourceFactory.createTypedLiteral(MODULUS, XSDDatatype.XSDstring)).
            getModel();
    private final Model hexBinaryModel = ModelFactory.createDefaultModel().
            createResource("https://localhost:4443/admin/acl/public-keys/09d7f482-6bc8-4bef-887f-6d63bc4ebc2a/#this").
            addLiteral(ResourceFactory.createProperty("http://www.w3.org/ns/auth/cert#modulus"), ResourceFactory.createTypedLiteral(MODULUS, XSDDatatype.XSDhexBinary)).
            getModel();

    @Test
    public void testXsdStringBGP()
    {
        Query query = QueryFactory.create("PREFIX  cert: <http://www.w3.org/ns/auth/cert#>\n" +
"\n" +
"SELECT  *\n" +
"WHERE\n" +
"  { ?key    cert:modulus   \"" + MODULUS + "\" .\n" +
"  }");
        try (QueryExecution qex = QueryExecutionFactory.create(query, stringModel))
        {
            ResultSet resultSet = qex.execSelect();
            assertTrue(resultSet.hasNext());
        }
    }
    
    @Test
    public void testHexBinaryBGP()
    {
        Query query = QueryFactory.create("PREFIX  cert: <http://www.w3.org/ns/auth/cert#>\n" +
"\n" +
"SELECT  *\n" +
"WHERE\n" +
"  { ?key    cert:modulus   \"" + MODULUS + "\"^^<http://www.w3.org/2001/XMLSchema#hexBinary> .\n" +
"  }");
        try (QueryExecution qex = QueryExecutionFactory.create(query, hexBinaryModel))
        {
            ResultSet resultSet = qex.execSelect();
            assertTrue(resultSet.hasNext());
        }
    }

    @Test
    public void testHexBinaryFilter()
    {
        Query query = QueryFactory.create("PREFIX  cert: <http://www.w3.org/ns/auth/cert#>\n" +
"\n" +
"SELECT  *\n" +
"WHERE\n" +
"  { ?key    cert:modulus   ?mod ." + 
"    FILTER (?mod = \"" + MODULUS + "\"^^<http://www.w3.org/2001/XMLSchema#hexBinary>)\n" +
"  }");
        try (QueryExecution qex = QueryExecutionFactory.create(query, hexBinaryModel))
        {
            ResultSet resultSet = qex.execSelect();
            assertTrue(resultSet.hasNext());
        }
    }

}
