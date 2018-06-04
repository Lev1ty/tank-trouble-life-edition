import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TankTroubleGUIController {
    /**
     * global logger
     */
    private final Logger log = LoggerFactory.getLogger(getClass());

    @FXML
    private void foo() {
        log.info("mouse was pressed");
    }
}
