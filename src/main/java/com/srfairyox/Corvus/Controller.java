package com.srfairyox.Corvus;

import com.srfairyox.Corvus.Config.ConfigLoader;
import com.srfairyox.Corvus.DarkOrbit.DOAccount;
import com.srfairyox.Corvus.DarkOrbit.Data.GalaxyGate;
import com.srfairyox.Corvus.DarkOrbit.Data.GalaxyGate.GalaxyGateEnum;
import com.srfairyox.Corvus.Http.Urls;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.NumberFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@SuppressWarnings("unchecked")
public class Controller {
    public Button btnStop;
    public CheckBox chkEnableLog;
    public CheckBox chkEnableDebugCMD;
    private DOAccount account;
    private boolean loginAccount = false;
    private boolean useMultiplier = false;
    private boolean running = false;

    //App
    public Button btnOpenBackPage;
    public Label lblLastStatus;

    //Tab Login
    public Tab tabLogin;
    public Label lblErrorNotificationLogin;
    ///Group of SessionID Login
    public TitledPane tpSessionIDLogin;
    public TextField txtFServer;
    public TextField txtFSID;
    public Button btnLogin;
    public Button btnClearFields;
    ///Group of General Settings
    public TitledPane tpGeneralSettings;

    //Tab Galaxy Gates
    public Tab tabGalaxyGates;
    ///Group of Materializer
    public TitledPane tpMaterializer;
    public ComboBox cBoxGates;
    public Button btnStart;
    ///Table of Gate info
    public TableView<Gate> tableViewGateInfo;
    public TableColumn nameColumn;
    public TableColumn partsColumn;
    public TableColumn readyColumn;
    public TableColumn onMapColumn;
    public TableColumn gatesBuiltColumn;
    ///Group of Account
    public TitledPane tpAccount;
    public Label lblUridium;
    public Label lblExtraEnergy;
    public Label lblSpinCost;
    ///Group of Statistics
    public TitledPane tpStatistics;
    public Label lblSpinsUsed;
    public Label lblReceivedX2;
    public Label lblReceivedX3;
    public Label lblReceivedX4;
    public Label lblReceivedSAB;
    public Label lblReceivedPLT2021;
    public Label lblReceivedACM;
    public Label lblReceivedNanoHull;
    public Label lblReceivedLogDisks;
    public Label lblReceivedParts;
    public Label lblReceivedRepairCredits;
    public Label lblReceivedXenomit;
    ///Group of Settings Gates
    public TitledPane tpSettingsGates;
    public CheckBox chkBoxPlaceGateOnMap;
    public CheckBox chkBoxSpinOnlyEE;
    public TextField txtMinUridium;
    public TextField txtMaxSpinCost;

    //Tab Log
    public Tab tabLog;
    public TextArea txtAreaLog;

    //Variable initial
    private int GatesBuiltAlpha = 0;
    private int GatesBuiltBeta = 0;
    private int GatesBuiltGamma = 0;
    private int GatesBuiltDelta = 0;
    private int GatesBuiltEpsilon = 0;
    private int GatesBuiltZeta = 0;
    private int GatesBuiltKappa = 0;
    private int GatesBuiltLambda = 0;
    private int GatesBuiltHades = 0;
    private int GatesBuiltKuiper = 0;

