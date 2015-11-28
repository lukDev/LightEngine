package lightEngine;

import lightEngine.core.GameController;
import lightEngine.core.Setup;

import static lightEngine.core.Setup.*;

public class Main {

    public static void main(String[] args) {

        Setup.setupDefaults();

        loadScene(
                new int[] {1, 40}, //normal light count, angle
                0, //directional light count
                3, //turnable color light count
                0, // color light count
                7, //sphere count in scene 1
                0, //sphere count in scene 2
                5, //monkey count in scene 1
                0 //monkey count in scene 2
        );

        GameController.setLoading(false);

    }

}
