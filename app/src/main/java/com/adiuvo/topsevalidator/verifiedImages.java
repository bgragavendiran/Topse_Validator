package com.adiuvo.topsevalidator;

public class verifiedImages {
    String url;
    String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public verifiedImages(String url, String verification, String operator, String datetime, String srfid, String treename,String userName) {
        this.url = url;
        this.verification = verification;
        this.operator = operator;
        this.datetime = datetime;
        this.srfid = srfid;
        this.treename = treename;
        this.userName= userName;
    }

    String verification;
    String operator;
    String datetime;
    String srfid;
    String treename;

    public verifiedImages() {
    }

}
