package com.diamonddrake.srgs;

import java.util.ArrayList;
import java.util.List;

import com.diamonddrake.srgs.model.Rule;
import com.diamonddrake.srgs.model.SpeechGrammar;
import com.diamonddrake.srgs.model.RuleItem;
import com.diamonddrake.srgs.SRGSTools;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("SRGS Writer!");

        String jsontest = "{\"type\":\"sisr|srgs|umrcp-gdf\",\"rules\":[{\"id\":\"yes\",\"tag\":\"yes\",\"items\":[{\"value\":\"yes\",\"tag\":null},{\"value\":\"yeah\",\"tag\":null},{\"value\":\"yis\"}]},{\"id\":\"no\",\"tag\":\"no\",\"items\":[{\"value\":\"no\",\"tag\":null},{\"value\":\"know\",\"tag\":null},{\"value\":\"oh\"}]}]}";

        SpeechGrammar grammar = SRGSTools.parseSpeechGrammarJson(jsontest);

        Rule r = new Rule();
        r.id = "yes";
        r.defaultTag = "yes";
        r.items.add(new RuleItem("yes",null));
        r.items.add(new RuleItem("yeah",null));
        r.items.add(new RuleItem("ya",null));

        List<Rule> rs = new ArrayList<Rule>();
        rs.add(r);

        if(grammar != null)
            SRGSTools.saveToXML(grammar.rules, null, null, null);

    }
}
