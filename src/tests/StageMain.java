package tests;

import java.io.File;
import java.util.UUID;

import api.commons.WindowManager;
import api.commons.WindowManager.Dialogs.FilesDialogTypes;
import api.reporting.jasper.JasperReportView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class StageMain {

	@FXML AnchorPane root;
	
	@FXML Button btn01;
	@FXML JasperReportView rpt01;
	@FXML TreeTableView<Ttv01Item> ttv01;
	
	@FXML public void initialize() {
		initButtonsTab();
		initJasperReportsTab();
		initTreeTableViewTab();
	}
	
	private void initButtonsTab() {
		btn01.setText("Hello World !!!");
	}
	
	private void initJasperReportsTab() {
		rpt01.load();
	}
	
	private void initTreeTableViewTab() {
		
		WindowManager.bind(ttv01, Ttv01Item.class);
		ttv01.setColumnResizePolicy((param) -> true);
		ttv01.setRoot(new TreeItem<Ttv01Item>(new Ttv01Item("", "", "")));
		ttv01.getRoot().setExpanded(true);
		//ttv01.setShowRoot(false); --already unchecked in FXML 
	}
	
	@FXML public void openFilesDialog(){
		try {
			File[] files = WindowManager.Dialogs.showFilesDialog("test", FilesDialogTypes.SAVE_FILE, "Portable Image Format", "*.png", null, "myfile", WindowManager.windowOf(root), new File("/home/user/Pictures/Selection_010.png"));
			//File[] files = WindowManager.Dialogs.showFilesDialog("test", FilesDialogTypes.SINGLE_FILE, "Portable Image Format", "*.png", new File("/home/user/Pictures"), "myfile", WindowManager.windowOf(root), null);
			
			if(files != null) {
				for (File file : files) {
					System.out.println(file.getAbsolutePath());
				}
			}
		} catch (Exception e) {
			WindowManager.Dialogs.showExceptionDialog(e, "Error", "Something Happened on the way to the moon..");
		}
	}
	
	@FXML public void addNewTreeTableViewItem() {
		Ttv01Item item = new Ttv01Item("/tests/application.png", String.valueOf(ttv01.getRoot().getChildren().size()), UUID.randomUUID().toString());
		ttv01.getRoot().getChildren().add(new TreeItem<Ttv01Item>(item));
	}
	
	@FXML public void addNewTreeTableViewSubItem() {
		if(ttv01.getSelectionModel().getSelectedItem() != null) {
			TreeItem<Ttv01Item> parent_item = ttv01.getSelectionModel().getSelectedItem();
			Ttv01Item item = new Ttv01Item("/tests/application.png", "sub item " + String.valueOf(parent_item.getChildren().size()), UUID.randomUUID().toString());
			parent_item.getChildren().add(new TreeItem<Ttv01Item>(item));
			parent_item.setExpanded(true);
		}
	}
	
	@FXML public void deleteTreeTableViewItem() {
		if(ttv01.getSelectionModel().getSelectedItem() != null) {
			ttv01.getSelectionModel().getSelectedItem().getParent().getChildren().remove(ttv01.getSelectionModel().getSelectedItem());
		}
	}
	
	public static class Ttv01Item{
		ImageView icon;
		String id;
		String description;
		
		public Ttv01Item(String icon_resource_path, String id, String description) {
			setIcon(new ImageView(new Image(getClass().getResourceAsStream(icon_resource_path))));
			setId(id);
			setDescription(description);
		}
		
		public ImageView getIcon() {
			return icon;
		}
		public void setIcon(ImageView icon) {
			this.icon = icon;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
}
