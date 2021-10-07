package com.adiuvo.topsevalidator;

public class modelImages {
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSrfid() {
        return srfid;
    }

    public void setSrfid(String srfid) {
        this.srfid = srfid;
    }

    public String getTreename() {
        return treename;
    }

    public void setTreename(String treename) {
        this.treename = treename;
    }

    public modelImages(String url, String verification, String operator, String datetime, String srfid, String treename) {
        this.url = url;
        this.verification = verification;
        this.operator = operator;
        this.datetime = datetime;
        this.srfid = srfid;
        this.treename = treename;
    }

    String verification;
    String operator;
    String datetime;
    String srfid;
    String treename;

    public modelImages() {
    }


}