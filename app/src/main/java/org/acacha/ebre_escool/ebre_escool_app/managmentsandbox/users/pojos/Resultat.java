package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.pojos;

import com.google.gson.annotations.Expose;

/**
 * Created by dorian on 11/03/15.
 */
public class Resultat {
    @Expose
    private String id;
    @Expose
    private String message;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

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
}
