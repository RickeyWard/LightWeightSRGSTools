package com.diamonddrake.srgs.model;

/**
 * ruleItem
 */
public class RuleItem {

    public RuleItem(String value, String tag){
        this();
        this.value = value;
        this.tag = tag;
    }

    public RuleItem(){}

    public String value = "";
    public String tag = null;
}