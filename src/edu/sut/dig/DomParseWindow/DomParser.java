package edu.sut.dig.DomParseWindow;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javafx.scene.web.WebEngine;

//Dom解析器

public class DomParser {
	private WebEngine webEngine = null;
	
	private List<NodeBox> Nodes = null;
	
	public DomParser(WebEngine engine){
		webEngine = engine;
		Nodes = new LinkedList<>();
		Each();
	}
	
	public List<NodeBox> GetDOMList(){
		return Nodes;
	}
	
	public String toString(){
		String Info = "";
		for(int i = 0;i<Nodes.size();i++){
			Info += Nodes.get(i);
			Info += "\n";
		}
		return Info;
	}
	//广度优先遍历 (层次遍历法)
	private void Each(){
		Document doc = (Document)webEngine.executeScript("document");
		Queue<Node> queEle = new LinkedList<>();
		Node eTemp = doc.getDocumentElement();
		queEle.add(eTemp);
		do{
			eTemp = queEle.poll();
			NodeBox dTemp = null;
			dTemp =new NodeBox(eTemp);
			Nodes.add(dTemp);
			if(eTemp instanceof Element){
				Node ndChild = eTemp.getFirstChild();
				do{
					if(ndChild != null){
						if(!(ndChild instanceof Element)){
							if(ndChild.getNodeValue().trim().length() == 0)
								continue;
						}
						queEle.add(ndChild);
					}
				}while(ndChild != null &&(ndChild = ndChild.getNextSibling())!=null);
			}
		}while(!queEle.isEmpty());
	}
}
