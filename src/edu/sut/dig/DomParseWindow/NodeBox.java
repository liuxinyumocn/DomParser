package edu.sut.dig.DomParseWindow;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import javafx.scene.control.TreeItem;

//描述节点特性
public class NodeBox extends TreeNodeBox{
	protected Node node = null;
	
	protected String TagName = "";
	
	protected Node ParentNode = null;
	
	protected float Width = 0;
	protected float Height = 0;
	protected float x = 0;
	protected float y = 0;
	
	protected float MarginTop = 0;
	protected float MarginRight = 0;
	protected float MarginBottom = 0;
	protected float MarginLeft = 0;
		
	protected float PaddingTop = 0;
	protected float PaddingRight = 0;
	protected float PaddingBottom = 0;
	protected float PaddingLeft = 0;
		
	protected String BgColor = "";
		
	protected float FontSize = 0;
	protected String FontColor = "";
	
	protected NamedNodeMap Attr = null;
	protected String AttrText = "";
	
	protected boolean IsLink = false;
		
	protected TreeItem<TreeNodeBox> tail = null;
	
	public NodeBox (Node node){
		super("undefined");
		if(node == null){
			TagName = "Root";
			return;
		}
		this.node = node;
		if(node instanceof Element){
			//抽取HTML信息
			TagName = node.getNodeName();
			//GetAttrText
			Attr = node.getAttributes();
			StringBuilder str = new StringBuilder() ;
			for(int i = 0 ; i<Attr.getLength();i++){
				str.append(" ");
				str.append(Attr.item(i).getNodeName());
				str.append("=\"");
				str.append(Attr.item(i).getNodeValue());
				str.append("\"");
			}
			AttrText = str.toString();
			//添加尾部节点数据
			tail = new TreeItem<TreeNodeBox>(new TreeNodeBox("</"+TagName.toLowerCase()+">"));
		}else{
			TagName = node.getNodeValue();
		}
		ParentNode = node.getParentNode();
		
		//SelfHTML = "<"+TagName.toLowerCase()+">...<"+TagName.toLowerCase()+">";
		SetSelfHTML();
	}

	private Object TreeNodeBox(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Node GetParentNode(){
		return ParentNode;
	}
	public Node GetNode(){
		return node;
	}
	private void SetSelfHTML(){
		StringBuilder str = new StringBuilder();
		if(node instanceof Element){
			str.append("<");
			str.append(TagName.toLowerCase());
			str.append(AttrText);
			str.append(">...</");
			str.append(TagName.toLowerCase());
			str.append(">");
		}else if(node instanceof Text){
			str.append(TagName);
		}else{
			str.append(TagName);
		}
		SelfHTML = str.toString();
	}
	public void Expande(TreeItem<TreeNodeBox> treeNode){
		StringBuilder str = new StringBuilder();
		if(node instanceof Element){
			str.append("<");
			str.append(TagName.toLowerCase());
			str.append(AttrText);
			str.append(">");
			//获取当前节点index
			int index = 1;
			for(int i = 0;i<999;i++){
				if(treeNode.getParent().getChildren().get(i) == treeNode){
					index = i+1;
					break;
				}
			}
			treeNode.getParent().getChildren().add(index, tail);
		}
		SelfHTML = str.toString();
	}
	public void Collapsed(TreeItem<TreeNodeBox> treeNode){
		StringBuilder str = new StringBuilder();
		if(node instanceof Element){
			str.append("<");
			str.append(TagName.toLowerCase());
			str.append(AttrText);
			str.append(">...</");
			str.append(TagName.toLowerCase());
			str.append(">");
			treeNode.getParent().getChildren().remove(tail);
		}
		SelfHTML = str.toString();
	}
}
