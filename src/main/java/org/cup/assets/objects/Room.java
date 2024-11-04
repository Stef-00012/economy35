package org.cup.assets.objects;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.cup.assets.UI.Floor;
import org.cup.assets.UI.GameButton;
import org.cup.assets.logic.Economy;
import org.cup.assets.objects.Machine.MachineUpgrade;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;
import org.cup.engine.core.nodes.components.defaults.Transform;

public class Room extends Floor {
    private Machine machine = new Machine();
    private DropZone dropZone;

    // An array of the employee with a fixed number of employees (3)
    private Employee[] employees = new Employee[3];

    // The number of the employee
    private int nEmployees;

    private MachineUpgrade nextMachineUpgrade;

    // UI
    private JPanel buttonsPanel = new JPanel();
    GameButton upgradeMachineBtn = new GameButton(
            "<html><center>" + "UPGRADE MACHINE" + "<br>" + "(???)" + "</center></html>");
    GameButton hireEmployeeBtn = new GameButton(
            "<html><center>" + "HIRE EMPLOYEE" + "<br>" + "(???)" + "</center></html>");

    public Room(int width, int height, int x, int y, int layer, String spritePath) {
        transform.setScale(Vector.ONE);
        transform.setPosition(new Vector(x, y));

        // UI element
        Transform rendererTransform = new Transform();
        rendererTransform.setScale(new Vector(width, height));
        SpriteRenderer sr = new SpriteRenderer(spritePath, rendererTransform, layer);
        sr.setPivot(Renderer.BOTTOM_LEFT_PIVOT);
        addChild(sr);
        rendererTransform.setParentTransform(transform);

        // set the drop zone
        dropZone = new DropZone(new Vector(width, -30));
        dropZone.transform.setParentTransform(transform);
        addChild(dropZone);

        addChild(machine);
        machine.transform.setParentTransform(transform);

        addEmployee();

        initUI();
    }

    public void addEmployee() {
        if (nEmployees >= employees.length) {
            Debug.warn("MAX EMPLOYEES");
            return;
        }
        Employee e = new Employee(this);
        employees[nEmployees] = e;
        nEmployees++;
        addChild(e);
        e.transform.setParentTransform(transform);
    }

    public Machine getMachine() {
        return machine;
    }

    public DropZone getDropZone() {
        return dropZone;
    }

    public void onUpdate(){
        upgradeMachineBtn.setEnabled(nextMachineUpgrade != null && Economy.getBalance() >= nextMachineUpgrade.cost);
    }

    private void initUI() {
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));

        buttonsPanel.add(upgradeMachineBtn);
        buttonsPanel.add(hireEmployeeBtn);

        hireEmployeeBtn.setColors(
                new Color(50, 192, 37),
                new Color(37, 160, 26));


        updateMachineUpgradeBtn();

        upgradeMachineBtn.addActionListener(e -> {
            machine.upgrade();
            if (!machine.canUpgrade()) {
                nextMachineUpgrade = null;
                upgradeMachineBtn.setText("<html><center> MAX LEVEL </center></html>");
                upgradeMachineBtn.setEnabled(false);
                return;
            }
            updateMachineUpgradeBtn();
        });

        hireEmployeeBtn.addActionListener(e -> {
        });
    }

    @Override
    public JPanel getUI() {
        upgradeMachineBtn.setEnabled(false);

        return buttonsPanel;
    }

    private void updateMachineUpgradeBtn() {
        nextMachineUpgrade = machine.getNextUpgrade();
        MachineUpgrade currentUpgrade = machine.getCurrentUpgrade();

        double successRateIncrease = nextMachineUpgrade.probability - currentUpgrade.probability;
        double doublespeedIncrease = (currentUpgrade.interval - nextMachineUpgrade.interval) / 1000;

        upgradeMachineBtn
                .setText("<html><center>" + "UPGRADE MACHINE" +
                        "<br>($" + nextMachineUpgrade.cost + ")" +
                        (successRateIncrease != 0 ? ("<br>+" + successRateIncrease + " SUCCESS RATE") : "") +
                        (doublespeedIncrease != 0 ? ("<br>+" + doublespeedIncrease + " SPEED") : "") +
                        "</center></html>");
    }
}
