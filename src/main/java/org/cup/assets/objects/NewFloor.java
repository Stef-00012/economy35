package org.cup.assets.objects;

import javax.swing.JPanel;

import org.cup.assets.PathHelper;
import org.cup.assets.UI.Floor;
import org.cup.assets.UI.GameButton;
import org.cup.assets.logic.Economy;
import org.cup.assets.scenes.MainScene;
import org.cup.engine.Vector;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;
import org.cup.engine.core.nodes.components.defaults.Transform;

public class NewFloor extends Floor {

    // UI
    private JPanel buttonsPanel = new JPanel();

    private int newFloorCost = 500;
    GameButton newFloorBtn = new GameButton(
            "<html><center>" + "ADD NEW FLOOR" + "<br>($" + newFloorCost + ")</center></html>");

    public NewFloor(int width, int height, int layer) {
        transform.setScale(Vector.ONE);

        Transform rendererTransform = new Transform();
        rendererTransform.setScale(new Vector(width, height));
        SpriteRenderer sr = new SpriteRenderer(PathHelper.sprites + "building\\placeholder.png", rendererTransform, layer);
        sr.setPivot(Renderer.BOTTOM_LEFT_PIVOT);
        rendererTransform.setParentTransform(transform);

        MainScene.addToScrollTransform(transform);
        
        addChild(sr);
        initUI();
    }

    private void initUI() {
        // Listeners
        newFloorBtn.addActionListener(e -> {
            Economy.spendMoney(newFloorCost);
            Building.get().addRoom();
        });

        buttonsPanel.add(newFloorBtn);
    }

    @Override
    public JPanel getUI() {
        return buttonsPanel;
    }

}
