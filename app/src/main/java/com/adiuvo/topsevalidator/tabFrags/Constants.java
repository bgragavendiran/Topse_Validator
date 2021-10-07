package com.adiuvo.topsevalidator.tabFrags;

/**
 * Created by Bikki Kumar Sha on 01,June,2020
 * Project : com.csir.feluda
 * Copyright : Adiuvo Diagnostics, Chennai
 */
public class Constants {
    // Sf tags
    public static final String lastFileName="lastFileName";
    public static final String lastOperatorName="lastOprName";


    public static final String operatorsList="operatorList";
    public static final String operatorsName="oprName";
    public static final String operatorsEmail="email";
    public static final String operatorsSex="sex";
    public static final String operatorsID="oprId";

    public static final String TAG_APP_USER_DATA = "TAG_APP_USER_DATA";

    public static final String ROOT_DIR_NAME_TOPSE = "topse";

    public static final String FLASH_STATE_USER_SF = "FLASH_STATE_USER_SF";
    public static final String NTC_GENE_STATE_USERS_SF = "NTC_GENE_STATE_USERS_SF";

    // Feluda Img Proc Constants
    public static final String result = "result";
    public static final String test_inv_val = "test_inv_val";
    public static final String norm_test_inv_val = "norm_test_inv_val";     // This value is calculated for patient strip only (using ntc averaging)
    public static final String white_bal_sca_fac = "white_bal_sca_fac";


    public static final String SAMPLE_TYPE_SF = "SAMPLE_TYPE_SF";
    public static final String FIP_ERROR = "FIP_ERROR";
    public static String SrfIdtoRecordHashmap="SrfIds";
    public static String SrfStatusMap="SrfStatusMap";
    public static String SrfIDSelectedMap="SrfIDSelectedMap";
    public static String currentSRFID="SRFID";
    public static String sampleID="SampleID";
    public static String currentMapCount="MapCount";
    public static String patientDob="patientDob";
    public static String patientCategory="patientCategory";
    public static String aadhar="aadhar";
    public static String ICMRmap="ICMRmap";
    public static String NumberOfImagingSessions="NumberOfImagingSessions";
    public static String GeneralUserAnalytics="GeneralUserAnalytics";
    public static String NumberOfPDfGenerated="NumberOfPDfGenerated";
    public static String NumberOfReportsVerified="NumberOfReportsVerified";
    public static String NumberOfReportsRejected="NumberOfReportsRejected";

    public enum SAMPLE_TYPE {
        NTC, PATIENT;
    }

    public static final String NTC_GENE_TYPE_SF = "NTC_GENE_TYPE_SF";
    public enum NTC_GENE_TYPE {
        N2, N3;
    }

    public static final String NTC_LOCATION_ARRAY_SF = "NTC_LOCATION_ARRAY_SF";
    public static final String NTC_INTENSITY_ARRAY_SF = "NTC_INTENSITY_ARRAY_SF";

    // Generated image consts
    public static final String IMAGE_result_original = "result_original";
    public static final String IMAGE_result_boundingbox_cropped = "result_boundingbox_cropped";
    public static final String IMAGE_result_strip_control = "result_strip_control";
    public static final String IMAGE_result_strip_test = "result_strip_test";

    //session data SF
    public static final String OPERATOR_ID_SF = "OPERATOR_ID_SF";
    public static final String CURRENT_SESSION_ID_SF = "CURRENT_SESSION_ID_SF";


    public static final String PATIENT_TAG_SF = "PATIENT_TAG_SF";
    public static final String PATIENT_DATETIME_SF = "PATIENT_DATETIME_SF";
    public static final String PATIENT_NAME_SF = "PATIENT_NAME_SF";
    public static final String PATIENT_AGE_SF = "PATIENT_AGE_SF";
    public static final String PATIENT_SEX_SF = "PATIENT_SEX_SF";
}
