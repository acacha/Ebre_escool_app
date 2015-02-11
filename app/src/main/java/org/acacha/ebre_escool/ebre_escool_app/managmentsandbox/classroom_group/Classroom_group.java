package org.acacha.ebre_escool.ebre_escool_app.pojos;

/**
 * Created by galandriel on 2/02/15.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Classroom_group {

    @Expose
    private String id;
    @Expose
    private String code;
    @Expose
    private String shortName;
    @Expose
    private String name;
    @Expose
    private String description;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @Expose
    private String type;
    @Expose
    private String entryDate;
    @Expose
    private String lastupdate;
    @Expose
    private String creationUserId;
    @Expose
    private String lastupdateUserId;
    @Expose
    private String markedForDeletion;
    @Expose
    private String markedForDeletionDate;

    public Classroom_group() {

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
     * The shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     *
     * @param shortName
     * The shortName
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     *
     * @param courseId
     * The course_id
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The entryDate
     */
    public String getEntryDate() {
        return entryDate;
    }

    /**
     *
     * @param entryDate
     * The entryDate
     */
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    /**
     *
     * @return
     * The lastupdate
     */
    public String getLastupdate() {
        return lastupdate;
    }

    /**
     *
     * @param lastupdate
     * The lastupdate
     */
    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    /**
     *
     * @return
     * The creationUserId
     */
    public String getCreationUserId() {
        return creationUserId;
    }

    /**
     *
     * @param creationUserId
     * The creationUserId
     */
    public void setCreationUserId(String creationUserId) {
        this.creationUserId = creationUserId;
    }

    /**
     *
     * @return
     * The lastupdateUserId
     */
    public String getLastupdateUserId() {
        return lastupdateUserId;
    }

    /**
     *
     * @param lastupdateUserId
     * The lastupdateUserId
     */
    public void setLastupdateUserId(String lastupdateUserId) {
        this.lastupdateUserId = lastupdateUserId;
    }

    /**
     *
     * @return
     * The markedForDeletion
     */
    public String getMarkedForDeletion() {
        return markedForDeletion;
    }

    /**
     *
     * @param markedForDeletion
     * The markedForDeletion
     */
    public void setMarkedForDeletion(String markedForDeletion) {
        this.markedForDeletion = markedForDeletion;
    }

    /**
     *
     * @return
     * The markedForDeletionDate
     */
    public String getMarkedForDeletionDate() {
        return markedForDeletionDate;
    }

    /**
     *
     * @param markedForDeletionDate
     * The markedForDeletionDate
     */
    public void setMarkedForDeletionDate(String markedForDeletionDate) {
        this.markedForDeletionDate = markedForDeletionDate;
    }

}