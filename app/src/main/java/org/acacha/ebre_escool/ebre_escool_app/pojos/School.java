package org.acacha.ebre_escool.ebre_escool_app.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class School {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String fullname;
    @SerializedName("logourl")
    @Expose
    private String logoURL;
    @SerializedName("logo_relative_url")
    @Expose
    private String relativeLogoURL;
    @SerializedName("api_url")
    @Expose
    private String api_url;
    @SerializedName("login_api_url")
    @Expose
    private String login_api_url;
    @SerializedName("dns_domain")
    @Expose
    private String school_dns_domain;
    @Expose
    private String email;
    @SerializedName("secondary_email")
    @Expose
    private String secondaryEmail;
    @SerializedName("school_terciary_email")
    @Expose
    private String schoolTerciaryEmail;
    @SerializedName("school_official_id")
    @Expose
    private String schoolOfficialId;
    @SerializedName("school_locality_id")
    @Expose
    private String schoolLocalityId;
    @SerializedName("school_telephoneNumber")
    @Expose
    private String schoolTelephoneNumber;
    @SerializedName("school_mobile")
    @Expose
    private String schoolMobile;
    @SerializedName("school_bank_account_id")
    @Expose
    private String schoolBankAccountId;
    @SerializedName("school_notes")
    @Expose
    private String schoolNotes;
    @SerializedName("school_entryDate")
    @Expose
    private String schoolEntryDate;
    @SerializedName("school_last_update")
    @Expose
    private String schoolLastUpdate;
    @SerializedName("school_creationUserId")
    @Expose
    private Object schoolCreationUserId;
    @SerializedName("school_lastupdateUserId")
    @Expose
    private Object schoolLastupdateUserId;
    @SerializedName("school_markedForDeletion")
    @Expose
    private String schoolMarkedForDeletion;
    @SerializedName("school_markedForDeletionDate")
    @Expose
    private String schoolMarkedForDeletionDate;

    public School(){

    }

    public School(String fullname){
        this.setFullname(fullname);
    }

    public String toString(){
        return this.getFullname();
    }

    /**
     *
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     *
     * @param fullname
     *     The fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     *
     * @return
     *     The logo full URL
     */
    public String getLogoURL() {
        return logoURL;
    }

    /**
     *
     * @param logoURL
     *     The logo url
     */
    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    /**
     *
     * @return
     *     The logo realative URL
     */
    public String getRelativeLogoURL() {
        return relativeLogoURL;
    }

    /**
     *
     * @param relativeLogoURL
     *     The logo relative url
     */
    public void setRelativeLogoURL(String relativeLogoURL) {
        this.relativeLogoURL = relativeLogoURL;
    }

    /**
     *
     * @return
     *   The API URL of School
     */
    public String getApi_url() {
        return api_url;
    }

    /**
     *
     * @param api_url
     *     The API url
     */
    public void setApi_url(String api_url) {
        this.api_url = api_url;
    }

    /**
     *
     * @return
     *     The Login API URL of School
     */
    public String getLogin_api_url() {
        return login_api_url;
    }

    /**
     *
     * @param login_api_url
     *     The Login API url
     */
    public void setLogin_api_url(String login_api_url) {
        this.login_api_url = login_api_url;
    }

    /**
     *
     * @return
     *     The school DNS domain name
     */
    public String getSchool_dns_domain() {
        return school_dns_domain;
    }

    /**
     *
     * @param school_dns_domain
     *     The school dns domain name
     */
    public void setSchool_dns_domain(String school_dns_domain) {
        this.school_dns_domain = school_dns_domain;
    }

    /**
     *
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     *     The secondaryEmail
     */
    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    /**
     *
     * @param secondaryEmail
     *     The secondary_email
     */
    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    /**
     *
     * @return
     *     The schoolTerciaryEmail
     */
    public String getSchoolTerciaryEmail() {
        return schoolTerciaryEmail;
    }

    /**
     *
     * @param schoolTerciaryEmail
     *     The school_terciary_email
     */
    public void setSchoolTerciaryEmail(String schoolTerciaryEmail) {
        this.schoolTerciaryEmail = schoolTerciaryEmail;
    }

    /**
     *
     * @return
     *     The schoolOfficialId
     */
    public String getSchoolOfficialId() {
        return schoolOfficialId;
    }

    /**
     *
     * @param schoolOfficialId
     *     The school_official_id
     */
    public void setSchoolOfficialId(String schoolOfficialId) {
        this.schoolOfficialId = schoolOfficialId;
    }

    /**
     *
     * @return
     *     The schoolLocalityId
     */
    public String getSchoolLocalityId() {
        return schoolLocalityId;
    }

    /**
     *
     * @param schoolLocalityId
     *     The school_locality_id
     */
    public void setSchoolLocalityId(String schoolLocalityId) {
        this.schoolLocalityId = schoolLocalityId;
    }

    /**
     *
     * @return
     *     The schoolTelephoneNumber
     */
    public String getSchoolTelephoneNumber() {
        return schoolTelephoneNumber;
    }

    /**
     *
     * @param schoolTelephoneNumber
     *     The school_telephoneNumber
     */
    public void setSchoolTelephoneNumber(String schoolTelephoneNumber) {
        this.schoolTelephoneNumber = schoolTelephoneNumber;
    }

    /**
     *
     * @return
     *     The schoolMobile
     */
    public String getSchoolMobile() {
        return schoolMobile;
    }

    /**
     *
     * @param schoolMobile
     *     The school_mobile
     */
    public void setSchoolMobile(String schoolMobile) {
        this.schoolMobile = schoolMobile;
    }

    /**
     *
     * @return
     *     The schoolBankAccountId
     */
    public String getSchoolBankAccountId() {
        return schoolBankAccountId;
    }

    /**
     *
     * @param schoolBankAccountId
     *     The school_bank_account_id
     */
    public void setSchoolBankAccountId(String schoolBankAccountId) {
        this.schoolBankAccountId = schoolBankAccountId;
    }

    /**
     *
     * @return
     *     The schoolNotes
     */
    public String getSchoolNotes() {
        return schoolNotes;
    }

    /**
     *
     * @param schoolNotes
     *     The school_notes
     */
    public void setSchoolNotes(String schoolNotes) {
        this.schoolNotes = schoolNotes;
    }

    /**
     *
     * @return
     *     The schoolEntryDate
     */
    public String getSchoolEntryDate() {
        return schoolEntryDate;
    }

    /**
     *
     * @param schoolEntryDate
     *     The school_entryDate
     */
    public void setSchoolEntryDate(String schoolEntryDate) {
        this.schoolEntryDate = schoolEntryDate;
    }

    /**
     *
     * @return
     *     The schoolLastUpdate
     */
    public String getSchoolLastUpdate() {
        return schoolLastUpdate;
    }

    /**
     *
     * @param schoolLastUpdate
     *     The school_last_update
     */
    public void setSchoolLastUpdate(String schoolLastUpdate) {
        this.schoolLastUpdate = schoolLastUpdate;
    }

    /**
     *
     * @return
     *     The schoolCreationUserId
     */
    public Object getSchoolCreationUserId() {
        return schoolCreationUserId;
    }

    /**
     *
     * @param schoolCreationUserId
     *     The school_creationUserId
     */
    public void setSchoolCreationUserId(Object schoolCreationUserId) {
        this.schoolCreationUserId = schoolCreationUserId;
    }

    /**
     *
     * @return
     *     The schoolLastupdateUserId
     */
    public Object getSchoolLastupdateUserId() {
        return schoolLastupdateUserId;
    }

    /**
     *
     * @param schoolLastupdateUserId
     *     The school_lastupdateUserId
     */
    public void setSchoolLastupdateUserId(Object schoolLastupdateUserId) {
        this.schoolLastupdateUserId = schoolLastupdateUserId;
    }

    /**
     *
     * @return
     *     The schoolMarkedForDeletion
     */
    public String getSchoolMarkedForDeletion() {
        return schoolMarkedForDeletion;
    }

    /**
     *
     * @param schoolMarkedForDeletion
     *     The school_markedForDeletion
     */
    public void setSchoolMarkedForDeletion(String schoolMarkedForDeletion) {
        this.schoolMarkedForDeletion = schoolMarkedForDeletion;
    }

    /**
     *
     * @return
     *     The schoolMarkedForDeletionDate
     */
    public String getSchoolMarkedForDeletionDate() {
        return schoolMarkedForDeletionDate;
    }

    /**
     *
     * @param schoolMarkedForDeletionDate
     *     The school_markedForDeletionDate
     */
    public void setSchoolMarkedForDeletionDate(String schoolMarkedForDeletionDate) {
        this.schoolMarkedForDeletionDate = schoolMarkedForDeletionDate;
    }

}