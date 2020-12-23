package com.srfairyox.Corvus.DarkOrbit.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import java.util.ArrayList;

public class GalaxyGate {
    public enum GalaxyGateEnum {
        None(0, "None", "none"), Alpha(1, "Alpha", "alpha"), Beta(2, "Beta", "beta"), Gamma(3, "Gamma", "gamma"), Delta(4, "Delta", "delta"), Epsilon(5,"Epsilon", "epsilon"), Zeta(6, "Zeta", "zeta"), Kappa(7, "Kappa", "kappa"), Lambda(8, "Lambda", "lambda"), Hades(13, "Hades", "hades"), Kuiper(19, "Kuiper", "streuner");

        private final int gateId;
        private final String displayName;
        private final String displayNameReal;

        GalaxyGateEnum(int gateId, String displayName, String displayNameReal) {
            this.gateId = gateId;
            this.displayName = displayName;
            this.displayNameReal = displayNameReal;
        }

        public int getGateId() {
            return gateId;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDisplayNameReal() {
            return displayNameReal;
        }

        public static String getGateId(int id) {
            for (GalaxyGateEnum res : GalaxyGateEnum.values()) {
                if(id == res.gateId) {
                    return res.getDisplayName();
                }
            }
            return null;
        }
    }

    @XmlRootElement(name = "multiplier")
    public static class Multiplier {
        @XmlAttribute(name = "mode")
        public String Mode;
        @XmlAttribute(name = "value")
        public int Value;
        @XmlAttribute(name = "state")
        public int State;
    }

    @XmlRootElement(name = "multipliers")
    public static class Multipliers {
        @XmlElement(name = "multiplier")
        public ArrayList<Multiplier> multiplierList = new ArrayList<>();
    }

    @XmlRootElement(name = "jumpgate")
    public static class GateData {
        @XmlElement(name = "money")
        public int Money;
        @XmlElement(name = "samples")
        public int Samples;
        @XmlElement(name = "energy_cost")
        public EnergyCostInfo EnergyCost;
        @XmlElement(name = "multipliers")
        public Multipliers MultiplierInfo;
        @XmlElement(name = "gates")
        public GatesInfo Gates;
        @XmlElement(name = "spinOnSale")
        public int spinOnSale;
        @XmlElement(name = "spinSalePercentage")
        public int spinSalePercentage;
        @XmlElement(name = "galaxyGateDay")
        public int galaxyGateDay;
        @XmlElement(name = "bonusRewardsDay")
        public int bonusRewardsDay;
        public boolean loaded() {
            return (this.EnergyCost != null && this.MultiplierInfo != null && this.Gates != null);
        }
    }

    @XmlRootElement(name = "energy_cost")
    public static class EnergyCostInfo {
        @XmlAttribute(name = "mode")
        public String Mode;
        @XmlValue
        public int Text;
    }

    @XmlRootElement(name = "gate")
    public static class Gate {
        @XmlAttribute(name = "total")
        public int total;
        @XmlAttribute(name = "current")
        public int current;
        @XmlAttribute(name = "id")
        public int id;
        @XmlAttribute(name = "prepared")
        public Boolean prepared;
        @XmlAttribute(name = "totalWave")
        public int totalWave;
        @XmlAttribute(name = "currentWave")
        public int currentWave;
        @XmlAttribute(name = "state")
        public String state;
        @XmlAttribute(name = "livesLeft")
        public int livesLeft;
        @XmlAttribute(name = "lifePrice")
        public int lifePrice;
        public boolean isReady() {
            return (this.current == this.total);
        }
    }

    @XmlRootElement(name = "gates")
    public static class GatesInfo {
        @XmlElement(name = "gate")
        public ArrayList<Gate> Gates = new ArrayList<>();

