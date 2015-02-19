package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nicolae on 5/02/15.
 */
public class Person {

    @Expose
    private String id;
    @Expose
    private String givenName;
    @Expose
    private String sn1;
    @Expose
    private String sn2;
    @Expose
    private String email1;
    @Expose
    private String email2;
    @Expose
    private String email3;
    @SerializedName("official_id")
    @Expose
    private String officialId;
    @SerializedName("official_id_type")
    @Expose
    private String officialIdType;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @Expose
    private String gender;
    @SerializedName("official_id2")
    @Expose
    private String officialId2;
    @SerializedName("official_id_type2")
    @Expose
    private String officialIdType2;
    @Expose
    private String homePostalAddress;
    @Expose
    private String photo;
    @SerializedName("locality_id")
    @Expose
    private String localityId;
    @Expose
    private String telephoneNumber;
    @Expose
    private String mobile;
    @SerializedName("bank_account_id")
    @Expose
    private String bankAccountId;
    @Expose
    private String notes;
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
     * The givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     *
     * @param givenName
     * The givenName
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     *
     * @return
     * The sn1
     */
    public String getSn1() {
        return sn1;
    }

    /**
     *
     * @param sn1
     * The sn1
     */
    public void setSn1(String sn1) {
        this.sn1 = sn1;
    }

    /**
     *
     * @return
     * The sn2
     */
    public String getSn2() {
        return sn2;
    }

    /**
     *
     * @param sn2
     * The sn2
     */
    public void setSn2(String sn2) {
        this.sn2 = sn2;
    }

    /**
     *
     * @return
     * The email1
     */
    public String getEmail1() {
        return email1;
    }

    /**
     *
     * @param email1
     * The email1
     */
    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    /**
     *
     * @return
     * The email2
     */
    public String getEmail2() {
        return email2;
    }

    /**
     *
     * @param email2
     * The email2
     */
    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    /**
     *
     * @return
     * The email3
     */
    public String getEmail3() {
        return email3;
    }

    /**
     *
     * @param email3
     * The email3
     */
    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    /**
     *
     * @return
     * The officialId
     */
    public String getOfficialId() {
        return officialId;
    }

    /**
     *
     * @param officialId
     * The official_id
     */
    public void setOfficialId(String officialId) {
        this.officialId = officialId;
    }

    /**
     *
     * @return
     * The officialIdType
     */
    public String getOfficialIdType() {
        return officialIdType;
    }

    /**
     *
     * @param officialIdType
     * The official_id_type
     */
    public void setOfficialIdType(String officialIdType) {
        this.officialIdType = officialIdType;
    }

    /**
     *
     * @return
     * The dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     *
     * @param dateOfBirth
     * The date_of_birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The officialId2
     */
    public String getOfficialId2() {
        return officialId2;
    }

    /**
     *
     * @param officialId2
     * The official_id2
     */
    public void setOfficialId2(String officialId2) {
        this.officialId2 = officialId2;
    }

    /**
     *
     * @return
     * The officialIdType2
     */
    public String getOfficialIdType2() {
        return officialIdType2;
    }

    /**
     *
     * @param officialIdType2
     * The official_id_type2
     */
    public void setOfficialIdType2(String officialIdType2) {
        this.officialIdType2 = officialIdType2;
    }

    /**
     *
     * @return
     * The homePostalAddress
     */
    public String getHomePostalAddress() {
        return homePostalAddress;
    }

    /**
     *
     * @param homePostalAddress
     * The homePostalAddress
     */
    public void setHomePostalAddress(String homePostalAddress) {
        this.homePostalAddress = homePostalAddress;
    }

    /**
     *
     * @return
     * The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo
     * The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     *
     * @return
     * The localityId
     */
    public String getLocalityId() {
        return localityId;
    }

    /**
     *
     * @param localityId
     * The locality_id
     */
    public void setLocalityId(String localityId) {
        this.localityId = localityId;
    }

    /**
     *
     * @return
     * The telephoneNumber
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     *
     * @param telephoneNumber
     * The telephoneNumber
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     *
     * @return
     * The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return
     * The bankAccountId
     */
    public String getBankAccountId() {
        return bankAccountId;
    }

    /**
     *
     * @param bankAccountId
     * The bank_account_id
     */
    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    /**
     *
     * @return
     * The notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     *
     * @param notes
     * The notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
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
