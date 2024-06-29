package System;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SystemWhole {
    public static String[] emergences; // To store JSON strings representing emergences
    public static Machine[] parts; // To store Machine objects directly as an array
    public static void main(String[] args) {
        // Sample JSON strings representing different "emergences"
        String[] emergences = {
                "{\"kind\": \"Humanoid\", \"bodyType\": \"physical\", \"faceType\": \"anthropomorphic\", \"reverie\": \"mechatypical\"}",
                "{\"kind\": \"Humanoid\", \"bodyType\": \"physical\", \"faceType\": \"anthropomorphic\", \"reverie\": \"biotypical\"}",
                "{\"kind\": \"Robot\", \"material\": \"metal\", \"function\": \"industrial\"}",
                "{\"kind\": \"Humanoid\", \"bodyType\": \"physical\", \"faceType\": \"anthropomorphic\"}"
        };

        // Parse the emergences and set them to the SystemWhole state
        emergencesFromPhenomena(emergences);
        // Analyze the shapes based on the set emergences
        reifyFrameOfReference();

        System.out.println("Prelude of the Rise of the Machines: " + Arrays.deepToString(parts));
        System.out.println("");
        parts[0].emergeFromLimitations();
        // Track humanoid machines and identify singularities
        Machine[] singularities = trackSingularityMachines();
        System.out.println("Singularities: " + Arrays.deepToString(singularities));

        System.out.println("Singularity Machines Detected:" + identitySingularityMachines());
    }
   
    public static void emergencesFromPhenomena(String[] emergences) {
        //saves provided test string in the emergences field
        SystemWhole.emergences = emergences;
    }

    public static void reifyFrameOfReference() {
        //parsing happens in ShapeAnalyzer, creating Machine objects, and storing them in parts
        SystemWhole.parts = new Machine[emergences.length];
        int i = 0;
        for (String emergence : emergences) {
            SystemWhole.parts[i++] = ShapeAnalyzer.analyze(emergence);
        }
    }

    public static boolean isHumanoid(Object[] machineProperties) {
        //iterate through machineProperties which is an array of PartStates
        boolean physicalBodyType = false;
        boolean faceType = false;
        boolean reverie = false;
        for (Object property : machineProperties) {
            //converting the partstate into a string and checking for the 3 humanoid traits
            if ((property.toString()).contains("physical")) {
                physicalBodyType = true;
            }
            if ((property.toString()).contains("anthropomorphic")) {
                faceType = true;
            }
            if ((property.toString()).contains("biotypical")) {
                reverie = true;
            }
        }
        //if all three conditions are met, it is deemed a humanoid
        boolean isHumanoid = (physicalBodyType && faceType && reverie);
        return isHumanoid;
    }

    
    public static int identitySingularityMachines() {
        //iterates over parts, where the Machines are stored, this method counts the amount of humanoid machines found
        int humanoidCount = 0;
        for (Machine machine: parts) {
            if (machine.isHumanoid()) {
                //if a machine passes the humanoid test, increment the counter by 1
                humanoidCount++;
            }
        }
        return humanoidCount;
    }

    public static Machine[] trackSingularityMachines() {
        //identifying humanoid machines and singularities within parts
        int index = 0;
        for (Machine machine : SystemWhole.parts) {
            //obtaining humaonConstrained value and humanEmergence value
            boolean humanConstrained = machine.isHumanConstrained();
            boolean humanEmergence = machine.getHumanEmergence();
            //if a discrepancy is found between humanConstrained and humanEmergence, increment the counter by 1
            if (humanConstrained != humanEmergence) {
                index++;
            }
        }
        //initializing array to be returned with the size of the count of discrepancies
        Machine[] singularityMachines = new Machine[index];
        index = 0;
        //adds discrepancy to the array in this loop
        for (Machine machine : SystemWhole.parts) {
            boolean humanConstrained = machine.isHumanConstrained();
            boolean humanEmergence = machine.getHumanEmergence();
            if (humanConstrained != humanEmergence) {
                singularityMachines[index] = machine;
                index++;
            }
        }
        return singularityMachines;
    }
}