    @FXML
    public void initialize() {
        Log("Corvus v0.1 started - Made by 'Heaven. and SrFairyox");
        tpSessionIDLogin.setCollapsible(false);
        tpGeneralSettings.setCollapsible(false);

        tpMaterializer.setCollapsible(false);
        tpAccount.setCollapsible(false);
        tpStatistics.setCollapsible(false);
        tpSettingsGates.setCollapsible(false);

        btnOpenBackPage.setDisable(true);
        btnStart.setDisable(true);
        btnStop.setDisable(true);

        ObservableList<String> gates = FXCollections.observableArrayList("ABG","Delta","Epsilon","Zeta","Kappa","Labda","Hades","Kuiper");

        cBoxGates.setItems(gates);
        cBoxGates.getSelectionModel().select(0);

        txtFServer.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                txtFServer.setStyle("-fx-background-color: rgba(215, 44, 44, 0.4)");
            } else {
                txtFServer.setStyle("");
            }
        });

        txtFSID.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                txtFSID.setStyle("-fx-background-color: rgba(215, 44, 44, 0.4)");
            } else {
                txtFSID.setStyle("");
            }
        });

        txtMinUridium.textProperty().addListener((observable, oldValue, newValue) ->  {
            if(newValue.isEmpty()) {
                txtMinUridium.setText(oldValue);
            }
            if(!newValue.matches("\\d*")) {
                txtMinUridium.setText(newValue.replaceAll("[^\\d]", oldValue));
            }
        });

        txtMaxSpinCost.textProperty().addListener((observable, oldValue, newValue) ->  {
            if(newValue.isEmpty()) {
                txtMaxSpinCost.setText(oldValue);
            }
            if(!newValue.matches("\\d*")) {
                txtMaxSpinCost.setText(newValue.replaceAll("[^\\d]", oldValue));
            }

        });

        tabLog.setDisable(!chkEnableLog.isSelected());

        chkEnableLog.selectedProperty().addListener((observable, oldValue, newValue) -> {
            tabLog.setDisable(!newValue);
        });


        if(new File("config.json").exists()) {
            String configLoader = new ConfigLoader().load();
            JSONObject obj = new JSONObject(configLoader);

            chkEnableLog.setSelected(obj.getBoolean("enableLog"));
            chkEnableDebugCMD.setSelected(obj.getBoolean("enableDebugConsole"));
            chkBoxPlaceGateOnMap.setSelected(obj.getBoolean("enableDebugConsole"));
            chkBoxSpinOnlyEE.setSelected(obj.getBoolean("spinOnlyEE"));
            cBoxGates.getSelectionModel().select(obj.getInt("gateSelectedIndex"));
            txtMinUridium.setText(String.valueOf(obj.getInt("minUridium")));
            txtMaxSpinCost.setText(String.valueOf(obj.getInt("maxSpinCost")));
        }
    }

    public void btnLoginOnClick(ActionEvent actionEvent) {
        //Disable gui
        if(txtFServer.getText().equals("") || txtFSID.getText().equals("")) {
            if(txtFServer.getText().equals("") && txtFSID.getText().equals("")){
                lblErrorNotificationLogin.setText("Error: Field server and sid empty");
                txtFServer.setStyle("-fx-background-color: rgba(215, 44, 44, 0.4)");
                txtFSID.setStyle("-fx-background-color: rgba(215, 44, 44, 0.4)");
            }
            else if(txtFServer.getText().equals("")){
                lblErrorNotificationLogin.setText("Error: Field server empty");
                txtFServer.setStyle("-fx-background-color: rgba(215, 44, 44, 0.4)");
            }
            else if(txtFSID.getText().equals("")){
                lblErrorNotificationLogin.setText("Error: Field sid empty");
                txtFSID.setStyle("-fx-background-color: rgba(215, 44, 44, 0.4)");
            }
        } else {
            lblErrorNotificationLogin.setText("");

            new Thread(() -> {
                Log("Performing sessionId...");
                account = new DOAccount(txtFServer.getText(), txtFSID.getText());
                boolean sessionValid = account.CheckSessionValid();

                if(!sessionValid) {
                    Log("There was a problem performing the login! Please check your input data!");
                    return;
                } else {
                    loginAccount = true;
                    btnOpenBackPage.setDisable(false);
                    btnStart.setDisable(false);
                    btnStop.setDisable(true);
                }

                Log("Login success!");
                InitializeGuiAsync();
            }).start();


            //Enable GUI
        }
    }

    public void btnClearFieldsOnClick(ActionEvent actionEvent) {
        System.out.println("clear");
        txtFServer.setText("");
        txtFServer.setStyle("");
        txtFSID.setText("");
        txtFSID.setStyle("");
    }

    public void btnOpenBackPageOnClick(ActionEvent actionEvent) {
        System.out.println("backpage");
        if(account.accountData.getSessionId()!=null) {
            try {
                Desktop.getDesktop().browse(URI.create(Urls.getBaseUrl(account.accountData.getServer())+Urls.getOpenBackPage(account.accountData.getSessionId())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnStartOnClick(ActionEvent actionEvent) {
        btnStart.setDisable(true);
        btnStop.setDisable(false);

        running = true;

        new Thread(() -> {
            while (running) {
                DoWork();
            }
        }).start();

    }

    private synchronized void InitializeGuiAsync() {
        Platform.runLater(() -> {
            Log("Initializing gui...");
            Log("Reading Galaxy Gates...");
        });
        account.ReadGatesAsync();
        Platform.runLater(() -> {
            UpdateGui();
            Log("Initialization finished!");
        });
    }

    private void UpdateGui() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        Log("Updating Gate gui...");
        UpdateGateTable(account.gateData);
        Platform.runLater(() -> {
            lblUridium.setText("Uridium: "+ numberFormat.format(account.gateData.Money));
            lblExtraEnergy.setText("Extra Energy: "+ numberFormat.format(account.gateData.Samples));
            lblSpinCost.setText("Spin Cost: "+ numberFormat.format(account.gateData.EnergyCost.Text));

            lblSpinsUsed.setText("Spin used: "+ numberFormat.format(GalaxyGate.GateItemsReceived.TotalSpins));
            lblReceivedX2.setText("X2: "+ numberFormat.format(GalaxyGate.GateItemsReceived.X2));
            lblReceivedX3.setText("X3: "+ numberFormat.format(GalaxyGate.GateItemsReceived.X3));
            lblReceivedX4.setText("X4: "+ numberFormat.format(GalaxyGate.GateItemsReceived.X4));
            lblReceivedSAB.setText("SAB: "+ numberFormat.format(GalaxyGate.GateItemsReceived.SAB));
            lblReceivedPLT2021.setText("PLT-2021: "+ numberFormat.format(GalaxyGate.GateItemsReceived.PLT2021));
            lblReceivedACM.setText("ACM: "+ numberFormat.format(GalaxyGate.GateItemsReceived.ACM));
            lblReceivedNanoHull.setText("Nano hull: "+ numberFormat.format(GalaxyGate.GateItemsReceived.NanoHull));
            lblReceivedLogDisks.setText("Log disks: "+ numberFormat.format(GalaxyGate.GateItemsReceived.LogDisks));
            lblReceivedParts.setText("Parts: "+ numberFormat.format(GalaxyGate.GateItemsReceived.GateParts));
            lblReceivedRepairCredits.setText("Repair credits: "+ numberFormat.format(GalaxyGate.GateItemsReceived.RepairCredits));
            lblReceivedXenomit.setText("Xenomit: "+ numberFormat.format(GalaxyGate.GateItemsReceived.Xenomit));
        });
    }

    private synchronized void ExecuteSpinAsync() {
        UpdateGui();
        GalaxyGate.Gate currentGate = account.gateData.Gates.Get(GetSelectedGate());
        if(cBoxGates.getSelectionModel().getSelectedIndex() == 0) {
            GalaxyGate.Gate currentGateA = account.gateData.Gates.Get(GalaxyGateEnum.Alpha);
            GalaxyGate.Gate currentGateB = account.gateData.Gates.Get(GalaxyGateEnum.Beta);
            GalaxyGate.Gate currentGateG = account.gateData.Gates.Get(GalaxyGateEnum.Gamma);
            if(chkBoxPlaceGateOnMap.isSelected()) {
                if((currentGateA.prepared && currentGateA.isReady()) || (currentGateB.prepared && currentGateB.isReady()) || (currentGateG.prepared && currentGateG.isReady())) {
                    Log("Stopping gate mode. Can not get more parts.");
                    return;
                }
                if(currentGateA.isReady() && !currentGateA.prepared){
                    //place
                    PlaceGateAsync(GalaxyGateEnum.Alpha);
                }
                if(currentGateB.isReady() && !currentGateB.prepared){
                    //place
                    PlaceGateAsync(GalaxyGateEnum.Beta);
                }
                if(currentGateG.isReady() && !currentGateG.prepared){
                    //place
                    PlaceGateAsync(GalaxyGateEnum.Gamma);
                }
            } else {
                if (currentGateA.isReady() || currentGateB.isReady() || currentGateG.isReady())
                {
                    Log("Stopping gate mode. Can not get more parts.");
                    return;
                }
            }
        } else {
            if(chkBoxPlaceGateOnMap.isSelected()) {
                if(currentGate.prepared && currentGate.isReady()) {
                    Log("Stopping gate mode. Can not get more parts.");
                    return;
                }
                if(currentGate.isReady() && !currentGate.prepared) {
                    //place
                    PlaceGateAsync(GetSelectedGate());
                }
            } else {
                if(currentGate.isReady()) {
                    Log("Stopping gate mode. Can not get more parts.");
                    return;
                }
            }
        }

        if(account.gateData.EnergyCost.Text > account.gateData.Money && account.gateData.Samples <= 0) {
            Log("Stopping gate mode. No Uridium/EE left");
            return;
        }
        if(account.gateData.Money <= Integer.parseInt(txtMinUridium.getText())) {
            Log("Stopping gate mode. Minimum Uridium reached");
            return;
        }
        if(chkBoxSpinOnlyEE.isSelected() && account.gateData.Samples <= 0) {
            Log("Stopping gate mode. No EE left");
            return;
        }
        if(!(account.gateData.EnergyCost.Text <= Integer.parseInt(txtMaxSpinCost.getText()))) {
            Log("Stopping gate mode. Max Spin Cost");
            return;
        }

        if(cBoxGates.getSelectionModel().getSelectedIndex()==0) {
            Log("Spinning ABG...");
        } else {
            Log("Spinning "+GetSelectedGate().getDisplayName());
        }

        GalaxyGate.Multipliers multiplierinfo = account.gateData.MultiplierInfo;
        for (GalaxyGate.Multiplier mi:multiplierinfo.multiplierList) {
            if(mi.Mode.contains(GetSelectedGate().getDisplayNameReal().toLowerCase())) {
                if(cBoxGates.getSelectionModel().getSelectedIndex() == 0) {
                    if(use3MultiplierABG(account.gateData.Gates.Get(GalaxyGateEnum.Alpha).current, account.gateData.Gates.Get(GalaxyGateEnum.Alpha).total, account.gateData.Gates.Get(GalaxyGateEnum.Beta).current,account.gateData.Gates.Get(GalaxyGateEnum.Beta).total,account.gateData.Gates.Get(GalaxyGateEnum.Gamma).current,account.gateData.Gates.Get(GalaxyGateEnum.Gamma).total)) {
                        useMultiplier = 2 <= mi.Value;
                    } else {
                        useMultiplier = 1 <= mi.Value;
                    }
                } else {
                    if ((account.gateData.Gates.Get(GetSelectedGate()).total - account.gateData.Gates.Get(GetSelectedGate()).current) == 3) {
                        useMultiplier = (2 <= mi.Value);
                    } else {
                        useMultiplier = (1 <= mi.Value);
                    }
                }
            }
        }
        System.out.println("useMultiplier: "+useMultiplier);

        //spin
        GalaxyGate.GateSpinData spin = account.SpinGateAsync(GetSelectedGate(), useMultiplier);

        for (GalaxyGate.GateSpinData.Item allItem:spin.Items.GetAllItems()) {
            if(allItem.Duplicate) {
                Log("Received duplicate gate part > received multiplier");
            } else {
                Log("Received "+allItem.ToString());
            }
        }
        UpdateGui();
    }

    private synchronized void DoWork() {
        try {
            Log("Reading Galaxy Gates...");
            account.ReadGatesAsync();
            UpdateGui();
                try {
                    ExecuteSpinAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void PlaceGateAsync(GalaxyGate.GalaxyGateEnum gate) {
        Log("Gate "+gate.getDisplayName()+" is ready...");
        Log("Placing "+gate.getDisplayName());
        account.PlaceGateAsync(gate);
        addGatesBuilt(gate);
        Log("Reading Galaxy Gates...");
        account.ReadGatesAsync();
        UpdateGui();
    }

    //Update gates in table
    public void UpdateGateTable(GalaxyGate.GateData value) {
        final ObservableList<Gate> data = FXCollections.observableArrayList(new Gate("Alpha", value.Gates.Alpha().current+"/"+value.Gates.Alpha().total, value.Gates.Alpha().current==value.Gates.Alpha().total, value.Gates.Alpha().prepared, GatesBuiltAlpha),
                new Gate("Beta", value.Gates.Beta().current+"/"+value.Gates.Beta().total, value.Gates.Beta().current==value.Gates.Beta().total, value.Gates.Beta().prepared, GatesBuiltBeta),
                new Gate("Gamma", value.Gates.Gamma().current+"/"+value.Gates.Gamma().total, value.Gates.Gamma().current==value.Gates.Gamma().total, value.Gates.Gamma().prepared, GatesBuiltGamma),
                new Gate("Delta", value.Gates.Delta().current+"/"+value.Gates.Delta().total, value.Gates.Delta().current==value.Gates.Delta().total, value.Gates.Delta().prepared, GatesBuiltDelta),
                new Gate("Epsilon", value.Gates.Epsilon().current+"/"+value.Gates.Epsilon().total, value.Gates.Epsilon().current==value.Gates.Epsilon().total, value.Gates.Epsilon().prepared, GatesBuiltEpsilon),
                new Gate("Zeta", value.Gates.Zeta().current+"/"+value.Gates.Zeta().total, value.Gates.Zeta().current==value.Gates.Zeta().total, value.Gates.Zeta().prepared, GatesBuiltZeta),
                new Gate("Kappa", value.Gates.Kappa().current+"/"+value.Gates.Kappa().total, value.Gates.Kappa().current==value.Gates.Kappa().total, value.Gates.Kappa().prepared, GatesBuiltKappa),
                new Gate("Lambda", value.Gates.Lambda().current+"/"+value.Gates.Lambda().total, value.Gates.Lambda().current==value.Gates.Lambda().total, value.Gates.Lambda().prepared, GatesBuiltLambda),
                new Gate("Hades", value.Gates.Hades().current+"/"+value.Gates.Hades().total, value.Gates.Hades().current==value.Gates.Hades().total, value.Gates.Hades().prepared, GatesBuiltHades),
                new Gate("Kuiper", value.Gates.Kuiper().current+"/"+value.Gates.Kuiper().total, value.Gates.Kuiper().current==value.Gates.Kuiper().total, value.Gates.Kuiper().prepared, GatesBuiltKuiper)
        );

        nameColumn.setCellValueFactory(new PropertyValueFactory<Gate, String>("name"));
        partsColumn.setCellValueFactory(new PropertyValueFactory<Gate, String>("parts"));
        readyColumn.setCellValueFactory(new PropertyValueFactory<Gate, Boolean>("ready"));
        readyColumn.setCellFactory(column -> new CheckBoxTableCell());
        onMapColumn.setCellValueFactory(new PropertyValueFactory<Gate, Boolean>("onMap"));
        onMapColumn.setCellFactory(column -> new CheckBoxTableCell());
        gatesBuiltColumn.setCellValueFactory(new PropertyValueFactory<Gate, Integer>("gatesBuilt"));

        tableViewGateInfo.setItems(data);
    }


    //Get Gate Selected from ComboBox
    private GalaxyGateEnum GetSelectedGate() {
        switch (cBoxGates.getSelectionModel().getSelectedIndex()) {
            case 0:
                return GalaxyGateEnum.Alpha;
            case 1:
                return GalaxyGateEnum.Delta;
            case 2:
                return GalaxyGateEnum.Epsilon;
            case 3:
                return GalaxyGateEnum.Zeta;
            case 4:
                return GalaxyGateEnum.Kappa;
            case 5:
                return GalaxyGateEnum.Lambda;
            case 6:
                return GalaxyGateEnum.Hades;
            case 7:
                return GalaxyGateEnum.Kuiper;
        }
        return GalaxyGateEnum.None;
    }

    public void Log(String text) {
        Platform.runLater(() -> {
            lblLastStatus.setText("Last status: " + text);
            if(chkEnableLog.isSelected()) {
                if(txtAreaLog.getLength()>20000)
                    txtAreaLog.appendText("");
                txtAreaLog.appendText("["+ DateTimeFormatter.ofPattern("HH:mm:ss").format(OffsetDateTime.now()) +"] " +text+"\n");
            }
        });
        if(chkEnableDebugCMD.isSelected()) {
            System.out.println(text);
        }
    }

    //Add gates built
    public void addGatesBuilt(GalaxyGate.GalaxyGateEnum gate)
    {
        switch (gate.getGateId())
        {
            case 1:
                GatesBuiltAlpha++;
                break;
            case 2:
                GatesBuiltBeta++;
                break;
            case 3:
                GatesBuiltGamma++;
                break;
            case 4:
                GatesBuiltDelta++;
                break;
            case 5:
                GatesBuiltEpsilon++;
                break;
            case 6:
                GatesBuiltZeta++;
                break;
            case 7:
                GatesBuiltKappa++;
                break;
            case 8:
                GatesBuiltLambda++;
                break;
            case 13:
                GatesBuiltHades++;
                break;
            case 19:
                GatesBuiltKuiper++;
                break;
        }
    }

    private Boolean use3MultiplierABG(int a_curr, int a_max, int b_curr, int b_max, int g_curr, int g_max)
    {
        if (a_max - a_curr == 3) return b_curr <= b_max * 0.2 && g_curr <= g_max * 0.2;
        if (b_max - b_curr == 3) return a_curr <= a_max * 0.2 && g_curr <= g_max * 0.2;
        if (g_max - b_curr == 3) return a_curr <= a_max * 0.2 && b_curr <= b_max * 0.2;
        return false;
    }


    public void btnStopOnClick(ActionEvent actionEvent) {
        running = false;
        btnStart.setDisable(false);
        btnStop.setDisable(true);
    }
}
