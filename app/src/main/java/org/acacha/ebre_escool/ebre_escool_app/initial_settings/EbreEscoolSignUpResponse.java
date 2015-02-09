package org.acacha.ebre_escool.ebre_escool_app.initial_settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.acacha.ebre_escool.ebre_escool_app.pojos.ApiUserProfile;
import org.acacha.ebre_escool.ebre_escool_app.pojos.Sessiondata;

public class EbreEscoolSignUpResponse {

    @Expose
    private String message;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String realm;
    @Expose
    private Sessiondata sessiondata;
    @SerializedName("api_user_profile")
    @Expose
    private ApiUserProfile apiUserProfile;

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The realm
     */
    public String getRealm() {
        return realm;
    }

    /**
     *
     * @param realm
     * The realm
     */
    public void setRealm(String realm) {
        this.realm = realm;
    }

    /**
     *
     * @return
     * The sessiondata
     */
    public Sessiondata getSessiondata() {
        return sessiondata;
    }

    /**
     *
     * @param sessiondata
     * The sessiondata
     */
    public void setSessiondata(Sessiondata sessiondata) {
        this.sessiondata = sessiondata;
    }

    /**
     *
     * @return
     * The apiUserProfile
     */
    public ApiUserProfile getApiUserProfile() {
        return apiUserProfile;
    }

    /**
     *
     * @param apiUserProfile
     * The api_user_profile
     */
    public void setApiUserProfile(ApiUserProfile apiUserProfile) {
        this.apiUserProfile = apiUserProfile;
    }

}

