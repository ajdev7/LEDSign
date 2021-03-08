/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.roelliott.RobertElliott;

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class GattAttributes {
    private static HashMap<String, String> attributes = new HashMap<String, String>();

    //GATT Services for standard profiles
    public static final String GENERIC_ACCESS_SERVICE =              "00001800-0000-1000-8000-00805f9b34fb";    //Required service that will be hidden from the user
    public static final String GENERIC_ATTRIBUTE_SERVICE =           "00001801-0000-1000-8000-00805f9b34fb";    //Required service that will be hidden from the user
    public static final String IMMEDIATE_ALERT_SERVICE =             "00001802-0000-1000-8000-00805f9b34fb";
    public static final String LINK_LOSS_SERVICE =                   "00001803-0000-1000-8000-00805f9b34fb";
    public static final String TX_POWER_SERVICE =                    "00001804-0000-1000-8000-00805f9b34fb";
    public static final String CURRENT_TIME_SERVICE =                "00001805-0000-1000-8000-00805f9b34fb";
    public static final String REFERENCE_TIME_UPDATE_SERVICE =       "00001806-0000-1000-8000-00805f9b34fb";
    public static final String NEXT_DST_CHANGE_SERVICE =             "00001807-0000-1000-8000-00805f9b34fb";
    public static final String GLUCOSE_SERVICE =                     "00001808-0000-1000-8000-00805f9b34fb";
    public static final String HEALTH_THERMOMETER_SERVICE =          "00001809-0000-1000-8000-00805f9b34fb";
    public static final String DEVICE_INFORMATION_SERVICE =          "0000180a-0000-1000-8000-00805f9b34fb";
    public static final String HEART_RATE_SERVICE =                  "0000180d-0000-1000-8000-00805f9b34fb";
    public static final String PHONE_ALERT_STATUS_SERVICE =          "0000180e-0000-1000-8000-00805f9b34fb";
    public static final String BATTERY_SERVICE =                     "0000180f-0000-1000-8000-00805f9b34fb";
    public static final String BLOOD_PRESSURE_SERVICE =              "00001810-0000-1000-8000-00805f9b34fb";
    public static final String ALERT_NOTIFICATION_SERVICE =          "00001811-0000-1000-8000-00805f9b34fb";
    public static final String HUMAN_INTERFACE_DEVICE_SERVICE =      "00001812-0000-1000-8000-00805f9b34fb";
    public static final String SCAN_PARAMETERS_SERVICE =             "00001813-0000-1000-8000-00805f9b34fb";
    public static final String RUNNING_SPEED_AND_CADENCE_SERVICE =   "00001814-0000-1000-8000-00805f9b34fb";
    public static final String CYCLING_SPEED_AND_CADENCE_SERVICE =   "00001816-0000-1000-8000-00805f9b34fb";
    public static final String CYCLING_POWER_SERVICE =               "00001818-0000-1000-8000-00805f9b34fb";
    public static final String LOCATION_AND_NAVIGATION_SERVICE =     "00001819-0000-1000-8000-00805f9b34fb";

    //GATT Characteristics for public services
    public static final String DEVICE_NAME_STRING =             "00002a00-0000-1000-8000-00805f9b34fb"; //For Device Information service
    public static final String APPEARANCE_STRING =              "00002a01-0000-1000-8000-00805f9b34fb";
    public static final String PREFERRED_PARAMETERS =           "00002a04-0000-1000-8000-00805f9b34fb";

    public static final String BATTERY_LEVEL =                  "00002a19-0000-1000-8000-00805f9b34fb"; //For Battery Service = uint8 0-100
    
    public static final String SYSTEM_ID =                      "00002a23-0000-1000-8000-00805f9b34fb"; //For Device Information Service
    public static final String MODEL_NUMBER =                   "00002a24-0000-1000-8000-00805f9b34fb";
    public static final String SERIAL_NUMBER =                  "00002a25-0000-1000-8000-00805f9b34fb";
    public static final String FIRMWARE_REVISION =              "00002a26-0000-1000-8000-00805f9b34fb";
    public static final String HARDWARE_REVISION =              "00002a27-0000-1000-8000-00805f9b34fb";
    public static final String SOFTWARE_REVISION =              "00002a28-0000-1000-8000-00805f9b34fb";
    public static final String MANUFACTURER_NAME =              "00002a29-0000-1000-8000-00805f9b34fb";
    public static final String REGULATORY_DATA =                "00002a2a-0000-1000-8000-00805f9b34fb";
    public static final String PLUG_N_PLAY_ID =                 "00002a50-0000-1000-8000-00805f9b34fb";

    public static final String HEART_RATE_MEASUREMENT =         "00002a37-0000-1000-8000-00805f9b34fb"; //For Heart Rate service

    //GATT Services for custom profiles
    public static final String MLDP_PRIVATE_SERVICE =           "00035b03-58e6-07dd-021a-08123a000300"; //Private service for Microchip MLDP
    public static final String PUMP_MONITOR_PRIVATE_SERVICE =	"11223344-5566-7788-9900-aabbccddeeff"; //Private service for Paolo's pump
    //GATT Characteristics for private services
    public static final String MLDP_DATA_PRIVATE_CHAR =   	    "00035b03-58e6-07dd-021a-08123a000301"; //Characteristic for MLDP Data, properties - notify, write
    public static final String MLDP_CONTROL_PRIVATE_CHAR = 	    "00035b03-58e6-07dd-021a-08123a0003ff"; //Characteristic for MLDP Control, properties - read, write

    public static final String PUMP_MONITOR_PRIVATE_CHAR1 =   	"01020304-0506-0708-0900-0a0b0c0d0e0f"; //Characteristic for Pump 1, properties 0x12 - notify, read
    public static final String PUMP_MONITOR_PRIVATE_CHAR2 =   	"01020304-0506-0708-0900-0a0b0c0d0e1f";	//Characteristic for Pump 2, properties 0x12 - notify, read
    public static final String PUMP_MONITOR_PRIVATE_CHAR3 =   	"01020304-0506-0708-0900-0a0b0c0d0e2f";	//Characteristic for Pump 3, properties 0x12 - notify, read
    public static final String PUMP_MONITOR_PRIVATE_CHAR4 =   	"01020304-0506-0708-0900-0a0b0c0d0e3f";	//Characteristic for Pump 4, properties 0x12 - notify, read
    public static final String PUMP_MONITOR_PRIVATE_CHAR5 =   	"01020304-0506-0708-0900-0a0b0c0d0e4f";	//Characteristic for writing to pump, properties 0x18 - notify, write

    public static final String CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR = "00002902-0000-1000-8000-00805f9b34fb";	//Special descriptor needed to enable notifications

    static {
        // Services
        attributes.put(GENERIC_ACCESS_SERVICE,              "Generic Access Service");					//Public service UUID
        attributes.put(GENERIC_ATTRIBUTE_SERVICE,           "Generic Attribute Service");
        attributes.put(IMMEDIATE_ALERT_SERVICE,             "Immediate Alert Service");
        attributes.put(LINK_LOSS_SERVICE,                   "Link Loss Service");
        attributes.put(TX_POWER_SERVICE,                    "Tx Power Service");
        attributes.put(CURRENT_TIME_SERVICE,                "Current Time Service");
        attributes.put(REFERENCE_TIME_UPDATE_SERVICE,       "Reference Time Update Service");
        attributes.put(NEXT_DST_CHANGE_SERVICE,             "Next DST Change Service");
        attributes.put(GLUCOSE_SERVICE,                     "Glucose Service");
        attributes.put(HEALTH_THERMOMETER_SERVICE,          "Health Thermometer Service");
        attributes.put(DEVICE_INFORMATION_SERVICE,          "Device Info Service");
        attributes.put(HEART_RATE_SERVICE,                  "Heart Rate Service");
        attributes.put(PHONE_ALERT_STATUS_SERVICE,          "Phone Alert Status Service");
        attributes.put(BATTERY_SERVICE,                     "Battery Service");
        attributes.put(BLOOD_PRESSURE_SERVICE,              "Blood Pressure Service");
        attributes.put(ALERT_NOTIFICATION_SERVICE,          "Alert Notification Service");
        attributes.put(HUMAN_INTERFACE_DEVICE_SERVICE,      "Human Interface Device Service");
        attributes.put(SCAN_PARAMETERS_SERVICE,             "Scan Parameters Service");
        attributes.put(RUNNING_SPEED_AND_CADENCE_SERVICE,   "Running Speed and Cadence Service");
        attributes.put(CYCLING_SPEED_AND_CADENCE_SERVICE,   "Cycling Speed and Cadence Service");
        attributes.put(CYCLING_POWER_SERVICE,               "Cycling Power Service");
        attributes.put(LOCATION_AND_NAVIGATION_SERVICE,     "Location and Navigation Service");
        
        attributes.put(MLDP_PRIVATE_SERVICE,                "MLDP Service");							//Private service UUID
        attributes.put(PUMP_MONITOR_PRIVATE_SERVICE,        "Pump Monitor Service");					

        // Characteristics
        attributes.put(DEVICE_NAME_STRING,                  "Device Name String");
        attributes.put(APPEARANCE_STRING,                   "Appearance String");
        attributes.put(PREFERRED_PARAMETERS,                "Preferred Parameters");

        attributes.put(BATTERY_LEVEL,                       "Battery Level");                   		//Battery Service
        
        attributes.put(SYSTEM_ID,                           "System ID");                       		//Device Information Service
        attributes.put(MODEL_NUMBER,                        "Model Number");
        attributes.put(SERIAL_NUMBER,                       "Serial Number");
        attributes.put(FIRMWARE_REVISION,                   "Firmware Revision");
        attributes.put(HARDWARE_REVISION,                   "Hardware Revision");
        attributes.put(SOFTWARE_REVISION,                   "Software Revision");
        attributes.put(MANUFACTURER_NAME,                   "Manufacturer Name");
        attributes.put(REGULATORY_DATA,    		    "Regulatory Certification Data List");
        attributes.put(PLUG_N_PLAY_ID,                      "Plug-n-Play ID");
        
        attributes.put(HEART_RATE_MEASUREMENT,              "Heart Rate Measurement");          		//Heart Rate Service
        
        attributes.put(MLDP_DATA_PRIVATE_CHAR,              "MLDP Data");                   			//Private Service
        attributes.put(MLDP_CONTROL_PRIVATE_CHAR,           "MLDP Conrtrol");

        attributes.put(PUMP_MONITOR_PRIVATE_CHAR1,          "Pump Pressure");                   		//Private Service
        attributes.put(PUMP_MONITOR_PRIVATE_CHAR2,          "Pump Fluid Temp");
        attributes.put(PUMP_MONITOR_PRIVATE_CHAR3,          "Pump Voltage");
        attributes.put(PUMP_MONITOR_PRIVATE_CHAR4,          "Pump Current");
        attributes.put(PUMP_MONITOR_PRIVATE_CHAR5,          "Pump ID");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }

//    public static boolean contains(String uuid) {
//        return attributes.containsKey(uuid);
//    }

}
