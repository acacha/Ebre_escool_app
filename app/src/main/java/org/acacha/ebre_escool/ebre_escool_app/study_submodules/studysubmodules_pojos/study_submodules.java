package org.acacha.ebre_escool.ebre_escool_app.study_submodules.studysubmodules_pojos;

/**
 * Created by alex on 05/02/15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class study_submodules {

    @Expose
    private String id;
    @Expose
    private String shortname;
    @Expose
    private String name;
    @SerializedName("module_id")
    @Expose
    private String moduleId;
    @Expose
    private String courseid;
    @Expose
    private String order;
    @Expose
    private String description;
    @Expose
    private String entryDate;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @Expose
    private String creationUserId;
    @Expose
    private String lastupdateUserId;
    @Expose
    private String markedForDeletion;
    @Expose
    private String markedForDeletionDate;

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
     *     The shortname
     */
    public String getShortname() {
        return shortname;
    }

    /**
     *
     * @param shortname
     *     The shortname
     */
    public void setShortname(String shortname) {
        this.shortname = shortname;
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
     *     The moduleId
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     *
     * @param moduleId
     *     The module_id
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     *
     * @return
     *     The courseid
     */
    public String getCourseid() {
        return courseid;
    }

    /**
     *
     * @param courseid
     *     The courseid
     */
    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    /**
     *
     * @return
     *     The order
     */
    public String getOrder() {
        return order;
    }

    /**
     *
     * @param order
     *     The order
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     *
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     *     The entryDate
     */
    public String getEntryDate() {
        return entryDate;
    }

    /**
     *
     * @param entryDate
     *     The entryDate
     */
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    /**
     *
     * @return
     *     The lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate
     *     The last_update
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return
     *     The creationUserId
     */
    public String getCreationUserId() {
        return creationUserId;
    }

    /**
     *
     * @param creationUserId
     *     The creationUserId
     */
    public void setCreationUserId(String creationUserId) {
        this.creationUserId = creationUserId;
    }

    /**
     *
     * @return
     *     The lastupdateUserId
     */
    public String getLastupdateUserId() {
        return lastupdateUserId;
    }

    /**
     *
     * @param lastupdateUserId
     *     The lastupdateUserId
     */
    public void setLastupdateUserId(String lastupdateUserId) {
        this.lastupdateUserId = lastupdateUserId;
    }

    /**
     *
     * @return
     *     The markedForDeletion
     */
    public String getMarkedForDeletion() {
        return markedForDeletion;
    }

    /**
     *
     * @param markedForDeletion
     *     The markedForDeletion
     */
    public void setMarkedForDeletion(String markedForDeletion) {
        this.markedForDeletion = markedForDeletion;
    }

    /**
     *
     * @return
     *     The markedForDeletionDate
     */
    public String getMarkedForDeletionDate() {
        return markedForDeletionDate;
    }

    /**
     *
     * @param markedForDeletionDate
     *     The markedForDeletionDate
     */
    public void setMarkedForDeletionDate(String markedForDeletionDate) {
        this.markedForDeletionDate = markedForDeletionDate;
    }

}
