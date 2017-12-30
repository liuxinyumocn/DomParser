package Main;

import edu.sut.dig.DomParseWindow.DomParserWindow;
import edu.sut.dig.DomParseWindow.DomParserWindowController;
import javafx.application.Application;
import javafx.stage.Stage;

public class mainfunc extends Application {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		DomParserWindow win = new DomParserWindow("DOMÔªËØÉó²éÆ÷ Power By Liuxinyumo.cn");
		win.SetVisible(true);
		win.LoadURL("baidu.com");
	}

}
