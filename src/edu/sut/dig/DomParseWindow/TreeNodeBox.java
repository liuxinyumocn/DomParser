package edu.sut.dig.DomParseWindow;

public class TreeNodeBox {
	protected String SelfHTML = "";
	public TreeNodeBox(String str){
		SelfHTML = str;
	}
	public String toString(){
		return SelfHTML;
	}
}
