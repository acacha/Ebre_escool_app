package org.acacha.ebre_escool.ebre_escool_app;

/**
 * Created by sergi on 22/01/15.
 */
public class School {

    public School() {

    }

    public School(String fullName) {
        this.setFullName(fullName);
    }

    @Override
    public String toString(){
        return getFullName();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private String fullName;

}
