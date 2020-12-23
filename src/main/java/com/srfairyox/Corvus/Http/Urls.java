package com.srfairyox.Corvus.Http;

public class Urls {

    public static String getBaseUrl(String server) {
        return "https://"+server+".darkorbit.com";
    }

    public static String getOpenBackPage(String sid) {
        return "/?dosid="+sid;
    }

    public static String getInternalStart() {
        return "/indexInternal.es?action=internalStart";
    }

    public static String getSpinGate(String userID, String sid, int gateId, String gateName) {
        return "/flashinput/galaxyGates.php?userID="+userID+"&action=multiEnergy&sid="+sid+"&gateID="+gateId+"&"+gateName+"=1";
    }

    public static String getSpinGateMultiplier(String userID, String sid, int gateId, String gateName) {
        return "/flashinput/galaxyGates.php?userID="+userID+"&action=multiEnergy&sid="+sid+"&gateID="+gateId+"&"+gateName+"=1&multiplier=1";
    }

    public static String getSpinGateAmount(String userID, String sid, String gateId, String gateName, String spinAmount) {
        return "/flashinput/galaxyGates.php?userID="+userID+"&action=multiEnergy&sid="+sid+"&gateID="+gateId+"&"+gateName+"=1&spinamount="+spinAmount;
    }

    public static String getSpinGateMultiplierAmount(String userID, String sid, String gateId, String gateName, String spinAmount) {
        return "/flashinput/galaxyGates.php?userID="+userID+"&action=multiEnergy&sid="+sid+"&gateID="+gateId+"&"+gateName+"=1&multiplier=1&spinamount="+spinAmount;
    }

    public static String getSpinGateSample(String userID, String sid, int gateId, String gateName) {
        return "/flashinput/galaxyGates.php?userID="+userID+"&action=multiEnergy&sid="+sid+"&gateID="+gateId+"&"+gateName+"=1&sample=1";
    }

    public static String getSpinGateSampleMultiplier(String userID, String sid, int gateId, String gateName) {
        return "/flashinput/galaxyGates.php?userID="+userID+"&action=multiEnergy&sid="+sid+"&gateID="+gateId+"&"+gateName+"=1&multiplier=1&sample=1";
    }

    public static String getSpinGateSampleAmount(String userID, String sid, String gateId, String gateName, String spinAmount) {
        return "/flashinput/galaxyGates.php?userID="+userID+"&action=multiEnergy&sid="+sid+"&gateID="+gateId+"&"+gateName+"=1&sample=1&spinamount="+spinAmount;
    }

    public static String getSpinGateSampleMultiplierAmount(String userID, String sid, String gateId, String gateName, String spinAmount) {
        return "/flashinput/galaxyGates.php?userID="+userID+"&action=multiEnergy&sid="+sid+"&gateID="+gateId+"&"+gateName+"=1&multiplier=1&sample=1&spinamount="+spinAmount;
    }

    public static String getGateInfo(String userID, String sid) {
        return "/flashinput/galaxyGates.php?userID="+userID+"&action=init&sid="+sid;
    }

    public static String getPlaceGate(String userID, String sid, int gateId) {
        return "/flashinput/galaxyGates.php?userID="+userID+"&sid="+sid+"&action=setupGate&gateID="+gateId;
    }
}
