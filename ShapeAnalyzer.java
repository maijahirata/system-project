package System;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShapeAnalyzer {
    private static final Object EMPTY_PROPERTY = new Object();

    public static Machine analyze(String json) {
        //creating a Machine object by obtaining all required information
        String[][] entriesParsed = parseEntries(json);
        Object kind = inferObject(reifyKind(entriesParsed));
        Object[] partStates = reifyProperties(entriesParsed);
        boolean humanConstrained = SystemWhole.isHumanoid(partStates);
        //where machine instance is created
        Machine machine = new Machine(kind, partStates, humanConstrained);
        
        return machine;
    }

    public static String[][] parseEntries(String flatJson) {
        //parsing and creating the actual key, value pairs
        //removes opening and closing braces
        String json = flatJson.substring(1, flatJson.length() - 1);
        //splits resulting string based on commas and whitespace
        String[] entries = json.split(",\\s*");
        String[][] keyValuePairs = new String[entries.length][2];
        //iterating through entries array 
        for (int i = 0; i < entries.length; i++) {
            //splits creating the key, value pairs
            String[] pair = entries[i].split(":");
            //reassigning the keys to the first column, and values to the second column
            keyValuePairs[i][0] = pair[0];
            keyValuePairs[i][1] = pair[1];
        }
        return keyValuePairs;
    }

    public static String reifyKind(String[][] entries) {
        //takes key, value pairs parsed from parseEntries, returns the kind as a String
        String kind = null;
        for (String[] entry : entries) {
            if ("kind".equals(entry[0])) {
                kind = entry[1];
                break;
                }
            }
            return kind;
        }
    
    public static Object[] reifyProperties(String[][] entries) {
        int nonKindCount = 0;
        //tracking the kind value so properties array is properly initiated
        for (String[] property : entries) {
            if (!"kind".equals(property[0])) {
                nonKindCount++;
            }
        }
        Object[] properties = new Object[nonKindCount];
        int propertiesIndex = 0;
        for (String[] property : entries) {
            //keys that are not "kind" get inferred by inferObject
            if (!"kind".equals(property[0])) {
                Object inferredObject = inferObject(property[1]);
                //if it is not an instance of PartState, it creates a new PartState with the properties key and value
                if (inferredObject instanceof PartState) {
                    properties[propertiesIndex] = inferredObject;
            } 
                else {
                    //creating a new PartState instance
                    PartState partState = new PartState(property[0], property[1]);
                    //adding it to the array
                    properties[propertiesIndex] = partState;
                }
                //increments propertiesIndex to ensure proper array sizing
                propertiesIndex++;
        }
    }
    return properties;
}

    public static boolean isDigit(char c) {
        //determining if a character is a digit
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        //if passed in char is in the digits array, isDigit is true
        for (char digit : digits) {
            if (c == digit) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNonNumbers(String value) {
        //checks for isDigit, _ , and only one . in a string by iterating over each char value
        int decimalCount = 0;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (!isDigit(c) && c != '_' && c != '.') {
                return true;
            }
            if (c == '.') {
                decimalCount += 1;
                if (decimalCount > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Object inferObject(String value) {
        //used by reifyProperties to determine the object from string value 
        if ((value == null) || (value.length() == 0)) {
            return EMPTY_PROPERTY;
        }
        //if string is not null or empty, check for hasNonNumbers 
        if (!hasNonNumbers(value)) {
            Integer intVal = parseInteger(value);
            if (intVal != null) {
                return intVal;
            }
        }
        //if there is one . in the string then it is a double 
        Double doubleVal = parseDouble(value);
            if (doubleVal != null) {
                return doubleVal;
            }
        return value;
    }

    /* helper methods; checking if parsing fails */

    public static Integer parseInteger(String value) {
        try {
            return Integer.valueOf(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
    public static Double parseDouble(String value) {
        try {
            return Double.valueOf(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
}
