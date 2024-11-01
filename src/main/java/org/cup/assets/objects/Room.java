package org.cup.assets.objects;

import java.awt.Color;

import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.Transform;

public class Room extends GameNode {
    private Machine machine = new Machine(this);
    private DropZone dropZone;

    // An array of the employee with a fixed number of employees (3)
    private Employee[] employees = new Employee[3];

    // The number of the employee
    private int nEmployees;

    

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
    }

    public void addEmployee() {
        if (nEmployees >= employees.length){
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
}
