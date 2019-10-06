package com.diamonddrake.srgs;

import javax.xml.parsers.*;
import org.w3c.dom.DocumentType;
import org.w3c.dom.DOMImplementation;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.transform.TransformerException;

import com.diamonddrake.srgs.model.SpeechGrammar;
import com.diamonddrake.srgs.model.Rule;
import com.diamonddrake.srgs.model.RuleItem;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.w3c.dom.*;

/**
 * JsonToSrgs
 */
public class SRGSTools {

    public static SpeechGrammar parseSpeechGrammarJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            SpeechGrammar grammar = mapper.readValue(json, SpeechGrammar.class);
            return grammar;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String saveToXML(List<Rule> rules, String langCode, String rootName, String writePath) {
        Document dom;

        if (rootName == null || rootName == "") {
            rootName = "main";
        }

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("grammar");

            // set up grammar name spaces
            rootEle.setAttribute("version", "1.0");
            rootEle.setAttribute("xmlns", "http://www.w3.org/2001/06/grammar");
            rootEle.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootEle.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            if (langCode != null && langCode != "")
                rootEle.setAttribute("xml:lang", langCode);
            else
                rootEle.setAttribute("xml:lang", "en-US");

            rootEle.setAttribute("root", rootName);

            // create data elements and place them under root

            for (Rule r : rules) {
                Element rule = dom.createElement("rule");
                rule.setAttribute("id", r.id);
                // default tag for rule
                if (r.defaultTag != null) {
                    Element defaultTag = dom.createElement("tag");
                    defaultTag.appendChild(dom.createTextNode(r.defaultTag));
                    rule.appendChild(defaultTag);
                }
                // one-of rules
                Element oneOf = dom.createElement("one-of");
                for (RuleItem ri : r.items) {
                    Element item = dom.createElement("item");
                    item.appendChild(dom.createTextNode(ri.value));
                    if (ri.tag != null) {
                        Element Tag = dom.createElement("tag");
                        Tag.appendChild(dom.createTextNode(ri.tag));
                        item.appendChild(Tag);
                    }
                    oneOf.appendChild(item);
                }
                rule.appendChild(oneOf);
                rootEle.appendChild(rule);
            }

            // add the root rule
            Element rule = dom.createElement("rule");
            rule.setAttribute("id", rootName);
            rule.setAttribute("scope", "public");
            Element oneOf = dom.createElement("one-of");

            for (Rule r : rules) {
                Element item = dom.createElement("item");
                Element ruleref = dom.createElement("ruleref");
                ruleref.setAttribute("uri", "#" + r.id);
                item.appendChild(ruleref);
                oneOf.appendChild(item);
            }
            rule.appendChild(oneOf);
            rootEle.appendChild(rule);

            // end main

            // add the root to the document
            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                DOMImplementation domImpl = dom.getImplementation();
                DocumentType doctype = domImpl.createDocumentType("grammar", "-//W3C//DTD GRAMMAR 1.0//EN",
                        "http://www.w3.org/TR/speech-grammar/grammar.dtd");

                tr.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());

                // output stream to string builder
                OutputStream output = new OutputStream() {
                    private StringBuilder string = new StringBuilder();

                    @Override
                    public void write(int b) throws IOException {
                        this.string.append((char) b);
                    }

                    @Override
                    public String toString() {
                        return this.string.toString();
                    }
                };

                // send DOM to file
                tr.transform(new DOMSource(dom), new StreamResult(output));
                // new StreamResult(new FileOutputStream(xml)));

                // write to a file if needed
                if (writePath != null && !writePath.isEmpty()) {
                    tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(writePath)));
                }

                // System.out.println(output.toString());

                return output.toString();

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("Error trying to instantiate DocumentBuilder " + pce);
        }
        return null;
    }

}