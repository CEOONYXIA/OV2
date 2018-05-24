package com.onyxiasoftware.linkr.Model;

/**
 * Created by Ramon on 4/24/2018.
 */

public class AppointmentObject {

    private String appointmentName;
    private String appointmentAddress;
    private String appointmentPhone;
    private String appointmentInfo;

    public AppointmentObject() {
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getAppointmentAddress() {
        return appointmentAddress;
    }

    public void setAppointmentAddress(String appointmentAddress) {
        this.appointmentAddress = appointmentAddress;
    }

    public String getAppointmentPhone() {
        return appointmentPhone;
    }

    public void setAppointmentPhone(String appointmentPhone) {
        this.appointmentPhone = appointmentPhone;
    }

    public String getAppointmentInfo() {
        return appointmentInfo;
    }

    public void setAppointmentInfo(String appointmentInfo) {
        this.appointmentInfo = appointmentInfo;
    }
}
