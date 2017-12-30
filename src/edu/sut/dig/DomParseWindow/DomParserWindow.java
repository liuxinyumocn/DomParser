package edu.sut.dig.DomParseWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.sut.dig.DomParseWindow.DomParserWindowController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class DomParserWindow {
	private Stage primaryStage = null;
	private Scene scene = null;
	private DomParserWindowController controller = null;
	private WebEngine webEngine = null;
	private WebView webView = null;
	private DomParser domParser = null;
	
	private long StartTime = 0;
	
	private HashMap<NodeBox,TreeItem<NodeBox>> HashMapItem = null;
	
	public DomParserWindow(String title){
		try {
			HashMapItem =new HashMap<>();
			primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("DomParserWindow.fxml")
			);
			Parent root1 = loader.load();
			controller =  loader.<DomParserWindowController>getController();
			webView = controller.GetWebViewHandle();
			webEngine = webView.getEngine();
		//����״̬�ص��¼�
			SetWebViewListener();
			scene = new Scene(root1,900,600);
			primaryStage.setScene(scene);
			primaryStage.setTitle(title);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	class StateChangeListener<Status> implements ChangeListener{
		@Override
		public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			// TODO Auto-generated method stub
			if(newValue == Worker.State.SUCCEEDED){
				ShowInfo();
			}else if(newValue == Worker.State.SCHEDULED){
				//��¼����ʱ��
				StartTime = System.currentTimeMillis();
				//������ʷ����
				HideInfo();
			}else if(newValue == Worker.State.CANCELLED){
				HideInfo();
				controller.SetTitle("ʧ��");
			}
		}
	}
	private void SetWebViewListener(){
		webEngine.getLoadWorker().stateProperty().addListener(new StateChangeListener());
	}
	private void ShowInfo(){
			//��������ʱ��
				if(StartTime != 0){
					long a = System.currentTimeMillis() - StartTime;
					controller.SetTime(Long.toString(a)+"ms");
				}
			//����ҳ��URI
				controller.SetURL(webEngine.getLocation());
			//����ҳ��Title
				controller.SetTitle(webEngine.getTitle());
				
			//DOM����
				domParser = new DomParser(webEngine);
				//System.out.println(domParser);
			//����DOM TreeView
				TreeViewParser();
	}
	private void HideInfo(){
		controller.SetTime("-");
		controller.SetURL("-");
		controller.SetTitle("-");
		try{
			TreeViewParser();
		}catch(Exception e){
			
		}
	}
	private void TreeViewParser(){
		//����DOM TreeView
		List<NodeBox> nodes = domParser.GetDOMList();
		TreeItem<TreeNodeBox> root= new TreeItem<>(new TreeNodeBox("root"));
		controller.GetTreeViewHandle().setRoot(root);
		controller.GetTreeViewHandle().setShowRoot(false);
		
		//���ýڵ�չ���¼�
		root.addEventHandler(TreeItem.<TreeNodeBox>branchExpandedEvent(), 
				new EventHandler<TreeItem.TreeModificationEvent<TreeNodeBox>>(){
					@Override
					public void handle(TreeItem.TreeModificationEvent<TreeNodeBox> event){
						TreeItem<TreeNodeBox> current = event.getTreeItem();
						//�ýڵ��������ֲ�������β���ڵ�
						NodeBox currentNode = (NodeBox)current.getValue();
						currentNode.Expande(event.getTreeItem());
			}});
		//���ýڵ������¼�
		root.addEventHandler(TreeItem.<TreeNodeBox>branchCollapsedEvent(), 
				new EventHandler<TreeItem.TreeModificationEvent<TreeNodeBox>>(){
					@Override
					public void handle(TreeItem.TreeModificationEvent<TreeNodeBox> event){
						TreeItem<TreeNodeBox> current = event.getTreeItem();
						//�ýڵ��������ֲ�������β���ڵ�
						NodeBox currentNode = (NodeBox)current.getValue();
						currentNode.Collapsed(event.getTreeItem());
			}});
		
		List<TreeItem<TreeNodeBox>> ex = new ArrayList<>();
		for(int i = 0;i<nodes.size();i++){
			TreeItem<TreeNodeBox> Leaf = new TreeItem<TreeNodeBox>(nodes.get(i));
			boolean found = false;
			for(TreeItem<TreeNodeBox> item : ex ){
				if(((NodeBox)item.getValue()).GetNode() ==((NodeBox)Leaf.getValue()).GetParentNode()){
					//�ҵ��丸�ڵ�
					item.getChildren().add(Leaf);
					found = true;
					break;
				}
			}
			if(!found)
				root.getChildren().add(Leaf);
			ex.add(Leaf);
			//System.out.println(Leaf.getValue());
		}
		
	}
	public void SetVisible(Boolean v) {
		if(v) {
			primaryStage.show();
			
		}
	}
	public DomParserWindowController GetController() {
		return controller;
	}
	public void LoadURL(String Url){
		String url = Url.trim();
		url = url.startsWith("http://") || url.startsWith("https://") ? url : "http://"+url;
		webEngine.load(url);
	}
}
