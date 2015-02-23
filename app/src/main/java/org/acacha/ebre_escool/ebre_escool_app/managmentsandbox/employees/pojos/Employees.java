package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.employees.pojos;

import com.google.gson.annotations.Expose;

/**
 * Created: pdavila on 11/02/15.
 */
public class Employees {
    @Expose
    private String id;
    @Expose
    private String person_id;
    @Expose
    private String code;
    @Expose
    private String type_id;

    public Employees() {

    }
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
     * The Person_id
     */
    public String getPerson_id() {
        return person_id;
    }

    /**
     *
     * @param person_id
     * The Person_id
     */
    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The type_id
     */
    public String getType_id() {
        return type_id;
    }

    /**
     *
     * @param type_id
     * The type_id
     */
    public void setType_id(String type_id) {
        this.type_id = type_id;
    }


}
