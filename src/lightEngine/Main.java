package lightEngine;

import static lightEngine.core.GameController.setLoading;
import static lightEngine.core.Setup.*;

public class Main {

    public static void main(String[] args) {

        setupDefaults();

        loadScene(
                new int[] {2, 40}, //normal light count, angle
                0, //directional light count
                3, //turnable color light count (scene 3)
                new int[] {3, 50}, // color light count, angle
                5, //sphere count in scene 1
                5, //sphere count in scene 2
                3, //monkey count in scene 1
                3, //monkey count in scene 2
                4 //object(sphere or monkey) count in scene 3
        );

        setLoading(false);

    }

}
