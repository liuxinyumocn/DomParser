package edu.sut.dig.DomParseWindow;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

public class DomParserWindowController {
	@FXML private Label Title;
	@FXML private Label URL;
	@FXML private Label MainDomain;
	@FXML private Label GetParameter;
	@FXML private Label FileSize;
	@FXML private Label Time;
	@FXML private Label NumberOfSublinks;
	
	@FXML private WebView webView;
	@FXML private TreeView treeView;
	//Init
	public void initialize() {
		
	}

	public WebView GetWebViewHandle(){
		return webView;
	}
	
	public TreeView GetTreeViewHandle(){
		return treeView;
	}
	
	//GetSet
	public void SetTitle(String str) {
		Title.setText(str);
	}
	public void SetURL(String str) {
		URL.setText(str);
	}
	public void SetMainDomain(String str) {
		MainDomain.setText(str);
	}
	public void SetGetParameter(String str) {
		GetParameter.setText(str);
	}
	public void SetFileSize(String str) {
		FileSize.setText(str);
	}
	public void SetTime(String str) {
		Time.setText(str);
	}
	public void SetNumberOfSublinks(String str) {
		NumberOfSublinks.setText(str);
	}
	public String GetTitle() {
		return Title.getText();
	}
	public String GetURL() {
		return URL.getText();
	}
	public String MainDomain() {
		return MainDomain.getText();
	}
	public String GetGetParameter() {
		return GetParameter.getText();
	}
	public String GetFileSize() {
		return FileSize.getText();
	}
	public String GetTime() {
		return Time.getText();
	}
	public String GetNumberOfSublinks() {
		return NumberOfSublinks.getText();
	}
}
