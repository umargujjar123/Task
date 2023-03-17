package com.example.basearchitectureproject.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("cnic")
        @Expose
        public String cnic;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("experience")
        @Expose
        public String experience;
        @SerializedName("commission")
        @Expose
        public Object commission;
        @SerializedName("avatar")
        @Expose
        public Object avatar;
        @SerializedName("rating")
        @Expose
        public String rating;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("registered_as")
        @Expose
        public String registeredAs;
        @SerializedName("ep_account_number")
        @Expose
        public Object epAccountNumber;
        @SerializedName("jazz_account_number")
        @Expose
        public Object jazzAccountNumber;
        @SerializedName("city")
        @Expose
        public Object city;
        @SerializedName("service_type_id")
        @Expose
        public Object serviceTypeId;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("latitude")
        @Expose
        public Float latitude;
        @SerializedName("longitude")
        @Expose
        public Float longitude;
        @SerializedName("device_token")
        @Expose
        public String deviceToken;
        @SerializedName("wallet")
        @Expose
        public String wallet;
        @SerializedName("verification_code")
        @Expose
        public String verificationCode;
        @SerializedName("verified_at")
        @Expose
        public String verifiedAt;
        @SerializedName("created_at")
        @Expose
        public String createdAt;

    }

}