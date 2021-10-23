package automate.capstone.feeder.Utility;

import java.util.regex.Pattern;

/**
 * Created by Allen Cayanan on 3/6/2018.
 */

public class NumberValidator {

    public static boolean isPhoneNumber(String phonenumber){
        boolean result = false;
        if(((phonenumber.startsWith("+63"))&&(phonenumber.length()==13)&&(phonenumber.substring(1,12).matches("[0-9]+")))) {
            result = true;
        }
        return result;
    }

    public static String phoneNumberConvertFormat(String phonenumber){
        if(phonenumber.startsWith("0")){
            phonenumber = "+63" + phonenumber.substring(1,11);
        }
        phonenumber = phonenumber.replace("-", "");
        phonenumber = phonenumber.replace(" ", "");
        phonenumber = phonenumber.replace("(", "");
        phonenumber = phonenumber.replace(")", "");
        phonenumber = phonenumber.replace("_", "");
        return phonenumber;
    }


    public static boolean isNumber(String number){
        boolean result = false;
        if(number.matches("[0-9]+")) {
            result = true;
        }
        return result;
    }

    public static boolean isIpAddress(String ipaddress){
        boolean result = false;
        Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        if (PATTERN.matcher(ipaddress).matches()){
            result = true;
        }
        return result;
    }

    public static boolean isValidFeedInput(String number){
        boolean result = false;
        if(isNumber(number)){
            if (Integer.parseInt(number) > 0 /*&& Integer.parseInt(number) <= 150 condition to limit feed amount*/){
                result = true;
            }
        }
        return result;
    }
}
