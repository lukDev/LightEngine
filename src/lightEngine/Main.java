package lightEngine;

import static lightEngine.core.GameController.setLoading;
import static lightEngine.core.Setup.*;

public class Main {

    public static void main(String[] args) {

        setupDefaults();

        loadScene(
                new int[] {0, 80}, //normal light count, angle (scene 1)
                0, //directional light count (complete scene)
                new int[] {0, 65}, //turnable color light count, angle (scene 3)
                new int[] {0, 90}, // color light count, angle (scene 2)
                new int[] {1, 70}, //moveable light count (scene 2)
                0, //sphere count in scene 1
                5, //sphere count in scene 2
                0, //monkey count in scene 1
                5, //monkey count in scene 2
                0 //object(sphere or monkey) count in scene 3
        );

        setLoading(false);

    }

}
