package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {


    @SerializedName("logourl")
    @Expose
    private String logoURL;
    @SerializedName("logo_relative_url")
    @Expose
    private String relativeLogoURL;
    @SerializedName("api_url")
    @Expose
    private String api_url;


    @Expose
    private String id;
    @SerializedName("person_id")
    @Expose
    private String personId;
    @SerializedName("ip_address")
    @Expose
    private String ipAddress;
    @Expose
    private String username;
    @Expose
    private String password;
    @SerializedName("initial_password")
    @Expose
    private String initialPassword;
    @SerializedName("force_change_password_next_login")
    @Expose
    private String forceChangePasswordNextLogin;
    @Expose
    private String mainOrganizationaUnitId;
    @Expose
    private Object salt;
    @SerializedName("activation_code")
    @Expose
    private Object activationCode;
    @SerializedName("forgotten_password_realm")
    @Expose
    private String forgottenPasswordRealm;
    @SerializedName("forgotten_password_code")
    @Expose
    private Object forgottenPasswordCode;
    @SerializedName("forgotten_password_time")
    @Expose
    private Object forgottenPasswordTime;
    @SerializedName("remember_code")
    @Expose
    private Object rememberCode;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("last_modification_date")
    @Expose
    private String lastModificationDate;
    @SerializedName("last_modification_user")
    @Expose
    private String lastModificationUser;
    @SerializedName("creation_user")
    @Expose
    private String creationUser;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @Expose
    private String active;
    @SerializedName("ldap_dn")
    @Expose
    private String ldapDn;

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
     *     The personId
     */
    public String getPersonId() {
        return personId;
    }

    /**
     *
     * @param personId
     *     The person_id
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     *
     * @return
     *     The ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     *
     * @param ipAddress
     *     The ip_address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     *
     * @return
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     *     The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     *     The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     *     The initialPassword
     */
    public String getInitialPassword() {
        return initialPassword;
    }

    /**
     *
     * @param initialPassword
     *     The initial_password
     */
    public void setInitialPassword(String initialPassword) {
        this.initialPassword = initialPassword;
    }

    /**
     *
     * @return
     *     The forceChangePasswordNextLogin
     */
    public String getForceChangePasswordNextLogin() {
        return forceChangePasswordNextLogin;
    }

    /**
     *
     * @param forceChangePasswordNextLogin
     *     The force_change_password_next_login
     */
    public void setForceChangePasswordNextLogin(String forceChangePasswordNextLogin) {
        this.forceChangePasswordNextLogin = forceChangePasswordNextLogin;
    }

    /**
     *
     * @return
     *     The mainOrganizationaUnitId
     */
    public String getMainOrganizationaUnitId() {
        return mainOrganizationaUnitId;
    }

    /**
     *
     * @param mainOrganizationaUnitId
     *     The mainOrganizationaUnitId
     */
    public void setMainOrganizationaUnitId(String mainOrganizationaUnitId) {
        this.mainOrganizationaUnitId = mainOrganizationaUnitId;
    }

    /**
     *
     * @return
     *     The salt
     */
    public Object getSalt() {
        return salt;
    }

    /**
     *
     * @param salt
     *     The salt
     */
    public void setSalt(Object salt) {
        this.salt = salt;
    }

    /**
     *
     * @return
     *     The activationCode
     */
    public Object getActivationCode() {
        return activationCode;
    }

    /**
     *
     * @param activationCode
     *     The activation_code
     */
    public void setActivationCode(Object activationCode) {
        this.activationCode = activationCode;
    }

    /**
     *
     * @return
     *     The forgottenPasswordRealm
     */
    public String getForgottenPasswordRealm() {
        return forgottenPasswordRealm;
    }

    /**
     *
     * @param forgottenPasswordRealm
     *     The forgotten_password_realm
     */
    public void setForgottenPasswordRealm(String forgottenPasswordRealm) {
        this.forgottenPasswordRealm = forgottenPasswordRealm;
    }

    /**
     *
     * @return
     *     The forgottenPasswordCode
     */
    public Object getForgottenPasswordCode() {
        return forgottenPasswordCode;
    }

    /**
     *
     * @param forgottenPasswordCode
     *     The forgotten_password_code
     */
    public void setForgottenPasswordCode(Object forgottenPasswordCode) {
        this.forgottenPasswordCode = forgottenPasswordCode;
    }

    /**
     *
     * @return
     *     The forgottenPasswordTime
     */
    public Object getForgottenPasswordTime() {
        return forgottenPasswordTime;
    }

    /**
     *
     * @param forgottenPasswordTime
     *     The forgotten_password_time
     */
    public void setForgottenPasswordTime(Object forgottenPasswordTime) {
        this.forgottenPasswordTime = forgottenPasswordTime;
    }

    /**
     *
     * @return
     *     The rememberCode
     */
    public Object getRememberCode() {
        return rememberCode;
    }

    /**
     *
     * @param rememberCode
     *     The remember_code
     */
    public void setRememberCode(Object rememberCode) {
        this.rememberCode = rememberCode;
    }

    /**
     *
     * @return
     *     The createdOn
     */
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     *
     * @param createdOn
     *     The created_on
     */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    /**
     *
     * @return
     *     The lastModificationDate
     */
    public String getLastModificationDate() {
        return lastModificationDate;
    }

    /**
     *
     * @param lastModificationDate
     *     The last_modification_date
     */
    public void setLastModificationDate(String lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    /**
     *
     * @return
     *     The lastModificationUser
     */
    public String getLastModificationUser() {
        return lastModificationUser;
    }

    /**
     *
     * @param lastModificationUser
     *     The last_modification_user
     */
    public void setLastModificationUser(String lastModificationUser) {
        this.lastModificationUser = lastModificationUser;
    }

    /**
     *
     * @return
     *     The creationUser
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     *
     * @param creationUser
     *     The creation_user
     */
    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    /**
     *
     * @return
     *     The lastLogin
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     *
     * @param lastLogin
     *     The last_login
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     *
     * @return
     *     The active
     */
    public String getActive() {
        return active;
    }

    /**
     *
     * @param active
     *     The active
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     *
     * @return
     *     The ldapDn
     */
    public String getLdapDn() {
        return ldapDn;
    }

    /**
     *
     * @param ldapDn
     *     The ldap_dn
     */

    /**
     *
     * @return
     * The logo full URL
     */
    public String getLogoURL() {
        return logoURL;
    }
    /**
     *
     * @param logoURL
     * The logo url
     */
    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }
    /**
     *
     * @return
     * The logo realative URL
     */
    public String getRelativeLogoURL() {
        return relativeLogoURL;
    }
    /**
     *
     * @param relativeLogoURL
     * The logo relative url
     */
    public void setRelativeLogoURL(String relativeLogoURL) {
        this.relativeLogoURL = relativeLogoURL;
    }
    /**
     *
     * @return
     * The API URL of School
     */
    public String getApi_url() {
        return api_url;
    }
    /**
     *
     * @param api_url
     * The API url
     */

    public void setLdapDn(String ldapDn) {
        this.ldapDn = ldapDn;
    }

}