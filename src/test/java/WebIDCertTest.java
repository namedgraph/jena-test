
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

    private static final String MODULUS_SHORT = "A";
    private static final String MODULUS_LONG = "a0a15887e44bab6c36862c54771324604d55bda36d2c015cbad2dc6528b5c33e01103cfbeb9c8b8a22e7d74c66fc950f0f82616412fc8c5028e9384651d4f40c7c1d40c9b861191894537d719a5098c1eea5521d2273613cdd91563205fa3e25f99ecacc04824b07449087499a6a53a81a6b1d838406a97a0f7d691257fd4cd10662afbdebdbddacb9631a255930470e866949b5e8684b38d82141263f5c6c029f716a026c31f139b3f1164cfc1865283256f75cad0ee23318ca2323bc37704124f4d17f77763314c2ed92a71b2df5a960be83acb83285e1eda6bfb5af6f3f1342487ef961cc96b3680f544626f88c611fbcb2e03c06b261b838f72ecac59831";
    
    public final Query QUERY_MODULUS_SHORT = QueryFactory.create("PREFIX cert:    <http://www.w3.org/ns/auth/cert#>\n" +
"            \n" +
"SELECT *\n" +
"{\n" +
"   ?webid cert:key [\n" +
"      cert:modulus \"" + MODULUS_SHORT + "\"^^<http://www.w3.org/2001/XMLSchema#hexBinary> \n" +
"   ] .\n" +
"}");
    
    public final Query QUERY_MODULUS_LONG = QueryFactory.create("PREFIX cert:    <http://www.w3.org/ns/auth/cert#>\n" +
"            \n" +
"SELECT *\n" +
"{\n" +
"   ?webid cert:key [\n" +
"      cert:modulus \"" + MODULUS_LONG + "\"^^<http://www.w3.org/2001/XMLSchema#hexBinary> \n" +
"   ] .\n" +
"}");
    
    @Test
    public void testQueryModulusShort()
    {
        Model model = ModelFactory.createDefaultModel();
        model.createResource("https://localhost:4443/admin/acl/agents/b69fb813-b4c7-44d0-8582-a20b25cb2491/#this").
            addProperty(model.createProperty("http://www.w3.org/ns/auth/cert#key"), model.createResource("https://localhost:4443/admin/acl/public-keys/09d7f482-6bc8-4bef-887f-6d63bc4ebc2a/#this").
                    addLiteral(model.createProperty("http://www.w3.org/ns/auth/cert#modulus"), model.createTypedLiteral(MODULUS_SHORT, XSDDatatype.XSDhexBinary)));

        try (QueryExecution qex = QueryExecutionFactory.create(QUERY_MODULUS_SHORT, model))
        {
            ResultSet resultSet = qex.execSelect();
            assertTrue(resultSet.hasNext());
        }
    }
    
    @Test
    public void testQueryModulusLong()
    {
        Model model = ModelFactory.createDefaultModel();
        model.createResource("https://localhost:4443/admin/acl/agents/b69fb813-b4c7-44d0-8582-a20b25cb2491/#this").
            addProperty(model.createProperty("http://www.w3.org/ns/auth/cert#key"), model.createResource("https://localhost:4443/admin/acl/public-keys/09d7f482-6bc8-4bef-887f-6d63bc4ebc2a/#this").
                    addLiteral(model.createProperty("http://www.w3.org/ns/auth/cert#modulus"), model.createTypedLiteral(MODULUS_LONG, XSDDatatype.XSDhexBinary)));

        try (QueryExecution qex = QueryExecutionFactory.create(QUERY_MODULUS_LONG, model))
        {
            ResultSet resultSet = qex.execSelect();
            assertTrue(resultSet.hasNext());
        }
    }
    
}
