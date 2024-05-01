package Client;

import javax.swing.SwingUtilities;

import Façade.LauncherApp;

public class MainClient {
	
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> {
            LauncherApp app = new LauncherApp();
        });
	

}

}
