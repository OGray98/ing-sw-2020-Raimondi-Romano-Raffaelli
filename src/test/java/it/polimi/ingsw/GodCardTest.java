package it.polimi.ingsw;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GodCardTest {

    private GodCard CardTestApollo;
    private GodCard CardTestArtemis;
    private GodCard CardTestAthena;
    private GodCard CardTestAtlas;
    private GodCard CardTestDemeter;
    private GodCard CardTestHephaestus;
    private GodCard CardTestMinotaur;
    private GodCard CardTestPan;
    private GodCard CardTestPrometheus;

    @Before
    public void before(){
        CardTestApollo = new GodCard(God.APOLLO,God.APOLLO.GetGodDescription(),PowerType.YOUR_MOVE);
        CardTestArtemis = new GodCard(God.ARTEMIS,God.ARTEMIS.GetGodDescription(),PowerType.YOUR_MOVE);
        CardTestAthena = new GodCard(God.ATHENA,God.ATHENA.GetGodDescription(),PowerType.OPPONENT_TURN);
        CardTestAtlas = new GodCard(God.ATLAS,God.ATLAS.GetGodDescription(),PowerType.YOUR_BUILD);
        CardTestDemeter = new GodCard(God.DEMETER,God.DEMETER.GetGodDescription(),PowerType.YOUR_BUILD);
        CardTestHephaestus = new GodCard(God.HEPHAESTUS,God.HEPHAESTUS.GetGodDescription(),PowerType.YOUR_BUILD);
        CardTestMinotaur = new GodCard(God.MINOTAUR,God.MINOTAUR.GetGodDescription(),PowerType.YOUR_MOVE);
        CardTestPan = new GodCard(God.PAN,God.PAN.GetGodDescription(),PowerType.WIN_CONDITION);
        CardTestPrometheus = new GodCard(God.PROMETHEUS,God.PROMETHEUS.GetGodDescription(),PowerType.YOUR_TURN);
    }

    @Test
    public void isSelected() {
        boolean actual = CardTestApollo.isSelected();
        boolean actualartemis = CardTestArtemis.isSelected();
        boolean actualathena = CardTestAthena.isSelected();
        boolean actualatlas = CardTestAtlas.isSelected();
        boolean actualdemeter = CardTestDemeter.isSelected();
        boolean actualhephastaus = CardTestHephaestus.isSelected();
        boolean actualminotaur = CardTestMinotaur.isSelected();
        boolean actualpan = CardTestPan.isSelected();
        boolean actualprome = CardTestPrometheus.isSelected();

        assertFalse(actual);
        assertFalse(actualartemis);
        assertFalse(actualathena);
        assertFalse(actualatlas);
        assertFalse(actualdemeter);
        assertFalse(actualhephastaus);
        assertFalse(actualminotaur);
        assertFalse(actualpan);
        assertFalse(actualprome);
    }

    @Test
    public void getName() {
        God actual = CardTestApollo.getName();
        God actualartemis = CardTestArtemis.getName();
        God actualathena = CardTestAthena.getName();
        God actualatlas = CardTestAtlas.getName();
        God actualdemeter = CardTestDemeter.getName();
        God actualhephastaus = CardTestHephaestus.getName();
        God actualminotaur = CardTestMinotaur.getName();
        God actualpan = CardTestPan.getName();
        God actualprome = CardTestPrometheus.getName();

        Assert.assertEquals(God.APOLLO,actual);
        Assert.assertEquals(God.ARTEMIS,actualartemis);
        Assert.assertEquals(God.ATHENA,actualathena);
        Assert.assertEquals(God.ATLAS,actualatlas);
        Assert.assertEquals(God.DEMETER,actualdemeter);
        Assert.assertEquals(God.HEPHAESTUS,actualhephastaus);
        Assert.assertEquals(God.MINOTAUR,actualminotaur);
        Assert.assertEquals(God.PAN,actualpan);
        Assert.assertEquals(God.PROMETHEUS,actualprome);
    }

    @Test
    public void getPowerDescription() {
        String actual = CardTestApollo.getPowerDescription();
        String actualartemis = CardTestArtemis.getPowerDescription();
        String actualathena = CardTestAthena.getPowerDescription();
        String actualatlas = CardTestAtlas.getPowerDescription();
        String actualdemeter = CardTestDemeter.getPowerDescription();
        String actualhephastaus = CardTestHephaestus.getPowerDescription();
        String actualminotaur = CardTestMinotaur.getPowerDescription();
        String actualpan = CardTestPan.getPowerDescription();
        String actualprome = CardTestPrometheus.getPowerDescription();

        Assert.assertEquals(God.APOLLO.GetGodDescription(),actual);
        Assert.assertEquals(God.ARTEMIS.GetGodDescription(),actualartemis);
        Assert.assertEquals(God.ATHENA.GetGodDescription(),actualathena);
        Assert.assertEquals(God.ATLAS.GetGodDescription(),actualatlas);
        Assert.assertEquals(God.DEMETER.GetGodDescription(),actualdemeter);
        Assert.assertEquals(God.HEPHAESTUS.GetGodDescription(),actualhephastaus);
        Assert.assertEquals(God.MINOTAUR.GetGodDescription(),actualminotaur);
        Assert.assertEquals(God.PAN.GetGodDescription(),actualpan);
        Assert.assertEquals(God.PROMETHEUS.GetGodDescription(),actualprome);
    }

    @Test
    public void getPowerType() {
        PowerType actual = CardTestApollo.getPowerType();
        PowerType actualartemis = CardTestArtemis.getPowerType();
        PowerType actualathena = CardTestAthena.getPowerType();
        PowerType actualatlas = CardTestAtlas.getPowerType();
        PowerType actualdemeter = CardTestDemeter.getPowerType();
        PowerType actualhephastaus = CardTestHephaestus.getPowerType();
        PowerType actualminotaur = CardTestMinotaur.getPowerType();
        PowerType actualpan = CardTestPan.getPowerType();
        PowerType actualprome = CardTestPrometheus.getPowerType();

        Assert.assertEquals(PowerType.YOUR_MOVE,actual);
        Assert.assertEquals(PowerType.YOUR_MOVE,actualartemis);
        Assert.assertEquals(PowerType.OPPONENT_TURN,actualathena);
        Assert.assertEquals(PowerType.YOUR_BUILD,actualatlas);
        Assert.assertEquals(PowerType.YOUR_BUILD,actualdemeter);
        Assert.assertEquals(PowerType.YOUR_BUILD,actualhephastaus);
        Assert.assertEquals(PowerType.YOUR_MOVE,actualminotaur);
        Assert.assertEquals(PowerType.WIN_CONDITION,actualpan);
        Assert.assertEquals(PowerType.YOUR_TURN,actualprome);

    }
}