package com.srfairyox.Corvus.DarkOrbit;

import com.srfairyox.Corvus.DarkOrbit.Data.AccountData;
import com.srfairyox.Corvus.DarkOrbit.Data.GalaxyGate;
import com.srfairyox.Corvus.Http.DOHttpClient;
import com.srfairyox.Corvus.Http.Http;
import com.srfairyox.Corvus.Http.Urls;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DOAccount {

    public AccountData accountData;
    public DOHttpClient doHttpClient;
    public GalaxyGate.GateData gateData;

    public DOAccount(String server, String sessionID) {
        accountData = new AccountData();
        doHttpClient = new DOHttpClient();
        accountData.setServer(server);
        accountData.setSessionId(sessionID);
        doHttpClient.initialize();
    }

    public synchronized void ReadGatesAsync() {
        try {
            String url = Urls.getBaseUrl(accountData.getServer())+Urls.getGateInfo(accountData.getUserId(), accountData.getSessionId());
            String data = Http.readResponse(doHttpClient.getConnection2(url));
            StringReader reader = new StringReader(data);
            JAXBContext jaxbContext = JAXBContext.newInstance(GalaxyGate.GateData.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            gateData = (GalaxyGate.GateData) unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            e.printStackTrace();
            gateData = null;
        }
    }

    public synchronized Boolean CheckSessionValid() {
        try {
            String rawResponse = Http.readResponse(doHttpClient.getConnection(Urls.getBaseUrl(accountData.getServer())+Urls.getInternalStart(), accountData.getSessionId()));
            if(rawResponse.contains("dosid")) {
                String regexUsername = "alt=\"([^\"]+)\"\\s+id=\"pilotAvatar\" ()";
                Pattern patternUsername = Pattern.compile(regexUsername);
                Matcher username = patternUsername.matcher(rawResponse);
                if(username.find()) {
                    accountData.setUsername(username.group(1));
                }

                String regexUserId = "\"uid\":(.*?),()";
                Pattern patternUserId = Pattern.compile(regexUserId);
                Matcher userID = patternUserId.matcher(rawResponse);
                if(userID.find()) {
                    accountData.setUserId(userID.group(1));
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean IsMultiplierAvailable(GalaxyGate.GalaxyGateEnum gate) {
        String gateName = gate.getDisplayNameReal().toLowerCase();
        GalaxyGate.Multiplier data = gateData.MultiplierInfo.multiplierList.stream().filter(x->x.Mode.toLowerCase().equals(gateName)).findFirst().orElse(null);
        if(data == null) {
            return false;
        }
        return data.Value >= 0;
    }

    public synchronized GalaxyGate.GateSpinData SpinGateAsync(GalaxyGate.GalaxyGateEnum gate, boolean useMultiplier) {
        String spinUrl = "";
        if(useMultiplier && IsMultiplierAvailable(gate)) {
            spinUrl = Urls.getBaseUrl(accountData.getServer())+Urls.getSpinGateMultiplier(accountData.getUserId(), accountData.getSessionId(), gate.getGateId(), gate.getDisplayNameReal().toLowerCase());
            if(gateData.Samples>0) {
                spinUrl = Urls.getBaseUrl(accountData.getServer())+Urls.getSpinGateSampleMultiplier(accountData.getUserId(), accountData.getSessionId(), gate.getGateId(), gate.getDisplayNameReal().toLowerCase());
            }
        } else {
            spinUrl = Urls.getBaseUrl(accountData.getServer())+Urls.getSpinGate(accountData.getUserId(), accountData.getSessionId(), gate.getGateId(), gate.getDisplayNameReal().toLowerCase());
            if(gateData.Samples>0) {
                spinUrl = Urls.getBaseUrl(accountData.getServer())+Urls.getSpinGateSample(accountData.getUserId(), accountData.getSessionId(), gate.getGateId(), gate.getDisplayNameReal().toLowerCase());
            }
        }

        String resultString = "";
        try {
            GalaxyGate.GateSpinData result;
            resultString  = Http.readResponse(doHttpClient.getConnection2(spinUrl));
            StringReader reader = new StringReader(resultString);
            JAXBContext jaxbContext = JAXBContext.newInstance(GalaxyGate.GateSpinData.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            result = (GalaxyGate.GateSpinData) unmarshaller.unmarshal(reader);

            EvaluateSpin(result);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void EvaluateSpin(GalaxyGate.GateSpinData spinData) {
        try {
            GalaxyGate.GateItemsReceived.TotalSpins++;
            gateData.Samples = spinData.Samples;
            gateData.EnergyCost.Text = spinData.EnergyCost.Text;
            gateData.Money = spinData.Money;
            gateData.MultiplierInfo = spinData.MultiplierInfo;

            for (GalaxyGate.GateSpinData.Item spinItem:spinData.Items.GetAllItems()) {
                if(spinItem.Type.equals("part") && !spinItem.Duplicate) {
                    GalaxyGate.GateItemsReceived.GateParts++;
                    GalaxyGate.Gate gate = gateData.Gates.Gates.stream().filter(x->x.id == spinItem.GateId).findAny().orElse(null);
                    if(gate != null) {
                        gate.total = spinItem.Total;
                        gate.current = spinItem.Current;
                    }
                }

                if(spinItem.Type.equals("battery")) {
                    switch (spinItem.ItemId) {
                        case 2:
                            GalaxyGate.GateItemsReceived.X2 += spinItem.Amount;
                            break;
                        case 3:
                            GalaxyGate.GateItemsReceived.X3 += spinItem.Amount;
                            break;
                        case 4:
                            GalaxyGate.GateItemsReceived.X4 += spinItem.Amount;
                            break;
                        case 5:
                            GalaxyGate.GateItemsReceived.SAB += spinItem.Amount;
                            break;
                    }
                }

                if(spinItem.Type.equals("ore") && spinItem.ItemId == 4) {
                    GalaxyGate.GateItemsReceived.Xenomit += spinItem.Amount;
                }

                if (spinItem.Type.equals("rocket"))
                {
                    switch (spinItem.ItemId)
                    {
                        case 3:
                            GalaxyGate.GateItemsReceived.PLT2021 += spinItem.Amount;
                            break;
                        case 11:
                            GalaxyGate.GateItemsReceived.ACM += spinItem.Amount;
                            break;
                    }
                }

                if (spinItem.Type.equals("logfile"))
                {
                    GalaxyGate.GateItemsReceived.LogDisks += spinItem.Amount;
                }

                if (spinItem.Type.equals("voucher"))
                {
                    GalaxyGate.GateItemsReceived.RepairCredits += spinItem.Amount;
                }

                if (spinItem.Type.equals("nanohull"))
                {
                    GalaxyGate.GateItemsReceived.NanoHull += spinItem.Amount;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean PlaceGateAsync(GalaxyGate.GalaxyGateEnum gate) {
        try {
            String url = Urls.getBaseUrl(accountData.getServer())+Urls.getPlaceGate(accountData.getUserId(), accountData.getSessionId(), gate.getGateId());
            String placed = Http.readResponse(doHttpClient.getConnection2(url));
            return !placed.contains("not_enough_parts");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
