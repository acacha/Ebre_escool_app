package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.lesson.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mark on 19/02/15.
 */
public class lesson { //TODO Es una copia de theacher per a poder fer l'array, ja que no tinc api acabada i per tant encara no tinc pojo

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("person_id")
    @Expose
    private String personId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("creator_id")
    @Expose
    private String creatorId;
    @SerializedName("last_update_user_id")
    @Expose
    private String lastUpdateUserId;
    @SerializedName("marked_for_deletion")
    @Expose
    private String markedForDeletion;
    @SerializedName("marked_for_deletion_date")
    @Expose
    private String markedForDeletionDate;
    @SerializedName("DNI_NIF")
    @Expose
    private String DNINIF;

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
     * The personId
     */
    public String getPersonId() {
        return personId;
    }

    /**
     *
     * @param personId
     * The person_id
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * The entry_date
     */
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    /**
     *
     * @return
     * The lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate
     * The last_update
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return
     * The creatorId
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     *
     * @param creatorId
     * The creator_id
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     *
     * @return
     * The lastUpdateUserId
     */
    public String getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    /**
     *
     * @param lastUpdateUserId
     * The last_update_user_id
     */
    public void setLastUpdateUserId(String lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
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
     * The marked_for_deletion
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
     * The marked_for_deletion_date
     */
    public void setMarkedForDeletionDate(String markedForDeletionDate) {
        this.markedForDeletionDate = markedForDeletionDate;
    }

    /**
     *
     * @return
     * The DNINIF
     */
    public String getDNINIF() {
        return DNINIF;
    }

    /**
     *
     * @param DNINIF
     * The DNI_NIF
     */
    public void setDNINIF(String DNINIF) {
        this.DNINIF = DNINIF;
    }

}
