package lightEngine;

import lightEngine.core.GameController;
import lightEngine.core.Setup;

import static lightEngine.core.Setup.*;

public class Main {

    public static void main(String[] args) {

        Setup.setupDefaults();

        loadStandardLight();

        GameController.setLoading(false);

    }

}
