package com.diamonddrake.srgs;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.diamonddrake.srgs.model.SpeechGrammar;

public class AppTest {

    @Test
    public void testJsonToXml() {

        String jsontest = "{\"type\":\"sisr|srgs|umrcp-gdf\",\"rules\":[{\"id\":\"yes\",\"tag\":\"yes\",\"items\":[{\"value\":\"yes\",\"tag\":null},{\"value\":\"yeah\",\"tag\":null},{\"value\":\"yis\"}]},{\"id\":\"no\",\"tag\":\"no\",\"items\":[{\"value\":\"no\",\"tag\":null},{\"value\":\"know\",\"tag\":null},{\"value\":\"oh\"}]}]}";

        String expectedBody = "<rule id=\"yes\">\r\n        <tag>yes</tag>\r\n        <one-of>\r\n            <item>yes</item>\r\n            <item>yeah</item>\r\n            <item>yis</item>\r\n        </one-of>\r\n    </rule>\r\n    <rule id=\"no\">\r\n        <tag>no</tag>\r\n        <one-of>\r\n            <item>no</item>\r\n            <item>know</item>\r\n            <item>oh</item>\r\n        </one-of>\r\n    </rule>\r\n    <rule id=\"main\" scope=\"public\">\r\n        <one-of>\r\n            <item>\r\n                <ruleref uri=\"#yes\"/>\r\n            </item>\r\n            <item>\r\n                <ruleref uri=\"#no\"/>\r\n            </item>\r\n        </one-of>\r\n    </rule>";
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<!DOCTYPE grammar PUBLIC \"-//W3C//DTD GRAMMAR 1.0//EN\" \"http://www.w3.org/TR/speech-grammar/grammar.dtd\">\r\n<grammar xmlns=\"http://www.w3.org/2001/06/grammar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" root=\"main\" version=\"1.0\" xml:lang=\"en-US\">\r\n    <rule id=\"yes\">\r\n        <tag>yes</tag>\r\n        <one-of>\r\n            <item>yes</item>\r\n            <item>yeah</item>\r\n            <item>yis</item>\r\n        </one-of>\r\n    </rule>\r\n    <rule id=\"no\">\r\n        <tag>no</tag>\r\n        <one-of>\r\n            <item>no</item>\r\n            <item>know</item>\r\n            <item>oh</item>\r\n        </one-of>\r\n    </rule>\r\n    <rule id=\"main\" scope=\"public\">\r\n        <one-of>\r\n            <item>\r\n                <ruleref uri=\"#yes\"/>\r\n            </item>\r\n            <item>\r\n                <ruleref uri=\"#no\"/>\r\n            </item>\r\n        </one-of>\r\n    </rule>\r\n</grammar>\r\n";

        try {
            SpeechGrammar grammar = SRGSTools.parseSpeechGrammarJson(jsontest);

            assertNotNull(grammar);

            String result = com.diamonddrake.srgs.SRGSTools.saveToXML(grammar.rules, null, null, null);

            // less strict test, only checks children of grammar tag
            assertThat("output didn't match", result, containsString(expectedBody));
            assertEquals(expected, result);

        } catch (Exception ex) {
            fail("Exception occured when not expected");
        }

    }
}
