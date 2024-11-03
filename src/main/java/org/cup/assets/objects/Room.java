package org.cup.assets.objects;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.cup.assets.UI.Floor;
import org.cup.assets.UI.GameButton;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;

public class Room extends Floor {
    private Machine machine = new Machine();
    private DropZone dropZone;

    // An array of the employee with a fixed number of employees (3)
    private Employee[] employees = new Employee[3];

    // The number of the employee
    private int nEmployees;

    // UI
    private JPanel buttonsPanel = new JPanel();
    JButton increaseInventoryBtn = new GameButton("<html><center>" + "INCREASE INVENTORY" + "<br>" + "(1000)" + "</center></html>");
    JButton increaseValueBtn = new GameButton("<html><center>" + "INCREASE PRODUCT VALUE" + "<br>" + "(3000)" + "</center></html>");

    public Room(int width, int height, int x, int y, int layer, Color c) {
        transform.setScale(Vector.ONE);
        transform.setPosition(new Vector(x, y));

        // UI element
        Rectangle rect;
        rect = new Rectangle(width, height, 0, 0, layer, c);
        rect.sr.setPivot(Renderer.BOTTOM_LEFT_PIVOT);
        addChild(rect);
        rect.transform.setParentTransform(transform);
        
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

    private void initUI() {
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));

        buttonsPanel.add(increaseInventoryBtn);
        buttonsPanel.add(increaseValueBtn);
        
        increaseValueBtn.setColors(
            new Color(50, 192, 37),
            new Color(37, 160, 26)
        );
        
        
        increaseInventoryBtn.addActionListener(e -> {
        });

        increaseValueBtn.addActionListener(e -> {
        });
    }

    @Override
    public JPanel getUI() {
        return buttonsPanel;
    }
}
