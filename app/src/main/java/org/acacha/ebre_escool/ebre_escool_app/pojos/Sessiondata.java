package org.acacha.ebre_escool.ebre_escool_app.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sessiondata {

    @Expose
    private String id;
    @Expose
    private String username;
    @Expose
    private String email;
    @SerializedName("secondary_email")
    @Expose
    private String secondaryEmail;
    @SerializedName("terciary_email")
    @Expose
    private String terciaryEmail;
    @SerializedName("person_id")
    @Expose
    private String personId;
    @Expose
    private String mainOrganizationaUnitId;
    @Expose
    private String givenName;
    @Expose
    private String sn1;
    @Expose
    private String sn2;
    @Expose
    private String fullname;
    @SerializedName("alt_fullname")
    @Expose
    private String altFullname;
    @SerializedName("person_official_id")
    @Expose
    private String personOfficialId;
    @SerializedName("person_official_id_type")
    @Expose
    private String personOfficialIdType;
    @SerializedName("person_date_of_birth")
    @Expose
    private String personDateOfBirth;
    @SerializedName("person_gender")
    @Expose
    private String personGender;
    @SerializedName("person_secondary_official_id")
    @Expose
    private String personSecondaryOfficialId;
    @SerializedName("person_secondary_official_id_type")
    @Expose
    private String personSecondaryOfficialIdType;
    @SerializedName("person_homePostalAddress")
    @Expose
    private String personHomePostalAddress;
    @SerializedName("person_locality_id")
    @Expose
    private String personLocalityId;
    @SerializedName("locality_name")
    @Expose
    private Object localityName;
    @SerializedName("person_entryDate")
    @Expose
    private String personEntryDate;
    @SerializedName("person_telephoneNumber")
    @Expose
    private String personTelephoneNumber;
    @SerializedName("person_mobile")
    @Expose
    private String personMobile;
    @SerializedName("person_notes")
    @Expose
    private String personNotes;
    @Expose
    private String photo;
    @SerializedName("logged_in")
    @Expose
    private Boolean loggedIn;
    @SerializedName("is_admin")
    @Expose
    private Boolean isAdmin;
    @SerializedName("is_teacher")
    @Expose
    private Boolean isTeacher;
    @SerializedName("is_student")
    @Expose
    private Boolean isStudent;
    @SerializedName("teacher_code")
    @Expose
    private String teacherCode;
    @SerializedName("teacher_id")
    @Expose
    private String teacherId;
    @SerializedName("teacher_department_id")
    @Expose
    private String teacherDepartmentId;
    @SerializedName("department_shortname")
    @Expose
    private String departmentShortname;

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
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The secondaryEmail
     */
    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    /**
     *
     * @param secondaryEmail
     * The secondary_email
     */
    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    /**
     *
     * @return
     * The terciaryEmail
     */
    public String getTerciaryEmail() {
        return terciaryEmail;
    }

    /**
     *
     * @param terciaryEmail
     * The terciary_email
     */
    public void setTerciaryEmail(String terciaryEmail) {
        this.terciaryEmail = terciaryEmail;
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
     * The mainOrganizationaUnitId
     */
    public String getMainOrganizationaUnitId() {
        return mainOrganizationaUnitId;
    }

    /**
     *
     * @param mainOrganizationaUnitId
     * The mainOrganizationaUnitId
     */
    public void setMainOrganizationaUnitId(String mainOrganizationaUnitId) {
        this.mainOrganizationaUnitId = mainOrganizationaUnitId;
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
     * The fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     *
     * @param fullname
     * The fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     *
     * @return
     * The altFullname
     */
    public String getAltFullname() {
        return altFullname;
    }

    /**
     *
     * @param altFullname
     * The alt_fullname
     */
    public void setAltFullname(String altFullname) {
        this.altFullname = altFullname;
    }

    /**
     *
     * @return
     * The personOfficialId
     */
    public String getPersonOfficialId() {
        return personOfficialId;
    }

    /**
     *
     * @param personOfficialId
     * The person_official_id
     */
    public void setPersonOfficialId(String personOfficialId) {
        this.personOfficialId = personOfficialId;
    }

    /**
     *
     * @return
     * The personOfficialIdType
     */
    public String getPersonOfficialIdType() {
        return personOfficialIdType;
    }

    /**
     *
     * @param personOfficialIdType
     * The person_official_id_type
     */
    public void setPersonOfficialIdType(String personOfficialIdType) {
        this.personOfficialIdType = personOfficialIdType;
    }

    /**
     *
     * @return
     * The personDateOfBirth
     */
    public String getPersonDateOfBirth() {
        return personDateOfBirth;
    }

    /**
     *
     * @param personDateOfBirth
     * The person_date_of_birth
     */
    public void setPersonDateOfBirth(String personDateOfBirth) {
        this.personDateOfBirth = personDateOfBirth;
    }

    /**
     *
     * @return
     * The personGender
     */
    public String getPersonGender() {
        return personGender;
    }

    /**
     *
     * @param personGender
     * The person_gender
     */
    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }

    /**
     *
     * @return
     * The personSecondaryOfficialId
     */
    public String getPersonSecondaryOfficialId() {
        return personSecondaryOfficialId;
    }

    /**
     *
     * @param personSecondaryOfficialId
     * The person_secondary_official_id
     */
    public void setPersonSecondaryOfficialId(String personSecondaryOfficialId) {
        this.personSecondaryOfficialId = personSecondaryOfficialId;
    }

    /**
     *
     * @return
     * The personSecondaryOfficialIdType
     */
    public String getPersonSecondaryOfficialIdType() {
        return personSecondaryOfficialIdType;
    }

    /**
     *
     * @param personSecondaryOfficialIdType
     * The person_secondary_official_id_type
     */
    public void setPersonSecondaryOfficialIdType(String personSecondaryOfficialIdType) {
        this.personSecondaryOfficialIdType = personSecondaryOfficialIdType;
    }

    /**
     *
     * @return
     * The personHomePostalAddress
     */
    public String getPersonHomePostalAddress() {
        return personHomePostalAddress;
    }

    /**
     *
     * @param personHomePostalAddress
     * The person_homePostalAddress
     */
    public void setPersonHomePostalAddress(String personHomePostalAddress) {
        this.personHomePostalAddress = personHomePostalAddress;
    }

    /**
     *
     * @return
     * The personLocalityId
     */
    public String getPersonLocalityId() {
        return personLocalityId;
    }

    /**
     *
     * @param personLocalityId
     * The person_locality_id
     */
    public void setPersonLocalityId(String personLocalityId) {
        this.personLocalityId = personLocalityId;
    }

    /**
     *
     * @return
     * The localityName
     */
    public Object getLocalityName() {
        return localityName;
    }

    /**
     *
     * @param localityName
     * The locality_name
     */
    public void setLocalityName(Object localityName) {
        this.localityName = localityName;
    }

    /**
     *
     * @return
     * The personEntryDate
     */
    public String getPersonEntryDate() {
        return personEntryDate;
    }

    /**
     *
     * @param personEntryDate
     * The person_entryDate
     */
    public void setPersonEntryDate(String personEntryDate) {
        this.personEntryDate = personEntryDate;
    }

    /**
     *
     * @return
     * The personTelephoneNumber
     */
    public String getPersonTelephoneNumber() {
        return personTelephoneNumber;
    }

    /**
     *
     * @param personTelephoneNumber
     * The person_telephoneNumber
     */
    public void setPersonTelephoneNumber(String personTelephoneNumber) {
        this.personTelephoneNumber = personTelephoneNumber;
    }

    /**
     *
     * @return
     * The personMobile
     */
    public String getPersonMobile() {
        return personMobile;
    }

    /**
     *
     * @param personMobile
     * The person_mobile
     */
    public void setPersonMobile(String personMobile) {
        this.personMobile = personMobile;
    }

    /**
     *
     * @return
     * The personNotes
     */
    public String getPersonNotes() {
        return personNotes;
    }

    /**
     *
     * @param personNotes
     * The person_notes
     */
    public void setPersonNotes(String personNotes) {
        this.personNotes = personNotes;
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
     * The loggedIn
     */
    public Boolean getLoggedIn() {
        return loggedIn;
    }

    /**
     *
     * @param loggedIn
     * The logged_in
     */
    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     *
     * @return
     * The isAdmin
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     *
     * @param isAdmin
     * The is_admin
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     *
     * @return
     * The isTeacher
     */
    public Boolean getIsTeacher() {
        return isTeacher;
    }

    /**
     *
     * @param isTeacher
     * The is_teacher
     */
    public void setIsTeacher(Boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    /**
     *
     * @return
     * The isStudent
     */
    public Boolean getIsStudent() {
        return isStudent;
    }

    /**
     *
     * @param isStudent
     * The is_student
     */
    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

    /**
     *
     * @return
     * The teacherCode
     */
    public String getTeacherCode() {
        return teacherCode;
    }

    /**
     *
     * @param teacherCode
     * The teacher_code
     */
    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    /**
     *
     * @return
     * The teacherId
     */
    public String getTeacherId() {
        return teacherId;
    }

    /**
     *
     * @param teacherId
     * The teacher_id
     */
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    /**
     *
     * @return
     * The teacherDepartmentId
     */
    public String getTeacherDepartmentId() {
        return teacherDepartmentId;
    }

    /**
     *
     * @param teacherDepartmentId
     * The teacher_department_id
     */
    public void setTeacherDepartmentId(String teacherDepartmentId) {
        this.teacherDepartmentId = teacherDepartmentId;
    }

    /**
     *
     * @return
     * The departmentShortname
     */
    public String getDepartmentShortname() {
        return departmentShortname;
    }

    /**
     *
     * @param departmentShortname
     * The department_shortname
     */
    public void setDepartmentShortname(String departmentShortname) {
        this.departmentShortname = departmentShortname;
    }

}