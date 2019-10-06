package com.diamonddrake.srgs.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

// <tag>yes<tag> <!-- Defult tag for this rule -->
// <one-of>
//   <item>yes</item>
//   <item>yeah</item>
//   <item>yea</item>
//   <item>yis</item>
// </one-of>
// </rule>

/**
 * rule
 */
public class Rule {

    public String id = "";

    @JsonProperty("tag")
    public String defaultTag = null;
    
    public List<RuleItem> items = new ArrayList<RuleItem>();
}