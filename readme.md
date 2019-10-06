# LightWeightSRGSTools

A java lib for generating srgs/sisr grammars from simple json objects to facilitate simple dynamic speech menus.

This does not intend to cover the full XML spec and only currently supports arrays of one-of rules with tags.

```JAVA
import com.diamonddrake.srgs.SRGSTools;

String jsontest = "{\"type\":\"sisr|srgs|umrcp-gdf\",\"rules\":[{\"id\":\"yes\",\"tag\":\"yes\",\"items\":[{\"value\":\"yes\",\"tag\":null},{\"value\":\"yeah\",\"tag\":null},{\"value\":\"yis\"}]},{\"id\":\"no\",\"tag\":\"no\",\"items\":[{\"value\":\"no\",\"tag\":null},{\"value\":\"know\",\"tag\":null},{\"value\":\"oh\"}]}]}";

SpeechGrammar grammar = SRGSTools.parseSpeechGrammarJson(jsontest);

String result = SRGSTools.saveToXML(grammar.rules, null, null, null);

```