        public Gate Get(GalaxyGateEnum gate) {
            return Gates.stream().filter(x->x.id == gate.gateId).findAny().orElse(null);
        }
        public Gate Alpha() {
            return Gates.stream().filter(x->x.id == GalaxyGateEnum.Alpha.gateId).findAny().orElse(null);
        }
        public Gate Beta() {
            return Gates.stream().filter(x->x.id == GalaxyGateEnum.Beta.gateId).findAny().orElse(null);
        }
        public Gate Gamma() {
            return Gates.stream().filter(x->x.id == GalaxyGateEnum.Gamma.gateId).findAny().orElse(null);
        }
        public Gate Delta() {
            return Gates.stream().filter(x->x.id == GalaxyGateEnum.Delta.gateId).findAny().orElse(null);
        }
        public Gate Epsilon() {
            return Gates.stream().filter(x->x.id == GalaxyGateEnum.Epsilon.gateId).findAny().orElse(null);
        }
        public Gate Zeta() {
            return Gates.stream().filter(x->x.id == GalaxyGateEnum.Zeta.gateId).findAny().orElse(null);
        }
        public Gate Kappa() {
            return Gates.stream().filter(x->x.id == GalaxyGateEnum.Kappa.gateId).findAny().orElse(null);
        }
        public Gate Lambda() {
            return Gates.stream().filter(x->x.id == GalaxyGateEnum.Lambda.gateId).findAny().orElse(null);
        }
        public Gate Kuiper() {
            return Gates.stream().filter(x->x.id == GalaxyGateEnum.Kuiper.gateId).findAny().orElse(null);
        }
        public Gate Hades() {
            return Gates.stream().filter(x->x.id == GalaxyGateEnum.Hades.gateId).findAny().orElse(null);
        }
    }

    @XmlRootElement(name = "jumpgate")
    public static class GateSpinData {
        @XmlElement(name = "money")
        public int Money;
        @XmlElement(name = "samples")
        public int Samples;
        @XmlElement(name = "energy_cost")
        public EnergyCostInfo EnergyCost;
        @XmlElement(name = "items")
        public ItemsInfo Items;
        @XmlElement(name = "multipliers")
        public Multipliers MultiplierInfo;

        @XmlRootElement(name = "energy_cost")
        public static class EnergyCostInfo {
            @XmlAttribute(name = "mode")
            public String Mode;
            @XmlValue
            public int Text;
        }

        @XmlRootElement(name = "item")
        public static class Item {
            @XmlAttribute(name = "type")
            public String Type;
            @XmlAttribute(name = "item_id")
            public int ItemId;
            @XmlAttribute(name = "amount")
            public int Amount;
            @XmlAttribute(name = "current")
            public int Current;
            @XmlAttribute(name = "total")
            public int Total;
            @XmlAttribute(name = "state")
            public String State;
            @XmlAttribute(name = "date")
            public String Date;
            @XmlAttribute(name = "gate_id")
            public int GateId;
            @XmlAttribute(name = "part_id")
            public int PartId;
            @XmlAttribute(name = "multiplier_used")
            public int MultiplierUsed;
            @XmlAttribute(name = "duplicate")
            public boolean Duplicate;

            public String ToString() {
                if (Type.equals("part"))
                {
                    return GalaxyGateEnum.getGateId(GateId)+" part #"+PartId+" ("+Current+"/"+Total+")";
                }

                if (Type.equals("battery"))
                {
                    switch (ItemId)
                    {
                        case 2:
                            return "X2: "+Amount;
                        case 3:
                            return "X3: "+Amount;
                        case 4:
                            return "X4: "+Amount;
                        case 5:
                            return "SAB: "+Amount;
                    }
                }

                if (Type.equals("ore") && ItemId == 4)
                {
                    return "Xenomit: "+Amount;
                }

                if (Type.equals("rocket"))
                {
                    switch (ItemId)
                    {
                        case 3:
                            return "PLT-2021: "+Amount;
                        case 11:
                            return "ACM: "+Amount;
                    }
                }

                if (Type.equals("logfile"))
                {
                    return "Log Disks: "+Amount;
                }

                if (Type.equals("voucher"))
                {
                    return "Repair credits: "+Amount;
                }

                if (Type.equals("nanohull"))
                {
                    return "Nano hull: "+Amount;
                }

                return Type+":"+Amount;
            }
        }
    }

    @XmlRootElement(name = "items")
    public static class ItemsInfo {
        @XmlElement(name = "item")
        public ArrayList<GateSpinData.Item> Items;
        public GateSpinData.Item Item;

        public ArrayList<GateSpinData.Item> GetAllItems() {
            if(Items == null) {
                Items = new ArrayList<>();
            }
            if(Item != null) {
                Items.add(Item);
            }
            return Items;
        }
    }

    public static class GateItemsReceived {
        public static int TotalSpins;
        public static int GateParts;
        public static int X2;
        public static int X3;
        public static int X4;
        public static int SAB;
        public static int PLT2021;
        public static int ACM;
        public static int LogDisks;
        public static int RepairCredits;
        public static int Xenomit;
        public static int NanoHull;

        public void Reset() {
            TotalSpins = 0;
            GateParts = 0;
            X2 = 0;
            X3 = 0;
            X4 = 0;
            SAB = 0;
            PLT2021 = 0;
            ACM = 0;
            LogDisks = 0;
            RepairCredits = 0;
            Xenomit = 0;
            NanoHull = 0;
        }
    }

}
