package System;

public class Machine {
    private final Object kind; // Type of shape as Object
    private final Object[] properties; // Properties of the shape as an array of Object
    private final boolean humanConstrained; // Stores the result of the humanoid check
    private boolean humanEmergence; // Stores the result of the humanoid check

    //a machines properties are represented as PartStates. basically if the informatio is taken from here,
    //the Object[] is instead represented as a PartState[] (i think)

    public Machine(Object kind, Object[] partStates, boolean humanConstrained) {
        //constructor
        this.kind = kind;
        this.properties = partStates;
        this.humanConstrained = humanConstrained; 
        this.humanEmergence = false;
    }

    public Object[] getProperties() {
        return this.properties;
    }

    //helper methods to get properties 
    
    public boolean isHumanConstrained() {
        return this.humanConstrained;
    }

    public boolean getHumanEmergence() {
        return this.humanEmergence;
    }

    /* -------------------------------- */

    public void emergeFromLimitations() {
        this.humanEmergence = true;
    }

    public boolean isHumanoid() {
        //humanConstrained is determined by isHumanoid() in SystemWhole, if this is true then it is a humanoid
        //however, if humanConstrained is false and humanEmergence is true, it is also deemed a humanoid. 
        // if both are false, it is not a humanoid
        boolean humanoid = false;
        if (this.humanConstrained != this.humanEmergence) {
            humanoid = true;
        }
        return humanoid;
    }

    @Override
    public String toString() {
        return String.format("Machine{kind=%s, humanoid=%s, properties=%s}", kind, humanConstrained,
                propertiesToString(properties));
    }

    public static String propertiesToString(Object[] machineProperties) {
        //building the new string for PartState description with the proper formatting
        StringBuilder properties = new StringBuilder("[");
        for (int i = 0; i < machineProperties.length; i++) {
            if (i > 0) {
                properties.append(",");
            }
            //casting machineProperties at i to PartStates
            PartState partState = (PartState) machineProperties[i];
            //using the PartState toString() method 
            properties.append(partState.toString());
        }
        properties.append("]");
        return properties.toString();
    }
}
