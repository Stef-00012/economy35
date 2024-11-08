package org.cup.assets.objects;

import javax.swing.JPanel;

import org.cup.assets.PathHelper;
import org.cup.assets.UI.Floor;
import org.cup.assets.UI.GameButton;
import org.cup.assets.logic.Economy;
import org.cup.assets.scenes.MainScene;
import org.cup.engine.Vector;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.Animation;
import org.cup.engine.core.nodes.components.defaults.Animator;
import org.cup.engine.core.nodes.components.defaults.Transform;

public class NewFloor extends Floor {
    private Animator animator;

    // UI
    private JPanel buttonsPanel = new JPanel();

    private int newFloorCost = 500;
    GameButton newFloorBtn = new GameButton(
            "<html><center>" + "ADD NEW FLOOR" + "<br>($" + newFloorCost + ")</center></html>");

    public NewFloor(int width, int height, int layer) {
        transform.setScale(Vector.ONE);

        Transform rendererTransform = new Transform();
        rendererTransform.setScale(new Vector(width, height));

        animator = new Animator(rendererTransform, layer);
        animator.addAnimation(
                "idle",
                new Animation(PathHelper.getFilePaths(PathHelper.sprites + "\\building\\floor-under-construction")));

        animator.setPivot(Renderer.BOTTOM_LEFT_PIVOT);
        rendererTransform.setParentTransform(transform);

        MainScene.addToScrollTransform(transform);

        addChild(animator);
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