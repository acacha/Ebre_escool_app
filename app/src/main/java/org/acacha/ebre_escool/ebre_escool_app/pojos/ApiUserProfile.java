package org.acacha.ebre_escool.ebre_escool_app.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiUserProfile {

    @Expose
    private String username;
    @Expose
    private String prova;
    @Expose
    private String another;
    @SerializedName("auth_token")
    @Expose
    private String authToken;

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
     * The prova
     */
    public String getProva() {
        return prova;
    }

    /**
     *
     * @param prova
     * The prova
     */
    public void setProva(String prova) {
        this.prova = prova;
    }

    /**
     *
     * @return
     * The another
     */
    public String getAnother() {
        return another;
    }

    /**
     *
     * @param another
     * The another
     */
    public void setAnother(String another) {
        this.another = another;
    }

    /**
     *
     * @return
     * The authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     *
     * @param authToken
     * The auth_token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}